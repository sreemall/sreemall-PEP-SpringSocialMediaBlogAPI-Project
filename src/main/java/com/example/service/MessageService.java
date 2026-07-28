package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import com.example.exception.InvalidMessagePostedUser;
import com.example.exception.InvalidMessageText;

import java.util.List;

import com.example.entity.Message;
//import com.example.exception.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService (AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message addMessage (Message message) {
        String message_text = message.getMessageText();

        if ((message_text != null) && (message_text.length() > 0) && (message_text.length() <= 255)) {
            //check if posted_by is a valid user
            if (accountRepository.existsByAccountId(message.getPostedBy())) {
                return messageRepository.save (message);
            }
            else {
                throw new InvalidMessagePostedUser (message.getPostedBy());
            }
        }
        else {
            throw new InvalidMessageText ("Invalid Message Text");
        }
    }

    public List<Message> getAllMessages () {
        return messageRepository.findAll ();
    }

    public Message getMessageById (Integer message_id) {

         return messageRepository.findById (message_id).orElse(null);
        
    }

    public Integer deleteMessageById (Integer message_id) {
        if (messageRepository.existsById (message_id)) {
            messageRepository.deleteById (message_id);
            return 1;
        }
        else
            return null;
    }

    public Integer updateMessageTextById (Integer message_id, String message_text) {
        if ((message_text != null) && (!message_text.isBlank()) && (message_text.length() <=255)) {
            Message message = messageRepository.findById (message_id).orElse(null);
            if (message != null) {
                message.setMessageText (message_text);
                messageRepository.save (message);
                return 1;
            }
        }

        return null;
    }

    public List<Message> getAllMessagesByUser (Integer account_id) {
        return messageRepository.findByPostedBy (account_id);
    }
 
}
