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
@Schema(description = "Сущность подписка(dto)")
public class SubcriberDtoReq {

    @Schema(description = "Имя пользователя")
    private String nameUser;

    @Schema(description = "Имя польщователя на кого подписываются")
    private String nameProposer;
}
