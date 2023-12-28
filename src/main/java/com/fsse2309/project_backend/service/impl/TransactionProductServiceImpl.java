package com.fsse2309.project_backend.service.impl;

import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2309.project_backend.repository.TransactionProductRepository;
import com.fsse2309.project_backend.service.TransactionProductService;
import com.fsse2309.project_backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
@Service
public class TransactionProductServiceImpl implements TransactionProductService {
    private TransactionProductRepository transactionProductRepository;
    @Autowired
    public TransactionProductServiceImpl(TransactionProductRepository transactionProductRepository){
        this.transactionProductRepository = transactionProductRepository;
    }
    public List<TransactionProductEntity> findTransactionProductEntitiesByTransaction(TransactionEntity transaction){
        return transactionProductRepository.findTransactionProductEntitiesByTransaction(transaction);
    }

    public TransactionProductEntity saveTransactionProduct(TransactionProductEntity transactionProductEntity){
        return transactionProductRepository.save(transactionProductEntity);
    }
}
