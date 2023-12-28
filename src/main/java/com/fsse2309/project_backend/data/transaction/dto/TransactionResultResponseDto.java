package com.fsse2309.project_backend.data.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResultResponseDto {
    @JsonProperty("result")
    private String result;

    public TransactionResultResponseDto(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
