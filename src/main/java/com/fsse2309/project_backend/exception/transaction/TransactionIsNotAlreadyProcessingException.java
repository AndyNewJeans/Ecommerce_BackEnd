package com.fsse2309.project_backend.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionIsNotAlreadyProcessingException extends RuntimeException{
}
