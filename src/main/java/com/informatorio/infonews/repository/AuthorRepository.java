package com.informatorio.infonews.repository;

import com.informatorio.infonews.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByCreatedAtGreaterThanEqual(LocalDate date);

    List<Author> findByFullNameContaining(String str);

    Optional<Author> findByFullName(String str);
}
