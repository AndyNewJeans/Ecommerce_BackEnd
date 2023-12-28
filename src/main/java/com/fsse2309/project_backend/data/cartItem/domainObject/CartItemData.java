package com.fsse2309.project_backend.data.cartItem.domainObject;

import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CartItemData {
    private UserEntity user;
    private ProductEntity product;
    private Integer quantity;

    public CartItemData(CartItemEntity entity) {
        this.user = entity.getUser();
        this.product = entity.getProduct();
        this.quantity = entity.getQuantity();
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
