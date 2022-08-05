package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.AuthorConverter;
import com.informatorio.infonews.domain.Author;
import com.informatorio.infonews.dto.AuthorDTO;
import com.informatorio.infonews.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;



    @Autowired
    public AuthorController(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    @PostMapping("/author")
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO){
        Author author = authorConverter.toEntity(authorDTO);
        author = authorRepository.save(author);
        return authorConverter.toDto(author);
    }
}
