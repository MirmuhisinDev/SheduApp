package org.example.shedu.repository;

import org.example.shedu.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
//    Page<Chat> findAllAndDeletedFalse(PageRequest pageRequest);
    List<Chat> findAllByReceiverIdAndSenderIdAndDeletedFalse(Integer receiverId, Integer senderId);

}
