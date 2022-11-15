package ai.codemap.codemap.service;

import ai.codemap.codemap.dto.*;
import ai.codemap.codemap.model.*;
import ai.codemap.codemap.repository.ContestRepository;
import ai.codemap.codemap.repository.ProblemSetRepository;
import ai.codemap.codemap.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
            problems.add(problem);
        }
        contest.setProblems(problems);
        List<ScoreboardTeam> unmod_teams = contestRepository.getUsersByProblemSetId(problemSetId).stream()
                .map(user -> new ScoreboardTeam(user.getUserId(), user.getNickname() + " (" + user.getUsername() + ")"))
                .toList();
        List<ScoreboardTeam> teams = new ArrayList<>(unmod_teams);
        if (problemSet.getProblemSetId() == 28L) {
            teams.add(new ScoreboardTeam(1000000000L, "qwerty1234 (qwerty1234)"));
            teams.add(new ScoreboardTeam(1000000001L, "OoOoOo0 (OoOoOo0)"));
            teams.add(new ScoreboardTeam(1000000002L, "ARCRAN (ARCRAN)"));
            teams.add(new ScoreboardTeam(1000000003L, "jeonmyunghoon (jeonmyunghoon)"));
            teams.add(new ScoreboardTeam(1000000004L, "silver_alloy (silver_alloy)"));
            teams.add(new ScoreboardTeam(1000000005L, "DeanJ (DeanJ)"));
            teams.add(new ScoreboardTeam(1000000006L, "__oww__ (__oww__)"));
            teams.add(new ScoreboardTeam(1000000007L, "__int128_t (__int128_t)"));
            teams.add(new ScoreboardTeam(1000000008L, "WriteInGo (WriteInGo)"));
            teams.add(new ScoreboardTeam(1000000009L, "rustecean (rustecean)"));
            teams.add(new ScoreboardTeam(1000000010L, "math0000 (math0000)"));
            teams.add(new ScoreboardTeam(1000000011L, "arcticfox (arcticfox)"));
            teams.add(new ScoreboardTeam(1000000012L, "paran0idAndr0id (paran0idAndr0id)"));
            teams.add(new ScoreboardTeam(1000000013L, "FakePlasticTrees (FakePlasticTrees)"));
            teams.add(new ScoreboardTeam(1000000014L, "ao19990823 (ao19990823)"));
            teams.add(new ScoreboardTeam(1000000015L, "1289io (1289io)"));
            teams.add(new ScoreboardTeam(1000000016L, "Gauss77 (Gauss77)"));
            teams.add(new ScoreboardTeam(1000000017L, "00000000 (00000000)"));
            teams.add(new ScoreboardTeam(1000000018L, "only (only)"));
            teams.add(new ScoreboardTeam(1000000019L, "ACL (ACL)"));
        }
        contest.setTeams(teams);
        return contest;
    }

    public ScoreboardRuns getScoreboardRuns(Long problemSetId) {
        ScoreboardRuns scoreboardRuns = new ScoreboardRuns();
        ProblemSet problemSet = problemSetRepository.findById(problemSetId);
        ScoreboardTime scoreboardTime = new ScoreboardTime();
        scoreboardTime.setContestTime(problemSet.getDuration() * 60L); // min -> sec
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
        if (problemSet.getProblemSetId() == 28) {
            runs.addAll(dummy());
        }
        scoreboardRuns.setRuns(runs);
        return scoreboardRuns;
    }

    List<ScoreboardRun> dummy() {
        List<ScoreboardRun> result = new ArrayList<>();

        result.add(new ScoreboardRun(1000000000L, 1000000000L, 0L, "NO", 1L));
        result.add(new ScoreboardRun(1000000001L, 1000000000L, 0L, "YES", 1L));
        result.add(new ScoreboardRun(1000000002L, 1000000000L, 1L, "YES", 1L));
        result.add(new ScoreboardRun(1000000003L, 1000000000L, 2L, "NO", 1L));
        result.add(new ScoreboardRun(1000000004L, 1000000000L, 3L, "YES", 1L));
        result.add(new ScoreboardRun(1000000005L, 1000000000L, 4L, "NO", 1L));
        result.add(new ScoreboardRun(1000000006L, 1000000000L, 5L, "YES", 1L));

        result.add(new ScoreboardRun(1000000007L, 1000000001L, 7L, "NO", 1L));
        result.add(new ScoreboardRun(1000000008L, 1000000001L, 0L, "YES", 1L));
        result.add(new ScoreboardRun(1000000009L, 1000000001L, 1L, "YES", 1L));
        result.add(new ScoreboardRun(1000000010L, 1000000001L, 2L, "YES", 1L));
        result.add(new ScoreboardRun(1000000011L, 1000000001L, 3L, "YES", 1L));
        result.add(new ScoreboardRun(1000000012L, 1000000001L, 4L, "YES", 1L));
        result.add(new ScoreboardRun(1000000013L, 1000000001L, 5L, "YES", 1L));
        result.add(new ScoreboardRun(1000000014L, 1000000001L, 9L, "YES", 1L));
        result.add(new ScoreboardRun(1000000015L, 1000000001L, 8L, "YES", 1L));
        result.add(new ScoreboardRun(1000000016L, 1000000001L, 7L, "YES", 1L));

        result.add(new ScoreboardRun(1000000017L, 1000000002L, 0L, "YES", 1L));
        result.add(new ScoreboardRun(1000000018L, 1000000002L, 1L, "YES", 1L));
        result.add(new ScoreboardRun(1000000019L, 1000000002L, 2L, "YES", 1L));
        result.add(new ScoreboardRun(1000000020L, 1000000002L, 3L, "YES", 1L));
        result.add(new ScoreboardRun(1000000021L, 1000000002L, 4L, "YES", 1L));
        result.add(new ScoreboardRun(1000000022L, 1000000002L, 5L, "YES", 1L));
        result.add(new ScoreboardRun(1000000023L, 1000000002L, 6L, "YES", 1L));
        result.add(new ScoreboardRun(1000000024L, 1000000002L, 7L, "YES", 1L));
        result.add(new ScoreboardRun(1000000025L, 1000000002L, 8L, "YES", 1L));
        result.add(new ScoreboardRun(1000000026L, 1000000002L, 9L, "YES", 1L));

        result.add(new ScoreboardRun(1000000027L, 1000000003L, 0L, "YES", 1L));
        result.add(new ScoreboardRun(1000000028L, 1000000003L, 1L, "YES", 1L));
        result.add(new ScoreboardRun(1000000029L, 1000000003L, 2L, "YES", 1L));
        result.add(new ScoreboardRun(1000000030L, 1000000003L, 3L, "NO", 1L));
        result.add(new ScoreboardRun(1000000031L, 1000000003L, 3L, "YES", 1L));
        result.add(new ScoreboardRun(1000000032L, 1000000003L, 5L, "YES", 1L));
        result.add(new ScoreboardRun(1000000033L, 1000000003L, 6L, "YES", 1L));
        result.add(new ScoreboardRun(1000000034L, 1000000003L, 7L, "NO", 1L));
        result.add(new ScoreboardRun(1000000035L, 1000000003L, 8L, "NO", 1L));
        result.add(new ScoreboardRun(1000000036L, 1000000003L, 9L, "NO", 1L));

        result.add(new ScoreboardRun(1000000037L, 1000000004L, 0L, "YES", 1L));
        result.add(new ScoreboardRun(1000000038L, 1000000004L, 1L, "YES", 1L));
        result.add(new ScoreboardRun(1000000039L, 1000000004L, 2L, "YES", 1L));
        result.add(new ScoreboardRun(1000000040L, 1000000004L, 3L, "YES", 1L));

        Random rand = new Random();
        rand.setSeed(0L);

        for (int i = 0; i < 200; ++i) {
            long id = 1000000041L + i;
            long team = 1000000000L + rand.nextLong(0, 20);
            long problem = rand.nextLong(0, 10);
            String result_ = rand.nextBoolean() ? "YES" : "NO";
            result.add(new ScoreboardRun(id, team, problem, result_, 1L));
        }

        return result;
    }
}
