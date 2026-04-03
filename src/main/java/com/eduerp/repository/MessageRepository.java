package com.eduerp.repository;

import com.eduerp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId OR m.receiver.id = :userId) ORDER BY m.sentAt DESC")
    List<Message> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :u1 AND m.receiver.id = :u2) OR (m.sender.id = :u2 AND m.receiver.id = :u1) ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("u1") Long u1, @Param("u2") Long u2);

    long countBySenderIdAndReceiverIdAndIsReadFalse(Long senderId, Long receiverId);
}
