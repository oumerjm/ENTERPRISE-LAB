package com.site.AOP_Demo.controller;

import com.site.AOP_Demo.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance() {
        System.out.println("[Weaving Proof] Injected class: " + bankService.getClass().getName());
        return ResponseEntity.ok("Balance: $" + bankService.getBalance());
    }

    @PostMapping("/withdraw/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable double amount) {
        try {
            return ResponseEntity.ok(bankService.withdraw(amount));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}