package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Contest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemoryContestRepository implements ContestRepository{


    @Override
    public Contest read(int cid) {

        /* todo */


        return null;
    }

    @Override
    public List<Contest> getList() {

        /* todo */

        return null;
    }
}
