package com.informatorio.infonews.dto;

import java.util.List;

public class ArticlePageDTO {

    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;
    private List<ArticleDTO> articles;

    public ArticlePageDTO(int page, int size, Long totalElements, int totalPages, List<ArticleDTO> articles) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.articles = articles;
    }

    public ArticlePageDTO() {
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

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "ArticlePageDTO {" +
                ", page=" + page +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                "articles=" + articles +
                "}";
    }
}
