package com.informatorio.infonews.converter;

import com.informatorio.infonews.domain.Author;
import com.informatorio.infonews.dto.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {
    public AuthorDTO toDto(Author author){
        return new AuthorDTO(author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getFullName(),
                author.getCreatedAt());
    }

    public Set<AuthorDTO> toDto(Set <Author> authors){
        return authors.stream()
                .map(author -> toDto(author))
                .collect(Collectors.toSet());
    }

    public Author toEntity(AuthorDTO authorDTO){
        return new Author(authorDTO.getFirstName(),
                authorDTO.getLastName(),
                authorDTO.getFullName(),
                authorDTO.getCreatedAt());
    }

    public Set<Author> toEntity(Set<AuthorDTO> authorsDTO){
        return authorsDTO.stream()
                .map(authorDTO -> toEntity(authorDTO))
                .collect(Collectors.toSet());
    };
}
