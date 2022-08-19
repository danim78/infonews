package com.informatorio.infonews.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.informatorio.infonews.converter.SourceConverter;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.SourceDTO;
import com.informatorio.infonews.repository.SourceRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SourceControllerTest {

    @Mock
    private SourceRepository sourceRepository;

    @Mock
    private SourceConverter sourceConverter;

    @InjectMocks
    private SourceController sourceController;

    @Test
    void given_valid_source_id_when_find_source_by_id() {
        Source source = new Source();
        SourceDTO sourceDTO = new SourceDTO();

        when(sourceRepository.findById(1L)).thenReturn(Optional.of(source));
        when(sourceConverter.toDto(source)).thenReturn(sourceDTO);

        ResponseEntity<?> response = sourceController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}