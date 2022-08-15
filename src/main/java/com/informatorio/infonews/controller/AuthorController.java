package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.AuthorConverter;
import com.informatorio.infonews.domain.Author;
import com.informatorio.infonews.dto.AuthorDTO;
import com.informatorio.infonews.dto.AuthorPageDTO;
import com.informatorio.infonews.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }

    // ALTA
    @PostMapping("/author")
    public ResponseEntity<?> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author author = authorConverter.toEntity(authorDTO);
        author = authorRepository.save(author);
        return new ResponseEntity<>(authorConverter.toDto(author), HttpStatus.CREATED);
    }

    // BAJA
    @PostMapping("/author/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // MODIFICACIÓN
    @PutMapping("/author/{id}/modify")
    public ResponseEntity<?> modifyById(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) {
        Optional<Author> wantedAuthor = authorRepository.findById(id);
        if (wantedAuthor.isPresent()) {
            Author authorToModify = wantedAuthor.get();
            Author author = authorConverter.toEntity(authorDTO);
            authorToModify.setFirstName(author.getFirstName());
            authorToModify.setLastName(author.getLastName());
            authorToModify.setFullName(author.getFullName());
            author = authorRepository.save(authorToModify);
            return new ResponseEntity<>(authorConverter.toDto(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // BUSCAR AUTOR POR ID
    @GetMapping("/author/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return new ResponseEntity<>(authorConverter.toDto(author.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // OBTENER TODOS LOS AUTORES
    @GetMapping("/author/all")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "5") @Positive int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = authorRepository.findAll(pageable);
        List<AuthorDTO> authorsPageDTO = authorPage.stream()
                .map(author -> authorConverter.toDto(author))
                .toList();
        AuthorPageDTO authorPageDTO = new AuthorPageDTO(authorPage.getNumber(),
                authorPage.getSize(),
                authorPage.getTotalElements(),
                authorPage.getTotalPages(),
                authorsPageDTO);
        // List<Author> authors = authorRepository.findAll();
        // return new ResponseEntity<>(authorConverter.toDto(authors), HttpStatus.OK);
        return new ResponseEntity<>(authorPageDTO, HttpStatus.OK);
    }

    // OBTENER LOS AUTORES CREADOS DESDE UNA FECHA DADA
    @GetMapping("/author/all/since")
    public ResponseEntity<?> findByCreatedAtSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PastOrPresent LocalDate date) {
        List<Author> authors = authorRepository.findByCreatedAtGreaterThanEqual(date);
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AuthorDTO> authorsDTO = authors.stream().map(author -> authorConverter.toDto(author))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(authorsDTO, HttpStatus.OK);
        }
    }

    // OBTENER LOS AUTORES CUYO FULLNAME CONTENGAN UN STRING
    @GetMapping("/author/all/fullname")
    public ResponseEntity<?> findByFullName(@RequestParam @Size(min = 3, max = 20) String q) {
        List<Author> authors = authorRepository.findByFullNameContaining(q);
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AuthorDTO> authorsDTO = authors.stream().map(author -> authorConverter.toDto(author))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(authorsDTO, HttpStatus.OK);
        }
    }
}