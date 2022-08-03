package ai.codemap.codemap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleAlgorithmDto {
    private Long algorithmId;
    private String title;
    private String description;
}
