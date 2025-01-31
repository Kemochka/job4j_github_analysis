package ru.job4j.github.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.job4j.github.analysis.model.Commit;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryCommits {
    private Long id;
    private String name;
    private String url;
    private String username;
    private List<Commit> commits = new ArrayList<>();
}
