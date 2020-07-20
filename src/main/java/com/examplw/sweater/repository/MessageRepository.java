package com.examplw.sweater.repository;

import com.examplw.sweater.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    void deleteById(Long id);
    List<Message> findByTag(String tag);
}
