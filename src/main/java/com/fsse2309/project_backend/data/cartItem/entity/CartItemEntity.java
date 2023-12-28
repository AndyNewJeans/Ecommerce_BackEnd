package com.fsse2309.project_backend.data.cartItem.entity;

import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;
    @ManyToOne
    @JoinColumn(name="uid", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn (name="pid", nullable = false)
    private ProductEntity product;
    @Column(name="quantity", nullable = false)
    private Integer quantity;

    public CartItemEntity(){
    }

    public CartItemEntity(UserEntity user, ProductEntity product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
