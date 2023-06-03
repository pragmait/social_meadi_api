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
@Schema(description = "Сущность сообщение(dto)")
public class MessageDtoReq {

    @Schema(description = "Имя отправителя")
    private String nameSender;

    @Schema(description = "Текст сообщения")
    private String textMessage;

    @Schema(description = "Имя получателя")
    private String nameRecipient;
}
