package com.fsse2309.project_backend.api;

import com.fsse2309.project_backend.Util.JwtUtil;
import com.fsse2309.project_backend.config.EnvConfig;
import com.fsse2309.project_backend.data.transaction.domainObject.TransactionData;
import com.fsse2309.project_backend.data.transaction.dto.TransactionResponseDto;
import com.fsse2309.project_backend.data.transaction.dto.TransactionResultResponseDto;
import com.fsse2309.project_backend.service.TransactionService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@CrossOrigin({EnvConfig.prodEnvBaseUrl, EnvConfig.devEnvBaseUrl})
public class TransactionApi {
    private TransactionService transactionService;

    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/prepare")
    public TransactionResponseDto prepareTransaction(JwtAuthenticationToken jwt){
        return new TransactionResponseDto(transactionService.prepareTransaction(JwtUtil.getFirebaseUser(jwt)));
    }

    @GetMapping("/{tid}")
    public TransactionResponseDto getTransaction(JwtAuthenticationToken jwt, @PathVariable Integer tid){
        return new TransactionResponseDto(transactionService.getTransaction(JwtUtil.getFirebaseUser(jwt), tid));
    }

    @PatchMapping("/{tid}/pay")
    public TransactionResultResponseDto updateTransaction(JwtAuthenticationToken jwt, @PathVariable Integer tid){
        transactionService.updateTransaction(JwtUtil.getFirebaseUser(jwt), tid);
        return new TransactionResultResponseDto("SUCCESS");
    }

    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto finishTransaction(JwtAuthenticationToken jwt, @PathVariable Integer tid){
        return new TransactionResponseDto(transactionService.finishTransaction(JwtUtil.getFirebaseUser(jwt), tid));
    }
}
