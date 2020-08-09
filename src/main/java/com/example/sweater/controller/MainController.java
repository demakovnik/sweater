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
        HashMap<Long, List<String>> filenames = new HashMap<>();
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

            model.addAttribute("filenames", filenames);

            model.addAttribute("messages", messages);

        }
        model.addAttribute("filenames", filenames);
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("add")
    public String add(
            @AuthenticationPrincipal User author,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("files") MultipartFile[] files) throws IOException {
        Message message = new Message(text, tag, author);
        Set<String> filenames = new HashSet<>();
        for (MultipartFile file : files) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                Path uploadDir = Files.createDirectories(Path.of(uploadPath, author.getUsername()));
                //File uploadDirectory = uploadDir.toFile();
            /*if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }*/
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + file.getOriginalFilename();
                file.transferTo(new File(uploadDir.toAbsolutePath() + "/" + resultFileName));
                filenames.add(resultFileName);
            }
        }
        message.setFilenames(filenames);
        messageRepository.save(message);
        return "redirect:/main";
    }

    @PostMapping("delete")
    public String delete(@RequestParam("messageId") Message message) throws IOException {
        messageRepository.delete(message);
        Set<String> filenames = message.getFilenames();
        for (String filefilename : filenames) {
            if (filefilename != null) {
                Path deletingPath = Path.of(uploadPath, message.getAuthorName(), filefilename);
                Files.deleteIfExists(deletingPath);
            }
        }

        return "redirect:/main";
    }
}
