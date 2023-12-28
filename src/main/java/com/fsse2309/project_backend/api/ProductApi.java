package com.fsse2309.project_backend.api;

import com.fsse2309.project_backend.config.EnvConfig;
import com.fsse2309.project_backend.data.product.domainObject.*;
import com.fsse2309.project_backend.data.product.dto.response.GetAllProductResponseDto;
import com.fsse2309.project_backend.data.product.dto.response.ProductDetailResponseDto;
import com.fsse2309.project_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/public/product")
@CrossOrigin({EnvConfig.prodEnvBaseUrl, EnvConfig.devEnvBaseUrl})
public class ProductApi {
    private ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<GetAllProductResponseDto> getAllProduct(){
        List<GetAllProductResponseDto> getAllProductResponseDtoList = new ArrayList<>();
        for (GetAllProductData data : productService.getAllProduct()){
            GetAllProductResponseDto dto = new GetAllProductResponseDto(data);
            getAllProductResponseDtoList.add(dto);
        }
        return getAllProductResponseDtoList;
    }
    @GetMapping("/{id}")
    public ProductDetailResponseDto getProductById(@PathVariable int id){
        return new ProductDetailResponseDto(productService.getEntityByPid(id));
    }
    @GetMapping("/test")
    public String testEndpoint() {
        return "Test endpoint response";
    }
}
