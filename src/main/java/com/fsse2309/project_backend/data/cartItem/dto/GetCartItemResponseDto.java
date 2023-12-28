package com.fsse2309.project_backend.data.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2309.project_backend.data.cartItem.domainObject.CartItemData;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;

import java.math.BigDecimal;

public class GetCartItemResponseDto {
    @JsonProperty("user")
    private UserEntity user;
    @JsonProperty("product")
    private ProductEntity product;
    @JsonProperty("quantity")
    private Integer quantity;

    public GetCartItemResponseDto(CartItemData data) {
        this.user = data.getUser();
        this.product = data.getProduct();
        this.quantity = data.getQuantity();
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
