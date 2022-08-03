package com.informatorio.infonews.dto;

import com.informatorio.infonews.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;

    private List<Article> articles = new ArrayList<>();

    public AuthorDTO(Long id, String firstName, String lastName, String fullName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
    }

    public AuthorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return Objects.equals(id, authorDTO.id) && Objects.equals(firstName, authorDTO.firstName) && Objects.equals(lastName, authorDTO.lastName) && Objects.equals(fullName, authorDTO.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, fullName);
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
