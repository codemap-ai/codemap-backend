package ai.codemap.codemap.repository;

import ai.codemap.codemap.model.KakaoInfo;

import java.util.Optional;

public interface KakaoInfoRespository {
    Optional<KakaoInfo> findById(Long Id);
    KakaoInfo save(KakaoInfo kakaoInfo);
}
