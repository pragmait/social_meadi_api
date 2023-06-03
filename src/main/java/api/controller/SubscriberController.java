package api.controller;

import api.core.service.SubscriberService;
import api.model.requestBody.SubcriberBoolDtoReq;
import api.model.requestBody.SubcriberDtoReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController()
@RequestMapping("/api/subs")
@Tag(name = "Подписки", description = "предложение дружба, отписка и т.д")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Operation(
            summary = "Запрос дружбы",
            description = "Отправка предложения дружбы"
    )
    @PostMapping("/friendship")
    public void requestFriendship(@Valid @RequestBody SubcriberDtoReq subcriber) {
        subscriberService.requestFriendship(subcriber.getNameUser(), subcriber.getNameProposer());
    }

    @Operation(
            summary = "Ответ на запрос об дружбе",
            description = "Ответ(да/нет) на запрос об дружбе от пользователя"
    )
    @PutMapping("/friendship/answer")
    public void responseFriendship(@Valid @RequestBody SubcriberBoolDtoReq subcriber) {
        subscriberService.responseFriendship(subcriber.getAnswer(), subcriber.getNameUser(), subcriber.getNameProposer());
    }

    @Operation(
            summary = "Удаление из друзей",
            description = "Удаление пользователя из списка друзей"
    )
    @PostMapping("/friendship/refusal")
    public void rejectionFriendship(@Valid @RequestBody SubcriberDtoReq subcriber) {
        subscriberService.rejectionFriendship(subcriber.getNameUser(), subcriber.getNameProposer());
    }

    @Operation(
            summary = "Отписка от пользователя"
    )
    @PostMapping("/cancellation")
    public void unsubscribe(@Valid @RequestBody SubcriberDtoReq subcriber) {
        subscriberService.unsubscribe(subcriber.getNameUser(), subcriber.getNameProposer());
    }

    @Operation(
            summary = "Получение списка подписчиков и друзей"
    )
    @GetMapping("/audit/{user}")
    public HashMap<String, Integer> getCountSubscribeAndFriend(@Valid @PathVariable(value = "user") @Parameter(description = "Имя пользователя") String userName) {
        return subscriberService.getCountSubscribeAndFriend(userName);
    }

}
