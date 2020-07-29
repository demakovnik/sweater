package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String userList(@AuthenticationPrincipal User loggedUser,
                           Model model) {
        model.addAttribute("userList",userRepository.findAll());
        model.addAttribute("loggedUser",loggedUser);
        model.addAttribute("adminRole",Role.ADMIN);
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user",user);
        Set<Role> roles = Arrays.stream(Role.values())
                .collect(Collectors.toSet());
        model.addAttribute("roles", roles);
        return "userEdit";
    }

    @PostMapping
    public String saveUser(
            @RequestParam("userId") User user,
            @RequestParam String username,
            @RequestParam Map<String,String> form
            ) {
        user.setUsername(username);

        Set<Role> userRoles = user.getRoles();
        userRoles.clear();
        Set<String> rolesFromForm = form.keySet();
        for (Role role: Role.values()) {
            if (rolesFromForm.contains(role.name())) {
                userRoles.add(role);
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("userId") User user
                         ) {
        userRepository.delete(user);
        return "redirect:/user";
    }
}
