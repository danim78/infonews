package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.ArticleConverter;
import com.informatorio.infonews.domain.Article;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.ArticleDTO;
import com.informatorio.infonews.repository.ArticleRepository;
import com.informatorio.infonews.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final SourceRepository sourceRepository;
    private final ArticleConverter articleConverter;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, SourceRepository sourceRepository,
            ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.sourceRepository = sourceRepository;
        this.articleConverter = articleConverter;
    }

    // ALTA
    @PostMapping("/article")
    public ResponseEntity<?> createArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        Article article = articleConverter.toEntity(articleDTO);
        article = articleRepository.save(article);
        return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.CREATED);
    }

    // BAJA
    @PostMapping("/article/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // MODIFICACIÓN
    @PutMapping("/article/{id}/modify")
    public ResponseEntity<?> modifyById(@PathVariable Long id, @RequestBody @Valid ArticleDTO articleDTO) {
        Optional<Article> wantedArticle = articleRepository.findById(id);
        if (wantedArticle.isPresent()) {
            Article articleToModify = wantedArticle.get();
            Article article = articleConverter.toEntity(articleDTO);
            articleToModify.setTitle(article.getTitle());
            articleToModify.setDescription(article.getDescription());
            articleToModify.setUrl(article.getUrl());
            articleToModify.setUrlToImage(article.getUrlToImage());
            articleToModify.setContent(article.getContent());
            articleToModify.setAuthor(article.getAuthor());
            articleToModify.setSources(article.getSource());
            article = articleRepository.save(articleToModify);
            return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // AGREGAR RECURSO A ARTICULO
    @PostMapping("/article/{idArticle}/source")
    public ResponseEntity<?> addSourceToArticle(@PathVariable Long idArticle, @RequestBody List<Long> sourceIds) {
        Article article = articleRepository.findById(idArticle).orElse(null);
        List<Source> sources = sourceIds.stream()
                .map(id -> sourceRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        sources.forEach(source -> article.addSource(source));
        articleRepository.save(article);
        return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.ACCEPTED);
    }

    // OBTENER TODOS LOS ARTICULOS PUBLICADOS
    // @GetMapping("/article/all")
    // public ResponseEntity<?> findAllPublished(){
    // List<Article> articles = articleRepository.findAllByPublishedAtIsNotNull();
    // return new ResponseEntity<>(articleConverter.toDto(articles),
    // HttpStatus.ACCEPTED);
    // }
    // OBTENER TODOS LOS ARTICULOS PUBLICADOS QUE CONTENGAN STRING
    @GetMapping("/article/all")
    public ResponseEntity<?> findByTitleOrDescriptionOrContentOrFullName(
            @RequestParam(required = false) @Size(min = 3, max = 20) String q) {
        if (q != null) {
            List<Article> articles = articleRepository
                    .findByPublishedAtIsNotNullAndTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullNameContaining(q, q, q,
                            q);

            List<ArticleDTO> articleDTO = articles.stream()
                    .map(article -> articleConverter.toDto(article))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(articleDTO, HttpStatus.OK);
        } else { 
            List<Article> articles = articleRepository.findAllByPublishedAtIsNotNull();
            return new ResponseEntity<>(articleConverter.toDto(articles), HttpStatus.ACCEPTED);
        }

        // List<Article> articlesPublished =
        // articleRepository.findAllByPublishedAtIsNotNull();
        // List<Article> articles =
        // articleRepository.findByTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullNameContaining(q,
        // q, q, q);

        // List<ArticleDTO> articleDTO = articles.stream()
        // .map(article -> articleConverter.toDto(article))
        // .collect(Collectors.toList());
        // return new ResponseEntity<>(articleDTO, HttpStatus.OK);

        // if(articles.publishedAt.isEmpty()) {
        // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // } else {
        // List<AuthorDTO> authorsDTO = authors.stream().
        // map(author -> authorConverter.toDto(author))
        // .collect(Collectors.toList());
        // return new ResponseEntity<>(authorsDTO, HttpStatus.OK);
        // }
    }
}
