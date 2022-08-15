package com.informatorio.infonews.repository;

import com.informatorio.infonews.domain.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByPublishedAtIsNotNull(Pageable pageable);

    Page<Article> findByPublishedAtIsNotNullAndTitleContainingOrPublishedAtIsNotNullAndDescriptionContainingOrPublishedAtIsNotNullAndContentContainingOrPublishedAtIsNotNullAndAuthorFullNameContaining(
            String str, String str1, String str2, String str3, Pageable pageable);
}
