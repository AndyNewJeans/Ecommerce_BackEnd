package com.fsse2309.project_backend.repository;

import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    public ProductEntity getProductEntityByPid(Integer pid);
    public boolean existsByPid(Integer pid);

}
