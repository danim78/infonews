package com.informatorio.infonews.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.informatorio.infonews.domain.Article;
import com.informatorio.infonews.repository.ArticleRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleController articleController;
    

    @Test
    void given_valid_article_id_when_delete_article() {
        Article article = new Article();

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        ResponseEntity<?> response = articleController.deleteById(1L);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
