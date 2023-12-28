package com.fsse2309.project_backend.service.impl;

import com.fsse2309.project_backend.data.cartItem.domainObject.CartItemData;
import com.fsse2309.project_backend.data.cartItem.dto.GetCartItemResponseDto;
import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import com.fsse2309.project_backend.exception.cartItem.CartItemNotFoundException;
import com.fsse2309.project_backend.exception.cartItem.NotEnoughStockException;
import com.fsse2309.project_backend.exception.cartItem.QuantityIsNotPositiveException;
import com.fsse2309.project_backend.exception.product.ProductNotFoundException;
import com.fsse2309.project_backend.repository.CartItemRepository;
import com.fsse2309.project_backend.service.CartItemService;
import com.fsse2309.project_backend.service.ProductService;
import com.fsse2309.project_backend.service.UserService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartItemServiceImpl implements CartItemService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private ProductService productService;
    @Autowired
    public CartItemServiceImpl(UserService userService, CartItemRepository cartItemRepository, ProductService productService){
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public CartItemData addCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData){
        try {
            if (!isQuantityPositive(quantity)) {
                throw new QuantityIsNotPositiveException();
            }
            if(!isEnoughStock(quantity, pid)){
                throw new NotEnoughStockException();
            }
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.getEntityByPid(pid);

            return new CartItemData(cartItemRepository.findCartItemEntitiesByProductAndUser(productEntity, userEntity)
                    .map(cartItemEntity -> addExistingCartItem(cartItemEntity, quantity))
                    .orElseGet(() -> createNewCartItem(userEntity, productEntity, quantity)));
        } catch (QuantityIsNotPositiveException ex){
            logger.warn("Cannot add cart item: "+quantity+" is not positive.");
            throw ex;
        }catch (NotEnoughStockException ex) {
            logger.warn("Cannot add cart item: "+quantity+" is larger than stock.");
            throw ex;
        }
    }

    public List<CartItemData> getAllCartItem(FirebaseUserData firebaseUserData){
        List<CartItemData> cartItemDataList = new ArrayList<>();
        for(CartItemEntity entity : getEntityByUser(firebaseUserData)){
            CartItemData cartItemData = new CartItemData(entity);
            cartItemDataList.add(cartItemData);
        }
        return cartItemDataList;
    }
    public CartItemData updateCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData){
        try {
            if (!isQuantityPositive(quantity)) {
                throw new QuantityIsNotPositiveException();
            }
            if(!isEnoughStock(quantity, pid)){
                throw new NotEnoughStockException();
            }
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            ProductEntity productEntity = productService.getEntityByPid(pid);

            return new CartItemData(cartItemRepository.findCartItemEntitiesByProductAndUser(productEntity, userEntity)
                    .map(cartItemEntity -> updateExistingCartItem(cartItemEntity, quantity))
                    .orElseThrow(CartItemNotFoundException::new));
        } catch (QuantityIsNotPositiveException ex){
            logger.warn("Cannot update cart item: "+quantity+" is not positive");
            throw ex;
        } catch (NotEnoughStockException ex){
        logger.warn("Cannot add cart item: "+quantity+" is larger than stock.");
        throw ex;
    }
    }
    public CartItemData deleteCartItem(Integer pid, FirebaseUserData firebaseUserData) {
        try {
            CartItemEntity cartItemEntity = cartItemRepository.findCartItemEntitiesByProductAndUser(
                            productService.getEntityByPid(pid), userService.getEntityByFirebaseUserData(firebaseUserData))
                    .orElseThrow(CartItemNotFoundException::new);
            CartItemData cartItemData = new CartItemData(cartItemEntity);
            cartItemRepository.delete(cartItemEntity);
            return cartItemData;
        } catch (CartItemNotFoundException ex) {
            logger.warn("Cannot delete cart item: " + pid + " cart item not found.");
            throw ex;
        }
    }
    @Override
    public List<CartItemEntity> getEntityByUser(FirebaseUserData data){
        return cartItemRepository.findCartItemEntitiesByUser(userService.getEntityByFirebaseUserData(data));
    }
    @Override
    public void deleteAllCartItem(UserEntity user){
        cartItemRepository.deleteAll(cartItemRepository.findCartItemEntitiesByUser(user));
    }
    @Override
    public boolean isCartItemExists(UserEntity user){
        return cartItemRepository.existsByUser(user);
    }
    private CartItemEntity addExistingCartItem(CartItemEntity cartItemEntity, int additionalQuantity) {
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() + additionalQuantity);
        return cartItemRepository.save(cartItemEntity);
    }

    private CartItemEntity updateExistingCartItem(CartItemEntity cartItemEntity, int additionalQuantity) {
        cartItemEntity.setQuantity(additionalQuantity);
        return cartItemRepository.save(cartItemEntity);
    }

    private CartItemEntity createNewCartItem(UserEntity userEntity, ProductEntity productEntity, int quantity) {
        return cartItemRepository.save(new CartItemEntity(userEntity, productEntity, quantity));
    }

    private boolean isQuantityPositive(Integer quantity){
        return quantity>0;
    }

    private boolean isEnoughStock(Integer quantity, Integer pid){
        return productService.getEntityByPid(pid).getStock()>quantity;
    }


}
