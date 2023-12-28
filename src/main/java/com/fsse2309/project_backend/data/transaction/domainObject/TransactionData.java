package com.fsse2309.project_backend.data.transaction.domainObject;

import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.transactionProduct.domainObject.TransactionProductData;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionData {
    private Integer tid;
    private UserEntity user;
    private LocalDateTime datetime;
    private BigDecimal total;
    private String status;
    private List<TransactionProductData> transactionProductDataList;

    public TransactionData(TransactionEntity entity, List<TransactionProductData> transactionProductDataList) {
        this.tid = entity.getTid();
        this.user = entity.getUser();
        this.datetime = entity.getDatetime();
        this.total = entity.getTotal();
        this.status = entity.getStatus();
        this.transactionProductDataList = transactionProductDataList;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransactionProductData> getTransactionProductDataList() {
        return transactionProductDataList;
    }

    public void setTransactionProductDataList(List<TransactionProductData> transactionProductDataList) {
        this.transactionProductDataList = transactionProductDataList;
    }
}
