package com.informatorio.infonews.repository;

import com.informatorio.infonews.domain.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
    List<Source> findByNameContaining(String str);

    Optional<Source> findByCode(String str);
}
