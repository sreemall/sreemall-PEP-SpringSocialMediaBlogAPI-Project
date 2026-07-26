package com.example.controller;

import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import com.example.exception.DuplicateAccountException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
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
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Account loginHandler ()  {
        // ObjectMapper mapper = new ObjectMapper ();
        // Account account = mapper.readValue(ctx.body(), Account.class);

        // account = accountService.login (account);
        // if (account != null) {
        //     ctx.json (account);
        // }
        // else
        //     ctx.status (400);

        return new Account();
    }

    @RequestMapping(value="/messages", method = RequestMethod.POST)
    public Message postMessageHandler()  {
        // ObjectMapper mapper = new ObjectMapper();

        // Message message = mapper.readValue(ctx.body(), Message.class);

        // Message newMesage = messageService.addMessage(message);
        // if (newMesage == null) {
        //     ctx.status(400);
        // } else {
        //     ctx.json(newMesage);
        // }
        return new Message();
    }

    // @RequestMapping("/messages", method = RequestMethod.GET)
    // public List<Message> getAllMessagesHandler() {
    //     // ctx.json(messageService.getAllMessages());
    // }

    // @RequestMapping("/messages/{message_id}", method = RequestMethod.GET)
    // public Message getMessageByIdHandler () {
    //     // messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));
    //     return new Message();
    // }

    // @RequestMapping("/messages/{message_id}", method = RequestMethod.DELETE)
    // public Message deleteMessageByIdHandler () {
    //     // Message message = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
    //     // if (message == null)
    //     //     ctx.status (200);
    //     // else
    //     //     ctx.json (message);
    //     return new Message();
    // }

    // @RequestMapping("/messages/{message_id}", method = RequestMethod.PATCH)
    // public Message updateMessageTextByIdHandler () {
    //     // ObjectMapper mapper = new ObjectMapper();
    //     // Message message = mapper.readValue(ctx.body(), Message.class);
    //     // message = messageService.UpdateMessageTextById(Integer.parseInt(
    //     //                                         ctx.pathParam ("message_id")),
    //     //                                         message.getMessage_text());
    //     // if (message == null)
    //     //     ctx.status (400);
    //     // else
    //     //     ctx.json (message);
    //     return new Message();
    // }

    // @RequestMapping("/accounts/{account_id}/messages", method = RequestMethod.GET)
    // public List<Message> getAllMessagesByUserHandler () {
    //     //ctx.json (messageService.getAllMessagesByUser (Integer.parseInt(ctx.pathParam ("account_id"))));
    //     return new ArrayList<>()
    // }



}
