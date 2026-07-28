package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.lang.Exception;

import com.example.repository.AccountRepository;

import com.example.entity.Account;
import com.example.exception.*;;



@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount (Account account) {
        String username = account.getUsername ();
        String password = account.getPassword();

        if ((username != null) && !username.isBlank() && 
                (password != null) && (password.length() >= 4)) {  //valid username and password
            if (!accountRepository.existsByUsername(username)) {  // no duplicate
                return accountRepository.save (account);
            }
            else  { //duplicate
                throw new DuplicateAccountException (username);
            } 
        }
        else
            throw new InvalidCredentialsException ("Invalid username/password!");
    }

    public Account login (Account account) {
        Account newAccount = accountRepository.findByUsernameAndPassword(account.getUsername(),
                                                                    account.getPassword());
        if (newAccount == null) {
            throw new InvalidLoginCredentialsException ("Invalid username/password!");
        }
        else {
            return newAccount;
        }
    }

}

