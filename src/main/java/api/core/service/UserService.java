package api.core.service;

import api.db.User;
import api.db.repository.UserRepository;
import api.model.requestBody.UserDtoReq;
import api.model.requestBody.UserLoginDtoReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRep;


    public void registration(UserDtoReq newUser) {
        try {
            newUser.setPassword(getMd5(newUser.getPassword()).toUpperCase());
            userRep.save(new User(newUser.getName(), newUser.getPassword(), newUser.getEmail()));
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint \"user_social_media_user_name_key\"")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, String.format("Данное имя (%s) пользователя занято.", newUser.getName())
                );
            }
            if (e.getMessage().contains("duplicate key value violates unique constraint \"user_social_media_email_key\"")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, String.format("Данный email (%s) уже зарегистрирован.", newUser.getEmail())
                );
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Ошибка при регистриции"
            );
        }
    }

    public String loginUser(UserLoginDtoReq login) {
        User user = getUser(login.getName());
        if (getMd5(login.getPassword()).toUpperCase().equals(user.getPassword())) {
            return "Вы вошли в систему";
        } else {
            return "Не верное имя пользователя или пароль";
        }
    }

    public User getUser(String nameUser) {
        Optional<User> user = userRep.findByName(nameUser);
        if (user.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Пользователь не найден"
            );
        }
        return user.get();
    }

    private static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
