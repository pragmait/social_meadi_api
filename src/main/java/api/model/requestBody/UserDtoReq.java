package api.model.requestBody;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность пользователя для регистрации(dto)")
public class UserDtoReq {

    @Schema(description = "Имя пользователя")
    private String name;

    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Email пользователя")
    private String email;
}
