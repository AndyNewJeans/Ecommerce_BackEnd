package com.fsse2309.project_backend.repository;

import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    TransactionEntity findTransactionEntitiesByTidAndUser(Integer tid, UserEntity entity);
    boolean existsByTidAndUser(Integer tid, UserEntity entity);
}
