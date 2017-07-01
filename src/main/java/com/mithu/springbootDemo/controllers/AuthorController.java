package com.mithu.springbootDemo.controllers;

import com.mithu.springbootDemo.model.Author;
import com.mithu.springbootDemo.repository.AuthorMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mithu on 25/6/17.
 */
@Controller
public class AuthorController {

    @Autowired
    AuthorMongoRepository authorMongoRepository;

    @RequestMapping("/listAuthors")
    public String list(Model model) {
        model.addAttribute("authorList", authorMongoRepository.findAll());
        return "/author/list";
    }

    @RequestMapping(value = {"/createAuthor", "/updateAuthor"}, method = RequestMethod.GET)
    public String createAuthor(Model model, @RequestParam(required = false) String id) {
        Author author;
        if (id != null) {
            author = authorMongoRepository.findOne(id);
            System.out.println("${author}" + author.toString());

        } else {
            author = new Author();
        }
        model.addAttribute("author", author);
        return "/author/createOrUpdate";
    }

    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
    public String addAuthor(@ModelAttribute Author author) {
        String authorId = author.getId();

        if (  authorId != null && !authorId.isEmpty()) {
            System.out.println("if>>>");
            Author existingAuthor = authorMongoRepository.findOne(authorId);
            existingAuthor = author;
            authorMongoRepository.save(existingAuthor);
        } else {
            authorMongoRepository.save(author);
        }

        return "redirect:listAuthors";
    }

    @RequestMapping(value = "/deleteAuthor", method = RequestMethod.GET)
    public String deleteAuthor(@RequestParam String id) {
        authorMongoRepository.delete(id);
        return "redirect:listAuthors";

    }

}
