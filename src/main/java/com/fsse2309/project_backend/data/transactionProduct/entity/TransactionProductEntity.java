package com.fsse2309.project_backend.data.transactionProduct.entity;

import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.transaction.entity.TransactionEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name="transaction_product")
public class TransactionProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tpid;
    @ManyToOne
    @JoinColumn(name= "tid", nullable = false)
    private TransactionEntity transaction;
    @Column(name = "pid", nullable = false)
    private Integer pid;
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    @Column(name= "quantity", nullable = false)
    private Integer quantity;

    public TransactionProductEntity(){
    }
    public TransactionProductEntity(TransactionEntity transaction, CartItemEntity cartItem){
        this.transaction = transaction;
        this.pid = cartItem.getProduct().getPid();
        setSubtotal(cartItem.getProduct().getPrice(), cartItem.getQuantity());
        this.quantity = cartItem.getQuantity();
    }

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setSubtotal(BigDecimal price, Integer quantity) {
        this.subtotal = price.multiply(BigDecimal.valueOf(quantity));
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
