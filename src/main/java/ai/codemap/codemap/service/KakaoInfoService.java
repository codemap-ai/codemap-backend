package ai.codemap.codemap.service;

import ai.codemap.codemap.model.KakaoInfo;
import ai.codemap.codemap.repository.KakaoInfoRespository;

public class KakaoInfoService {
    private KakaoInfoRespository kakaoInfoRespository;

    public KakaoInfoService(KakaoInfoRespository kakaoInfoRespository){
        this.kakaoInfoRespository = kakaoInfoRespository;
    }

    public KakaoInfo getById(Long Id) {
        return kakaoInfoRespository.findById(Id).get();
    }

    public KakaoInfo addKakaoInfo(KakaoInfo kakaoInfo){
        return kakaoInfoRespository.save(kakaoInfo);
    }
}
