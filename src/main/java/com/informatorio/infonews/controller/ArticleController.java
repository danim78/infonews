package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.ArticleConverter;
import com.informatorio.infonews.domain.Article;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.ArticleDTO;
import com.informatorio.infonews.repository.ArticleRepository;
import com.informatorio.infonews.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/article")
    public Article createArticle(@RequestBody Article article){
        return articleRepository.save(article);
    }

    @PostMapping("/article/{idArticle}/source")
    public ArticleDTO addSourceToArticle(@PathVariable Long idArticle, @RequestBody List<Long> sourceIds){
       Article article = articleRepository.findById(idArticle).orElse(null);
       List<Source> sources = sourceIds.stream()
               .map(id -> sourceRepository.findById(id))
               .filter(Optional::isPresent)
               .map(Optional::get)
               .collect(Collectors.toList());
       sources.forEach(source -> article.addSource(source));
       articleRepository.save(article);
       return articleConverter.toDto(article);
    }
}
