package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Controller
public class MainController {

    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {

        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User author,
                       @RequestParam(required = false, defaultValue = "")
                               String filter,
                       Model model) {
        Collection<Message> messages;
        model.addAttribute("author", author);
        model.addAttribute("roleAdmin", Role.ADMIN);
        if (filter != null && !filter.isEmpty()) {
            messages = new ArrayList<>();
            String[] tags = filter.trim().split(",");
            Arrays.stream(tags).forEach(s -> {
                messages.addAll(messageRepository.findByTag(s.trim()));
                model.addAttribute("messages", messages);

            });
        } else {
            messages = messageRepository.findAll();
            model.addAttribute("messages", messages);

        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("add")
    public String add(
            @AuthenticationPrincipal User author,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file) throws IOException {
        Message message = new Message(text, tag, author);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFilename(resultFileName);
        }
        messageRepository.save(message);
        return "redirect:/main";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("messageId") Message message) throws IOException {
        messageRepository.delete(message);
        Files.deleteIfExists(Path.of(uploadPath + "/" + message.getFilename()));
        return "redirect:/main";
    }
}
