package com.informatorio.infonews.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

public class SourceDTO {
    private Long id;
    @NotBlank
    private String name;
    private String code;
    private LocalDate createdAt = LocalDate.now();


    public SourceDTO(Long id, String name, String code, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        setCode();
        this.createdAt = createdAt;
    }

    public SourceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode() {
        this.code = this.name.toLowerCase().trim().replace(" ", "-");
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SourceDTO sourceDTO = (SourceDTO) o;
        return Objects.equals(id, sourceDTO.id) && Objects.equals(name, sourceDTO.name)
                && Objects.equals(code, sourceDTO.code) && Objects.equals(createdAt, sourceDTO.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, createdAt);
    }

    @Override
    public String toString() {
        return "SourceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
