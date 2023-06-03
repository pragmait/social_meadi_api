package api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность сообщение(dto)")
public class CorrespondenceDto {

    @Schema(description = "Идентификатор сообщения")
    private Integer id;

    @Schema(description = "Имя отправителя")
    private String nameSender;

    @Schema(description = "Текст сообщения")
    private String textMessage;

    @Schema(description = "Время отправки")
    private String timeDispatch;

}
