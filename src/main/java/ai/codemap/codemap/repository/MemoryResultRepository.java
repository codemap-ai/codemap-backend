package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemoryResultRepository implements ResultRepository{

    @Override
    public Result read(int rid) {

        /* todo */

        return null;
    }

    @Override
    public List<Result> getList() {

        /* todo */

        return null;
    }

    @Override
    public List<Result> getByPid(int pid) {

        /* todo */

        return null;
    }

    @Override
    public List<Result> getByUid(int uid) {

        /* todo */

        return null;
    }
}
