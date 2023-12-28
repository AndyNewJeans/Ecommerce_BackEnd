package com.fsse2309.project_backend.data.transactionProduct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2309.project_backend.data.product.domainObject.ProductDetailData;
import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;

import java.math.BigDecimal;

public class TransactionProductResponseDto {
    @JsonProperty("transaction")
    private TransactionEntity transaction;
    @JsonProperty("items")
    private ProductDetailData productDetailData;
    private Integer quantity;
}
