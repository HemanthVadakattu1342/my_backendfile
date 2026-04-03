package com.eduerp.service;

import com.eduerp.dto.*;
import com.eduerp.entity.*;
import com.eduerp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    public List<MessageDto> getMyMessages(String email) {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return messageRepo.findAllByUserId(user.getId())
            .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageDto> getConversation(Long otherUserId, String email) {
        User me = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return messageRepo.findConversation(me.getId(), otherUserId)
            .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public MessageDto send(MessageRequest req, String senderEmail) {
        User sender = userRepo.findByEmail(senderEmail)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepo.findById(req.getReceiverId())
            .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message message = Message.builder()
            .sender(sender)
            .receiver(receiver)
            .body(req.getBody())
            .build();
        return MessageDto.from(messageRepo.save(message));
    }

    public void markRead(Long messageId, String readerEmail) {
        User reader = userRepo.findByEmail(readerEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Message msg = messageRepo.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        if (msg.getReceiver().getId().equals(reader.getId())) {
            msg.setIsRead(true);
            messageRepo.save(msg);
        }
    }
}
