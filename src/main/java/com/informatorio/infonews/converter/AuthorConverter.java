package com.informatorio.infonews.converter;

import com.informatorio.infonews.domain.Author;
import com.informatorio.infonews.dto.AuthorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {
    public AuthorDTO toDto(Author author){
        return new AuthorDTO(author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getFullName());
    }

    public List <AuthorDTO> toDto(List <Author> authors){
        return authors.stream()
                .map(author -> toDto(author))
                .collect(Collectors.toList());
    }
}
