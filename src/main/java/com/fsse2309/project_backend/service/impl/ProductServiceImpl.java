package com.fsse2309.project_backend.service.impl;

import com.fsse2309.project_backend.data.product.domainObject.GetAllProductData;
import com.fsse2309.project_backend.data.product.domainObject.ProductDetailData;
import com.fsse2309.project_backend.data.product.entity.ProductEntity;
import com.fsse2309.project_backend.exception.product.ProductNotFoundException;
import com.fsse2309.project_backend.repository.ProductRepository;
import com.fsse2309.project_backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public List<GetAllProductData> getAllProduct(){
        List<GetAllProductData> getAllProductDataList = new ArrayList<>();
        for(ProductEntity entity:productRepository.findAll()){
            GetAllProductData data = new GetAllProductData(entity);
            getAllProductDataList.add(data);
        }
        return getAllProductDataList;
    }
    @Override
    public ProductEntity getEntityByPid(Integer pid) {
        try {
            if (productRepository.existsByPid(pid)) {
                return productRepository.getProductEntityByPid(pid);
            }
            throw new ProductNotFoundException();
        } catch (ProductNotFoundException ex){
            logger.warn("Product pid: " + pid + " Not Found");
            throw ex;
        }
    }
    @Override
    public ProductEntity minusProductStock(Integer pid, Integer quantity){
        ProductEntity productEntity = getEntityByPid(pid);
        productEntity.setStock(productEntity.getStock()-quantity);
        return productRepository.save(productEntity);
    }
}
