package ai.codemap.codemap.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class KakaoInfo {
    @Id
    private Long Id;
    @Column(name = "image")
    private String image;

}
