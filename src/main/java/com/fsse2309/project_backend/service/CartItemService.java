package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.data.cartItem.domainObject.CartItemData;
import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CartItemService {
    public CartItemData addCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData);
    public List<CartItemData> getAllCartItem(FirebaseUserData firebaseUserData);
    public CartItemData updateCartItem(Integer pid, Integer quantity, FirebaseUserData firebaseUserData);
    public CartItemData deleteCartItem(Integer pid, FirebaseUserData firebaseUserData);
    public List<CartItemEntity> getEntityByUser(FirebaseUserData data);
    public void deleteAllCartItem(UserEntity user);
    public boolean isCartItemExists(UserEntity user);
}
