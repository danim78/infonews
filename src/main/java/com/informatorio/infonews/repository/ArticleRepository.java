package com.informatorio.infonews.repository;

import com.informatorio.infonews.domain.Article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByPublishedAtIsNotNull();
    List<Article> findByPublishedAtIsNotNullAndTitleContainingOrPublishedAtIsNotNullAndDescriptionContainingOrPublishedAtIsNotNullAndContentContainingOrPublishedAtIsNotNullAndAuthorFullNameContaining(String str, String str1, String str2, String str3);
}
