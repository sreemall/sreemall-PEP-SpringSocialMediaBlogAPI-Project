package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.Exception;

import com.example.repository.AccountRepository;

import com.example.entity.Account;
import com.example.exception.*;;



@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account addAccount (Account account) throws  Exception {
        String username = account.getUsername ();
        String password = account.getPassword();

        if ((username != null) && (username.length() >= 1) && 
                (password != null) && (password.length() >= 4)) {  //valid username and password
            if (!accountRepository.existByUsername(username)) // no duplicate
                return accountRepository.save (account);
            else  { //duplicate
                throw new DuplicateAccountException (username);
            } 
        }
        else
            throw new InvalidCredentialsException ("Invalid username/password!");
    }

    // public Account addAccount (Account account) {
    //     String username = account.getUsername ();
    //     if ((username != null) && (username.length() >= 1) &&  (account.getPassword ().length() >= 4)) {
    //         if (accountDAO.selectAccountByUsername(username) == null) {
    //             return accountDAO.insertAccount(account);
    //         }
    //     }

    //     return null;
    // }

    // public Account login (Account account) {
    //     return accountDAO.login (account);
    // }

}

