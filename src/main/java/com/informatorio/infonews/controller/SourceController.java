package com.informatorio.infonews.controller;

import com.informatorio.infonews.converter.SourceConverter;
import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.SourceDTO;
import com.informatorio.infonews.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
public class SourceController {

    private final SourceRepository sourceRepository;
    private final SourceConverter sourceConverter;

    @Autowired
    public SourceController(SourceRepository sourceRepository, SourceConverter sourceConverter) {
        this.sourceRepository = sourceRepository;
        this.sourceConverter = sourceConverter;
    }

    // ALTA
    @PostMapping("/source")
    public ResponseEntity<?> createSource(@RequestBody @Valid SourceDTO sourceDTO) {
        Optional<Source> wantedSource = sourceRepository.findByCode(sourceDTO.getCode());
        if (wantedSource.isPresent()) {
            return new ResponseEntity<>(sourceConverter.toDto(wantedSource.get()), HttpStatus.OK);
        } else {
            Source newAuthor = sourceConverter.toEntity(sourceDTO);
            newAuthor = sourceRepository.save(newAuthor);
            return new ResponseEntity<>(sourceConverter.toDto(newAuthor), HttpStatus.CREATED);
        }
    }

    // BAJA
    @PostMapping("/source/{id}/delete")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Source> author = sourceRepository.findById(id);
        if (author.isPresent()) {
            sourceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // MODIFICACIÓN
    @PutMapping("/source/{id}/modify")
    public ResponseEntity<?> modifyById(@PathVariable Long id, @RequestBody @Valid SourceDTO sourceDTO) {
        Optional<Source> wantedSource = sourceRepository.findById(id);
        if (wantedSource.isPresent()) {
            Source sourceToModify = wantedSource.get();
            Source source = sourceConverter.toEntity(sourceDTO);
            sourceToModify.setName(source.getName());
            sourceToModify.setCode(source.getCode());
            source = sourceRepository.save(sourceToModify);
            return new ResponseEntity<>(sourceConverter.toDto(source), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // BUSCAR SOURCE POR ID
    @GetMapping("/source/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Source> source = sourceRepository.findById(id);
        if (source.isPresent()) {
            return new ResponseEntity<>(sourceConverter.toDto(source.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // OBTENER TODOS LOS SOURCES
    @GetMapping("/source/all")
    public ResponseEntity<?> findAll() {
        List<Source> sources = sourceRepository.findAll();
        return new ResponseEntity<>(sourceConverter.toDto(sources), HttpStatus.OK);
    }

    // OBTENER LOS SOURCES CUYO FULLNAME CONTENGAN UN STRING
    @GetMapping("/source/all/name")
    public ResponseEntity<?> findByFullName(@RequestParam @Size(min = 2, max = 20) String str) {
        List<Source> sources = sourceRepository.findByNameContaining(str);
        if (sources.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<SourceDTO> sourcesDTO = sources.stream().map(source -> sourceConverter.toDto(source))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(sourcesDTO, HttpStatus.OK);
        }
    }
}
