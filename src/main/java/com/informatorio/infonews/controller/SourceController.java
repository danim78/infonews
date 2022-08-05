package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.SourceConverter;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.SourceDTO;
import com.informatorio.infonews.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceController {

    private final SourceRepository sourceRepository;
    private final SourceConverter sourceConverter;

    @Autowired
    public SourceController(SourceRepository sourceRepository, SourceConverter sourceConverter) {
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
    }

    @PostMapping("/source")
    public SourceDTO createSource(@RequestBody SourceDTO sourceDTO){
        Source source = sourceConverter.toEntity(sourceDTO);
        source = sourceRepository.save(source);
        return sourceConverter.toDto(source);
    }
}
