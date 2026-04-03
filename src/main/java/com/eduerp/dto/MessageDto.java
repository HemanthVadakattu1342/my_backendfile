package com.eduerp.dto;

import com.eduerp.entity.Message;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private String body;
    private Boolean isRead;
    private LocalDateTime sentAt;

    public static MessageDto from(Message m) {
        MessageDto dto = new MessageDto();
        dto.setId(m.getId());
        dto.setBody(m.getBody());
        dto.setIsRead(m.getIsRead());
        dto.setSentAt(m.getSentAt());
        if (m.getSender() != null) {
            dto.setSenderId(m.getSender().getId());
            dto.setSenderName(m.getSender().getName());
        }
        if (m.getReceiver() != null) {
            dto.setReceiverId(m.getReceiver().getId());
            dto.setReceiverName(m.getReceiver().getName());
        }
        return dto;
    }
}
