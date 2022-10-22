package ai.codemap.codemap.service;

import ai.codemap.codemap.dto.*;
import ai.codemap.codemap.model.*;
import ai.codemap.codemap.repository.ContestRepository;
import ai.codemap.codemap.repository.ProblemSetRepository;
import ai.codemap.codemap.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScoreboardService {

    private final ProblemSetRepository problemSetRepository;
    private final ContestRepository contestRepository;
    private final SubmissionRepository submissionRepository;

    @Autowired
    public ScoreboardService(ProblemSetRepository problemSetRepository, ContestRepository contestRepository, SubmissionRepository submissionRepository) {
        this.problemSetRepository = problemSetRepository;
        this.contestRepository = contestRepository;
        this.submissionRepository = submissionRepository;
    }

    public ScoreboardContest getScoreboardContest(Long problemSetId) {
        ScoreboardContest contest = new ScoreboardContest();
        ProblemSet problemSet = problemSetRepository.findById(problemSetId);
        contest.setTitle(problemSet.getTitle());
        contest.setSystemName("Codemap");
        contest.setSystemVersion("");
        List<String> titles = problemSet.getProblemSetProblems().stream()
                .map(ProblemSetProblem::getProblem)
                .map(Problem::getTitle)
                .toList();
        List<ScoreboardProblem> problems = new ArrayList<>();
        for (int i = 0; i < titles.size(); ++i) {
            ScoreboardProblem problem = new ScoreboardProblem();
            problem.setId((long) i);
            problem.setName(Character.toString('A' + i));
            problem.setTitle(titles.get(i));
            problem.setColor("sample" + i);
        }
        contest.setProblems(problems);
        List<ScoreboardTeam> teams = contestRepository.getUsersByProblemSetId(problemSetId).stream()
                .map(user -> new ScoreboardTeam(user.getUserId(), user.getNickname() + " (" + user.getUsername() + ")"))
                .toList();
        contest.setTeams(teams);
        return contest;
    }

    public ScoreboardRuns getScoreboardRuns(Long problemSetId) {
        ScoreboardRuns scoreboardRuns = new ScoreboardRuns();
        ProblemSet problemSet = problemSetRepository.findById(problemSetId);
        ScoreboardTime scoreboardTime = new ScoreboardTime();
        scoreboardTime.setContestTime(problemSet.getDuration());
        scoreboardTime.setNoMoreUpdate(false);
        scoreboardTime.setTimeStamp(0L);
        scoreboardRuns.setTime(scoreboardTime);

        Map<Long, Long> problemIdMap = new HashMap<>();
        List<Long> problemIds = problemSet.getProblemSetProblems().stream()
                .map(ProblemSetProblem::getProblem)
                .map(Problem::getProblemId)
                .toList();
        for (int i = 0; i < problemIds.size(); ++i) {
            problemIdMap.put(problemIds.get(i), (long) i);
        }

        List<Contest> contests = contestRepository.findByProblemSetId(problemSetId);
        Map<Long, Long> map = new HashMap<>();
        for (Contest c : contests) {
            map.put(c.getUser().getUserId(), c.getContestId());
        }
        List<ScoreboardRun> runs = new ArrayList<>();
        for (var entry : map.entrySet()) {
            List<Submission> submissions = submissionRepository.findByUserIdAndContestId(entry.getKey(), entry.getValue());
            for (Submission submission : submissions) {
                if (submission.getTestMode()) {
                    continue;
                }
                ScoreboardRun run = new ScoreboardRun();
                run.setId(submission.getSubmissionId());
                run.setTeam(submission.getUserId());
                run.setResult(submission.getResult().equals("ACCEPTED") ? "Yes" : "No");
                run.setProblem(problemIdMap.get(submission.getProblemId()));
                run.setSubmissionTime(1L);
                runs.add(run);
            }
        }
        scoreboardRuns.setRuns(runs);

        return scoreboardRuns;
    }
}
