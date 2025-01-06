package ru.job4j.github.analysis.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;

import java.util.List;

public interface CommitRepository extends CrudRepository<Commit, Long> {
    @Query("""
            select commit from Commit as commit join Repository as r where r.name = :name
            """)
    List<Commit> findAllByRepositoryName(@Param("name") String name);

    Commit findTopByRepositoryOrderByDateDesc(Repository repository);
}
