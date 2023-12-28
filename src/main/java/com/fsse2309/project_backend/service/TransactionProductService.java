package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;

import java.util.List;

public interface TransactionProductService {
    List<TransactionProductEntity> findTransactionProductEntitiesByTransaction(TransactionEntity transaction);
    TransactionProductEntity saveTransactionProduct(TransactionProductEntity transactionProductEntity);
}
