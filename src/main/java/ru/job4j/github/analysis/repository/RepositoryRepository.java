package ru.job4j.github.analysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;
import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    @NonNull
    List<Repository> findAll();

    Optional<Repository> findByName(String name);
}
