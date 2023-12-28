package com.fsse2309.project_backend.api;

import com.fsse2309.project_backend.Util.JwtUtil;
import com.fsse2309.project_backend.config.EnvConfig;
import com.fsse2309.project_backend.data.cartItem.domainObject.CartItemData;
import com.fsse2309.project_backend.data.cartItem.dto.CartItemResultResponseDto;
import com.fsse2309.project_backend.data.cartItem.dto.GetCartItemResponseDto;
import com.fsse2309.project_backend.service.CartItemService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin({EnvConfig.prodEnvBaseUrl, EnvConfig.devEnvBaseUrl})
public class CartItemApi {
    private CartItemService cartItemService;

    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/{pid}/{quantity}")
    public CartItemResultResponseDto addCartItem(JwtAuthenticationToken jwt, @PathVariable Integer pid, @PathVariable Integer quantity){
        cartItemService.addCartItem(pid, quantity, JwtUtil.getFirebaseUser(jwt));
        return new CartItemResultResponseDto("SUCCESS");
    }

    @GetMapping
    public List<GetCartItemResponseDto> getAllCartItem(JwtAuthenticationToken jwt){
        List<GetCartItemResponseDto> getCartItemResponseDtoList = new ArrayList<>();
        for(CartItemData data : cartItemService.getAllCartItem(JwtUtil.getFirebaseUser(jwt))){
            GetCartItemResponseDto dto = new GetCartItemResponseDto(data);
            getCartItemResponseDtoList.add(dto);
        }
        return getCartItemResponseDtoList;
    }
    @PatchMapping ("/{pid}/{quantity}")
    public GetCartItemResponseDto updateCartItem(JwtAuthenticationToken jwt, @PathVariable Integer pid, @PathVariable Integer quantity){
        return new GetCartItemResponseDto(cartItemService.updateCartItem(pid, quantity, JwtUtil.getFirebaseUser(jwt)));
    }

    @DeleteMapping("/{pid}")
    public CartItemResultResponseDto deleteCartItem(JwtAuthenticationToken jwt, @PathVariable Integer pid){
        cartItemService.deleteCartItem(pid, JwtUtil.getFirebaseUser(jwt));
        return new CartItemResultResponseDto("SUCCESS");
    }
}
