package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.KakaoInfo;
import ai.codemap.codemap.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaKakaoInfoRepository extends JpaRepository<KakaoInfo, Long>, KakaoInfoRespository{
}
