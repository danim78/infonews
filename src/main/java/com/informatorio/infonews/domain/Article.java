package com.informatorio.infonews.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private LocalDate publishedAt;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Author author;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "article_source",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "source_id"))
    private List<Source> sources = new ArrayList<>();

    public Article(String title, String description, String url, String urlToImage, LocalDate publishedAt, String content, Author author) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.author = author;
    }

    public Article(String title, String description, String url, String urlToImage, LocalDate publishedAt, String content, Author author, List<Source> sources) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.author = author;
        this.sources = sources;
    }

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Source> getSource() {
        return sources;
    }

    public void addSource(Source source){
        sources.add(source);
        source.getArticles().add(this);
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(url, article.url) && Objects.equals(urlToImage, article.urlToImage) && Objects.equals(publishedAt, article.publishedAt) && Objects.equals(content, article.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, url, urlToImage, publishedAt, content);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt=" + publishedAt +
                ", content='" + content + '\'' +
                ", author=" + author +
                '}';
    }
}
