package com.fsse2309.project_backend.data.transaction.entity;

import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;
    @ManyToOne
    @JoinColumn(name="buyer_uid", nullable = false)
    private UserEntity user;
    @Column(name="datetime", nullable = false)
    private LocalDateTime datetime;
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    @Column(name = "status")
    private String status;

    public TransactionEntity(){
    }

    public TransactionEntity(UserEntity user, LocalDateTime datetime, BigDecimal total, String status){
        this.user = user;
        this.datetime=datetime;
        this.total=total;
        this.status=status;
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
}
