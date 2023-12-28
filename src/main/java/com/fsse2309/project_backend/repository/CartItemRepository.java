package com.fsse2309.project_backend.repository;

import com.fsse2309.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.data.user.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository<CartItemEntity, Integer> {
    boolean existsByProductAndUser(ProductEntity product, UserEntity user);
    Optional<CartItemEntity> findCartItemEntitiesByProductAndUser(ProductEntity product, UserEntity user);
    List<CartItemEntity> findCartItemEntitiesByUser(UserEntity user);
    boolean existsByUser(UserEntity user);
}
