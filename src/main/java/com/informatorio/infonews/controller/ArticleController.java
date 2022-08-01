package com.informatorio.infonews.controller;

import com.informatorio.infonews.domain.Article;
import com.informatorio.infonews.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/article")
    public Article createArticle(@RequestBody Article article){
        return articleRepository.save(article);
    }
}
