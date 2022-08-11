package com.informatorio.infonews.converter;

import com.informatorio.infonews.domain.Article;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.ArticleDTO;
import com.informatorio.infonews.dto.SourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticleConverter {

    private final AuthorConverter authorConverter;
    private final SourceConverter sourceConverter;

    @Autowired
    public ArticleConverter(AuthorConverter authorConverter,SourceConverter sourceConverter) {
        this.authorConverter = authorConverter;
        this.sourceConverter = sourceConverter;
    }

    public ArticleDTO toDto(Article article){
        return new ArticleDTO(article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getContent(),
                authorConverter.toDto(article.getAuthor()),
                sourceConverter.toDto(article.getSource()));
    }

    public List<ArticleDTO> toDto(List<Article> articles) {
        return articles.stream()
                .map(article -> toDto(article))
                .collect(Collectors.toList());
    }

    public Article toEntity(ArticleDTO articleDTO){
        return new Article(articleDTO.getTitle(),
                articleDTO.getDescription(),
                articleDTO.getUrl(),
                articleDTO.getUrlToImage(),
                articleDTO.getPublishedAt(),
                articleDTO.getContent(),
                authorConverter.toEntity(articleDTO.getAuthor()));
    }

    public List<Article> toEntity(List<ArticleDTO> articlesDTO){
        return articlesDTO.stream()
                .map(articleDTO -> toEntity(articleDTO))
                .collect(Collectors.toList());
    }
}
