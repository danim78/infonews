package com.informatorio.infonews.dto;

import java.util.List;

public class AuthorPageDTO {
    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;
    private List<AuthorDTO> authors;

    public AuthorPageDTO(int page, int size, Long totalElements, int totalPages, List<AuthorDTO> authors) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.authors = authors;
    }

    public AuthorPageDTO() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthorsDTO(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "AuthorPageDTO{" +
                "page=" + page +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", authorsDTO=" + authors +
                '}';
    }
}
