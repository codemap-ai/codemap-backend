package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Contest;

import java.util.List;

public interface ContestRepository {
    Contest findById(int contestId);
    List<Contest> findAll();
}
