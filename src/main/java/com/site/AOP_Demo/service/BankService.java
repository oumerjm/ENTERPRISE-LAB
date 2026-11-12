package com.site.AOP_Demo.service;

import com.site.AOP_Demo.annotation.CheckSecurity;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    private double balance = 1000.0;

    public double getBalance() {
        return balance;
    }

    @CheckSecurity(role = "ADMIN")
    public String withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new RuntimeException("Insufficient funds! Available: $" + balance);
        }
        balance -= amount;
        return "Withdrew $" + amount + ". New balance: $" + balance;
    }
}