package com.fsse2309.project_backend.data.transactionProduct.domainObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class TransactionProductData {
    @JsonProperty("tpid")
    private Integer tpid;
    @JsonProperty("product")
    private ProductEntity product;
    @JsonProperty("subtotal")
    private BigDecimal subtotal;
    @JsonProperty("quantity")
    private Integer quantity;

    public TransactionProductData(TransactionProductEntity transactionProductEntity, ProductEntity productEntity){
        this.tpid = transactionProductEntity.getTpid();
        this.product = productEntity;
        this.subtotal = transactionProductEntity.getSubtotal();
        this.quantity = transactionProductEntity.getQuantity();
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
