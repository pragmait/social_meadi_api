package api.model.requestBody;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность ответ на дружбу(dto)")
public class SubcriberBoolDtoReq {

    @Schema(description = "Имя пользователя")
    private String nameUser;

    @Schema(description = "Имя пользователя предложившего дружбу")
    private String nameProposer;

    @Schema(description = "Ответ на запрос")
    private Boolean answer;
}
