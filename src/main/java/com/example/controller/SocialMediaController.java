package com.example.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import com.example.exception.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity<?> postAccountHandler(@RequestBody Account account) {
        
        // Account account = mapper.readValue(ctx.body(), Account.class);

        try {
            Account newAccount = accountService.addAccount(account);

            return ResponseEntity.ok().body(newAccount);
        }
        catch (DuplicateAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
        }
        catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginHandler (@RequestBody Account account)  {

        try {
            account = accountService.login (account);
        }
        catch (InvalidLoginCredentialsException e) {
            return ResponseEntity.status (HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        return ResponseEntity.ok().body(account);
    }

    @RequestMapping(value="/messages", method = RequestMethod.POST)
    public ResponseEntity<?> postMessageHandler(@RequestBody Message message)  {

        try {
            Message newMessage = messageService.addMessage(message);

            return ResponseEntity.ok().body(newMessage);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value="/messages", method = RequestMethod.GET)
    public List<Message> getAllMessagesHandler() {
        return messageService.getAllMessages ();
    }

    @RequestMapping(value="/messages/{message_id}", method = RequestMethod.GET)
    public Message getMessageByIdHandler (@PathVariable Integer message_id) {
        return messageService.getMessageById(message_id);
        
    }

    @RequestMapping(value="/messages/{message_id}", method = RequestMethod.DELETE)
    public Integer deleteMessageByIdHandler (@PathVariable Integer message_id) {

        return messageService.deleteMessageById(message_id);
    }

    @RequestMapping(value="/messages/{message_id}", method = RequestMethod.PATCH)
    public ResponseEntity<Integer> updateMessageTextByIdHandler (@PathVariable Integer message_id, @RequestBody Message message) {
    
        Integer count = messageService.updateMessageTextById(message_id, message.getMessageText());

        if (count != null) {
            return ResponseEntity.ok().body(count);
        }
        else {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @RequestMapping(value="/accounts/{account_id}/messages", method = RequestMethod.GET)
    public List<Message> getAllMessagesByUserHandler (@PathVariable Integer account_id) {
        return messageService.getAllMessagesByUser (account_id);  
    }

}
