package api.controller;

import api.core.service.UserService;
import api.model.requestBody.UserDtoReq;
import api.model.requestBody.UserLoginDtoReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользователь", description = "регистрация, вход")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Регистрация пользователя"
    )
    @PostMapping("/registration")
    public void registrationUser(@Valid @RequestBody UserDtoReq userDto) {
        userService.registration(userDto);
    }

    @Operation(
            summary = "Авторизация пользователя"
    )
    @PostMapping("/login")
    public String entranceUser(@Valid @RequestBody UserLoginDtoReq login) {
        return userService.loginUser(login);
    }
}
