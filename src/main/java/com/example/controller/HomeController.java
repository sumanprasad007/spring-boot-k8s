package com.example.controller;

import com.example.model.BlogPost;
import com.example.model.ContactForm;
import com.example.service.BlogService;
import com.example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final ContactService contactService;
    private final BlogService blogService;

    @Autowired
    public HomeController(ContactService contactService, BlogService blogService) {
        this.contactService = contactService;
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        model.addAttribute("latestPosts", blogService.getLatestPosts(3));
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactForm contactForm, RedirectAttributes redirectAttributes) {
        contactService.saveContactForm(contactForm);
        redirectAttributes.addFlashAttribute("successMessage", "Thank you for your message! We will get back to you shortly.");
        return "redirect:/";
    }

    @GetMapping("/blog")
    public String blogList(@RequestParam(required = false) String category, Model model) {
        List<BlogPost> posts;
        if (category != null && !category.isEmpty()) {
            posts = blogService.getPublishedPostsByCategory(category);
            model.addAttribute("currentCategory", category);
        } else {
            posts = blogService.getAllPublishedPosts();
        }
        model.addAttribute("posts", posts);
        return "blog/list";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable Long id, Model model) {
        Optional<BlogPost> post = blogService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "blog/detail";
        } else {
            return "redirect:/blog";
        }
    }
}