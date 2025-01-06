package ru.job4j.github.analysis.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ScheduledTasks {
    private final RepositoryService repositoryService;
    private final GitHubService gitHubService;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void fetchCommits() {
        var repositories = repositoryService.findAll();
        for (var repository : repositories) {
            var latestCommit = repositoryService.findLatestCommit(repository);
            var lastCommitSha = latestCommit != null ? latestCommit.getSha() : null;
            var commits = gitHubService.fetchNewCommits(repository.getUserName(), repository.getName(), lastCommitSha);
            if (!commits.isEmpty()) {
                for (var commit : commits) {
                    commit.setRepository(repository);
                    repositoryService.create(commit);
                }
            }
        }
    }
}
