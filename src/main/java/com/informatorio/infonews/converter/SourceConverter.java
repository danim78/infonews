package com.informatorio.infonews.converter;

import com.informatorio.infonews.domain.Source;
import com.informatorio.infonews.dto.SourceDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SourceConverter {

    public SourceDTO toDto(Source source){
        return new SourceDTO(source.getId(),
                source.getName(),
                source.getCode(),
                source.getCreatedAt(),
                source.getArticles());
    }

    public Set<SourceDTO> toDto(Set<Source> sources){
        return sources.stream()
                .map(source -> toDto(source))
                .collect(Collectors.toSet());
    }
}
