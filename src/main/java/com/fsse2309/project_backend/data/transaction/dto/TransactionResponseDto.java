package com.fsse2309.project_backend.data.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.transaction.domainObject.TransactionData;
import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.transactionProduct.domainObject.TransactionProductData;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionResponseDto {
    @JsonProperty("tid")
    private Integer tid;
    @JsonProperty("buyer_id")
    private Integer buyerId;
    @JsonProperty("datetime")
    private LocalDateTime datetime;
    @JsonProperty("total")
    private BigDecimal total;
    @JsonProperty("status")
    private String status;
    @JsonProperty("items")
    private List<TransactionProductData> transactionProductDataList;

    public TransactionResponseDto(TransactionData data) {
        this.tid = data.getTid();
        this.buyerId = data.getUser().getUid();
        this.datetime = data.getDatetime();
        this.total = data.getTotal();
        this.status = data.getStatus();
        this.transactionProductDataList = data.getTransactionProductDataList();
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
