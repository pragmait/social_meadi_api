package api.core.service;

import api.db.User;
import api.db.UserChat;
import api.db.UserSubscriber;
import api.db.repository.UserChatRepository;
import api.db.repository.UserSubscriberRepository;
import api.mapping.ApiMapper;
import api.model.CorrespondenceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserChatRepository chatRep;

    @Autowired
    private UserSubscriberRepository userSubscriberRep;

    @Autowired
    private ApiMapper apiMapper;

    public void sendMessage(String senderName, String recipientName, String text) {
        User sender = userService.getUser(senderName);
        User recipient = userService.getUser(recipientName);
        Optional<UserSubscriber> subscriber = userSubscriberRep.findByUserAndFriend(sender, recipient);
        if (subscriber.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Подписка не найден"
            );
        }
        if (!subscriber.get().getIsFriend()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Отправка сообщения только для друзей"
            );
        }
        UserChat newMessage = new UserChat(text, LocalDateTime.now(), recipient, sender);
        try {
            chatRep.save(newMessage);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public List<CorrespondenceDto> getMessages(String userName, String nameCompanion) {
        User user = userService.getUser(userName);
        User companion = userService.getUser(nameCompanion);
        List<UserChat> correspondence = chatRep.findAllByUserAndRecipientOrUserAndRecipient(user, companion, companion, user);
        return apiMapper.userChatsToDto(correspondence);
    }
}
