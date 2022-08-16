package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.ArticleConverter;
import com.informatorio.infonews.domain.Article;
import com.informatorio.infonews.domain.Author;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.ArticleDTO;
import com.informatorio.infonews.dto.ArticlePageDTO;
import com.informatorio.infonews.dto.AuthorDTO;
import com.informatorio.infonews.repository.ArticleRepository;
import com.informatorio.infonews.repository.AuthorRepository;
import com.informatorio.infonews.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final SourceRepository sourceRepository;
    private final ArticleConverter articleConverter;
    private final AuthorRepository authorRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, SourceRepository sourceRepository,
            ArticleConverter articleConverter, AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.sourceRepository = sourceRepository;
        this.articleConverter = articleConverter;
        this.authorRepository = authorRepository;
    }

    // ALTA
    @PostMapping("/article")
    public ResponseEntity<?> createArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        AuthorDTO authorDTO = articleDTO.getAuthor();
        if (authorDTO != null) {
            Optional<Author> author = authorRepository.findByFullName(authorDTO.getFullName());
            if (author.isPresent()) {
                Article articleToCreate = articleConverter.toEntity(articleDTO);
                articleToCreate.setAuthor(author.get());
                Article article = articleRepository.save(articleToCreate);
                return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        Optional<Author> wantedAuthor = authorRepository.findByFullName(articleDTO.getAuthor().getFullName());

        List<Source> wantedSources = articleDTO.getSources().stream()
                .map(source -> sourceRepository.findById(source.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());

        if (wantedArticle.isPresent()) {
            Article articleToModify = wantedArticle.get();
            Article article = articleConverter.toEntity(articleDTO);
            articleToModify.setTitle(article.getTitle());
            articleToModify.setDescription(article.getDescription());
            articleToModify.setUrl(article.getUrl());
            articleToModify.setUrlToImage(article.getUrlToImage());
            articleToModify.setContent(article.getContent());
            articleToModify.setPublishedAt(article.getPublishedAt());
            articleToModify.setSources(wantedSources);

            if (wantedAuthor.isPresent()){
                articleToModify.setAuthor(wantedAuthor.get());
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            article = articleRepository.save(articleToModify);
            return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // AGREGAR FUENTE A ARTICULO
    @PostMapping("/article/{idArticle}/source")
    public ResponseEntity<?> addSourceToArticle(@PathVariable Long idArticle, @RequestBody List<Long> sourceIds) {
        Optional<Article> article = articleRepository.findById(idArticle);
        List<Source> sourcesArticle = article.get().getSource();
        List<Source> sourcesAdd = sourceIds.stream()
                .map(sourceRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Predicate.not(sourcesArticle::contains))
                .distinct()
                .collect(Collectors.toList());

        Article articleNew = article.get();
        sourcesAdd.forEach(source -> articleNew.addSource(source));
        articleRepository.save(articleNew);
        return new ResponseEntity<>(articleConverter.toDto(articleNew), HttpStatus.ACCEPTED);
    }

    // OBTENER TODOS LOS ARTICULOS PUBLICADOS O QUE CONTENGAN STRING
    @GetMapping("/article/all")
    public ResponseEntity<?> findByTitleOrDescriptionOrContentOrFullName(
            @RequestParam(required = false) @Size(min = 3, max = 20) String q,
            @RequestParam(defaultValue = "0") @PositiveOrZero int page,
            @RequestParam(defaultValue = "2") @Positive int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (q != null) {
            Page<Article> articlePage = articleRepository
                    .findByPublishedAtIsNotNullAndTitleContainingOrPublishedAtIsNotNullAndDescriptionContainingOrPublishedAtIsNotNullAndContentContainingOrPublishedAtIsNotNullAndAuthorFullNameContaining(
                            q, q, q,
                            q, pageable);

            List<ArticleDTO> articlesPageDTO = articlePage.stream()
                    .map(article -> articleConverter.toDto(article))
                    .toList();
            ArticlePageDTO articlePageDTO = new ArticlePageDTO(
                    articlePage.getNumber(),
                    articlePage.getSize(),
                    articlePage.getTotalElements(),
                    articlePage.getTotalPages(),
                    articlesPageDTO);
            return new ResponseEntity<>(articlePageDTO, HttpStatus.OK);
        } else {
            Page<Article> articlePage = articleRepository.findAllByPublishedAtIsNotNull(pageable);
            List<ArticleDTO> articlesPageDTO = articlePage.stream()
                    .map(article -> articleConverter.toDto(article))
                    .toList();
            ArticlePageDTO articlePageDTO = new ArticlePageDTO(articlePage.getNumber(),
                    articlePage.getSize(),
                    articlePage.getTotalElements(),
                    articlePage.getTotalPages(),
                    articlesPageDTO);

            return new ResponseEntity<>(articlePageDTO, HttpStatus.OK);
        }
    }
}
