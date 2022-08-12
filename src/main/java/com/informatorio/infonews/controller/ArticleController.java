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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final SourceRepository sourceRepository;
    private final ArticleConverter articleConverter;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, SourceRepository sourceRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.sourceRepository = sourceRepository;
        this.articleConverter = articleConverter;
    }
    //ALTA
    @PostMapping("/article")
    public ResponseEntity<?> createArticle(@RequestBody @Valid ArticleDTO articleDTO){
        Article article = articleConverter.toEntity(articleDTO);
        article = articleRepository.save(article);
        return new ResponseEntity<>(articleConverter.toDto(article), HttpStatus.CREATED);
    }
    //BAJA
    @PostMapping("/article/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()){
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //MODIFICACIÓN
    @PutMapping("/article/{id}/modify")
    public ResponseEntity<?> modifyById(@PathVariable Long id, @RequestBody @Valid ArticleDTO articleDTO){
        Optional<Article> wantedArticle = articleRepository.findById(id);
        if (wantedArticle.isPresent()){
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

    //AGREGAR RECURSO A ARTICULO
    @PostMapping("/article/{idArticle}/source")
    public ResponseEntity<?> addSourceToArticle(@PathVariable Long idArticle, @RequestBody List<Long> sourceIds){
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

    //OBTENER TODOS LOS ARTICULOS
    @GetMapping("/article/all")
    public ResponseEntity<?> findAll(){
        List<Article> articles = articleRepository.findAll();
        return new ResponseEntity<>(articleConverter.toDto(articles), HttpStatus.ACCEPTED);
    }
}
