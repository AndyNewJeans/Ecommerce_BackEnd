package com.fsse2309.project_backend.service.impl;

import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.transaction.domainObject.TransactionData;
import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.transactionProduct.domainObject.TransactionProductData;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import com.fsse2309.project_backend.exception.cartItem.CartItemNotFoundException;
import com.fsse2309.project_backend.exception.transaction.TransactionIsNotAlreadyPrepareException;
import com.fsse2309.project_backend.exception.transaction.TransactionIsNotAlreadyProcessingException;
import com.fsse2309.project_backend.exception.transaction.TransactionNotFoundException;
import com.fsse2309.project_backend.repository.TransactionProductRepository;
import com.fsse2309.project_backend.repository.TransactionRepository;
import com.fsse2309.project_backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private TransactionRepository transactionRepository;
    private TransactionProductService transactionProductService;
    private CartItemService cartItemService;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionProductService transactionProductService, ProductService productService, CartItemService cartItemService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.transactionProductService = transactionProductService;
        this.productService =productService;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @Override
    public TransactionData prepareTransaction(FirebaseUserData firebaseUserData) {
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            if (!cartItemService.isCartItemExists(userEntity)) {
                throw new CartItemNotFoundException();
            }
            List<CartItemEntity> cartItems = cartItemService.getEntityByUser(firebaseUserData);
            TransactionEntity transaction = createAndSaveTransaction(userEntity, calculateTotal(cartItems));
            List<TransactionProductData> transactionProducts = processCartItems(transaction, cartItems);
            cartItemService.deleteAllCartItem(userEntity);
            return new TransactionData(transaction, transactionProducts);
        } catch (CartItemNotFoundException ex){
            logger.warn("Cannot prepare transaction. Cart item does not exist.");
            throw ex;
        }
    }

    @Override
    public TransactionData getTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            UserEntity user = userService.getEntityByFirebaseUserData(firebaseUserData);
            if (!isTransactionExist(tid, user)) {
                throw new TransactionNotFoundException();
            }
            TransactionEntity transaction = findUserTransaction(user, tid);
            return buildTransactionData(transaction);
        } catch (TransactionNotFoundException ex){
            logger.warn("Cannot not get transaction: Transaction does not exist.");
            throw ex;
        }
    }

    @Override
    public TransactionData updateTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            UserEntity user = userService.getEntityByFirebaseUserData(firebaseUserData);
            if (!isTransactionExist(tid, user)) {
                throw new TransactionNotFoundException();
            }
            if (isTransactionNotAlreadyPrepare(tid, user)) {
                throw new TransactionIsNotAlreadyPrepareException();
            }
            TransactionEntity transaction = findUserTransaction(user, tid);
            transaction.setStatus("PROCESSING");
            transactionRepository.save(transaction);
            return buildTransactionData(transaction);
        } catch (TransactionNotFoundException ex){
            logger.warn("Cannot update transaction: "+ tid +" id not found.");
            throw ex;
        } catch (TransactionIsNotAlreadyPrepareException ex){
            logger.warn("Cannot update transaction: "+ tid +" id status is not prepare.");
            throw ex;
        }
    }

    @Override
    public TransactionData finishTransaction(FirebaseUserData firebaseUserData, Integer tid) {
        try {
            UserEntity user = userService.getEntityByFirebaseUserData(firebaseUserData);
            if (!isTransactionExist(tid, user)) {
                throw new TransactionNotFoundException();
            }
            if (isTransactionNotAlreadyProcessing(tid, user)) {
                throw new TransactionIsNotAlreadyProcessingException();
            }

            TransactionEntity transaction = findUserTransaction(user, tid);
            transaction.setStatus("SUCCESS");
            transactionRepository.save(transaction);
            List<TransactionProductData> transactionProducts = processFinishedTransaction(transaction);
            return new TransactionData(transaction, transactionProducts);
        } catch (TransactionNotFoundException ex){
            logger.warn("Cannot finish transaction: "+ tid +" id not found.");
            throw ex;
        } catch (TransactionIsNotAlreadyPrepareException ex){
            logger.warn("Cannot finish transaction: "+ tid +" id status is not processing.");
            throw ex;
        }
    }

    private BigDecimal calculateTotal(List<CartItemEntity> cartItems) {
        return cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private TransactionEntity createAndSaveTransaction(UserEntity user, BigDecimal totalAmount) {
        TransactionEntity transaction = new TransactionEntity(user, LocalDateTime.now(), totalAmount, "PREPARE");
        return transactionRepository.save(transaction);
    }

    private List<TransactionProductData> processCartItems(TransactionEntity transaction, List<CartItemEntity> cartItems) {
        return cartItems.stream()
                .map(cartItem -> createTransactionProduct(transaction, cartItem))
                .collect(Collectors.toList());
    }

    private TransactionProductData createTransactionProduct(TransactionEntity transaction, CartItemEntity cartItem) {
        TransactionProductEntity transactionProductEntity = new TransactionProductEntity(transaction, cartItem);
        transactionProductService.saveTransactionProduct(transactionProductEntity);
        return new TransactionProductData(transactionProductEntity, productService.getEntityByPid(transactionProductEntity.getPid()));
    }
    private TransactionEntity findUserTransaction(UserEntity user, Integer tid) {
        return transactionRepository.findTransactionEntitiesByTidAndUser(tid, user);
    }

    private List<TransactionProductData> processFinishedTransaction(TransactionEntity transaction) {
        List<TransactionProductEntity> transactionProducts = transactionProductService.findTransactionProductEntitiesByTransaction(transaction);
        return transactionProducts.stream()
                .map(this::processTransactionProduct)
                .collect(Collectors.toList());
    }

    private TransactionProductData processTransactionProduct(TransactionProductEntity transactionProduct) {
        productService.minusProductStock(transactionProduct.getPid(), transactionProduct.getQuantity());
        return new TransactionProductData(transactionProduct, productService.getEntityByPid(transactionProduct.getPid()));
    }

    private TransactionData buildTransactionData(TransactionEntity transaction) {
        List<TransactionProductEntity> transactionProducts = transactionProductService.findTransactionProductEntitiesByTransaction(transaction);
        List<TransactionProductData> transactionProductDataList = transactionProducts.stream()
                .map(tp -> new TransactionProductData(tp, productService.getEntityByPid(tp.getPid())))
                .collect(Collectors.toList());
        return new TransactionData(transaction, transactionProductDataList);
    }

    private boolean isTransactionExist(Integer tid, UserEntity entity){
        return transactionRepository.existsByTidAndUser(tid, entity);
    }

    private boolean isTransactionNotAlreadyPrepare(Integer tid, UserEntity entity){
        return !("PREPARE").equals(findUserTransaction(entity, tid).getStatus());
    }

    private boolean isTransactionNotAlreadyProcessing(Integer tid, UserEntity entity){
        return !("PROCESSING").equals(findUserTransaction(entity, tid).getStatus());
    }
}
