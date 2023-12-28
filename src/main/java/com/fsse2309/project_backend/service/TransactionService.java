package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.data.transaction.domainObject.TransactionData;
import com.fsse2309.project_backend.data.transactionProduct.domainObject.TransactionProductData;
import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;

public interface TransactionService {
    public TransactionData prepareTransaction(FirebaseUserData firebaseUserData);
    public TransactionData getTransaction(FirebaseUserData firebaseUserData, Integer tid);
    public TransactionData updateTransaction(FirebaseUserData firebaseUserData, Integer tid);
    public TransactionData finishTransaction(FirebaseUserData firebaseUserData, Integer tid);
}
