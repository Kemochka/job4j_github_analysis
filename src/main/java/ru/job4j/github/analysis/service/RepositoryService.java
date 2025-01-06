package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.job4j.github.analysis.dto.RepositoryCommits;
import ru.job4j.github.analysis.model.Commit;
import ru.job4j.github.analysis.model.Repository;
import ru.job4j.github.analysis.repository.CommitRepository;
import ru.job4j.github.analysis.repository.RepositoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repository;
    private final CommitRepository commitRepository;
    private final GitHubService gitHubService;

    public List<Repository> findAll() {
        return this.repository.findAll();
    }

    public Optional<Repository> findByName(String name) {
        return repository.findByName(name);
    }

    public Commit findLatestCommit(Repository repository) {
        return commitRepository.findTopByRepositoryOrderByDateDesc(repository);
    }

    public List<Commit> findCommitsByRepository(String name) {
        return commitRepository.findAllByRepositoryName(name);
    }

    @Async
    public void create(Repository newRepository) {
        repository.save(newRepository);
    }

    @Async
    public void createForUser(String userName) {
        List<Repository> repositories = gitHubService.fetchRepositories(userName);
        for (Repository rep : repositories) {
            rep.setUserName(userName);
            repository.save(rep);
        }
    }
    @Async
    public void create(Commit newCommit) {
        commitRepository.save(newCommit);
    }

    public List<RepositoryCommits> getCommitsByUserName(String userName) {
        List<RepositoryCommits> result = new ArrayList<>();
        gitHubService.fetchRepositories(userName).stream().map(rep -> new RepositoryCommits(
                        rep.getId(), rep.getName(), rep.getUrl(), rep.getUserName(), findCommitsByRepository(rep.getName())))
                .forEach(result::add);
        return result;
    }
}
