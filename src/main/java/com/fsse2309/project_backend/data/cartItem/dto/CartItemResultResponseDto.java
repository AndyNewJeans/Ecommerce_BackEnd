package com.fsse2309.project_backend.data.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItemResultResponseDto {
    @JsonProperty("result")
    private String result;

    public CartItemResultResponseDto(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
