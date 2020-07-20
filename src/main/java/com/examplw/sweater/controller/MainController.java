package com.examplw.sweater.controller;

import com.examplw.sweater.domain.Message;
import com.examplw.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {

    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("add")
    public String add(@RequestParam String text, @RequestParam String tag,
                      Map<String, Object> model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam String id, Map<String, Object> model) {
        messageRepository.deleteById(Long.parseLong(id));
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String tag, Map<String, Object> model) {
        Collection<Message> messages;
        if (tag != null && !tag.isEmpty()) {
            messages = new ArrayList<>();
            String[] tags = tag.trim().split(",");
            Arrays.stream(tags).forEach(s -> {
                messages.addAll(messageRepository.findByTag(s.trim()));
                model.put("messages", messages);
            });
        } else {
            messages = messageRepository.findAll();
            model.put("messages", messages);

        }

        return "main";
    }


}
