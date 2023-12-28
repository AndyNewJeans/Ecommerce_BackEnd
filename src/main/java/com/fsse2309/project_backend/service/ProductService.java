package com.fsse2309.project_backend.service;

import com.fsse2309.project_backend.data.product.domainObject.*;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    public List<GetAllProductData> getAllProduct();
    public ProductEntity getEntityByPid(Integer pid);
    public ProductEntity minusProductStock(Integer pid, Integer quantity);
}
