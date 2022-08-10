package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.SourceConverter;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.SourceDTO;
import com.informatorio.infonews.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    public ResponseEntity<?> createSource(@RequestBody @Valid SourceDTO sourceDTO){
        Source source = sourceConverter.toEntity(sourceDTO);
        source = sourceRepository.save(source);
        return new ResponseEntity<>(sourceConverter.toDto(source), HttpStatus.CREATED);
    }

    @GetMapping("/source/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Source> source = sourceRepository.findById(id);
        if (source.isPresent()){
            return new ResponseEntity<>(sourceConverter.toDto(source.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
