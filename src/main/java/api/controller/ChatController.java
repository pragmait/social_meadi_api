package api.controller;

import api.core.service.ChatService;
import api.model.CorrespondenceDto;
import api.model.requestBody.MessageDtoReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Чаты", description = "Отправка сообщений, получение чата")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Operation(
            summary = "Отправка сообщения",
            description = "Отправка сообщения другому пользователю (другу)"
    )
    @PostMapping("/message/send")
    public void sendMessage(@Valid @RequestBody MessageDtoReq message) {
        chatService.sendMessage(message.getNameSender(), message.getNameRecipient(), message.getTextMessage());
    }

    @Operation(
            summary = "Получить чат пользоателя",
            description = "Получить чат между двумя пользователями(друзьями)"
    )
    @GetMapping("/listchat/{sender}/{recipient}")
    public List<CorrespondenceDto> getListChat(@Valid @PathVariable(value = "sender") @Parameter(description = "Имя пользователя") String nameSender,
                                               @Valid @PathVariable(value = "recipient") @Parameter(description = "Имя друга(собеседника-пользователь)") String nameRecipient) {
        return chatService.getMessages(nameSender, nameRecipient);
    }

}
