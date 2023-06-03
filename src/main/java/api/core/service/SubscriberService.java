package api.core.service;

import api.db.User;
import api.db.UserSubscriber;
import api.db.repository.UserSubscriberRepository;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    @Autowired
    private UserSubscriberRepository userSubscriberRep;

    @Autowired
    UserService userService;

    public void requestFriendship(String nameUser, String nameProposer) {
        User user = userService.getUser(nameUser);
        User friend = userService.getUser(nameProposer);
        UserSubscriber newSubscriber = new UserSubscriber(user, false, true, true, friend);
        try {
            userSubscriberRep.save(newSubscriber);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public void responseFriendship(Boolean answer, String nameUser, String nameProposer) {
        User user = userService.getUser(nameUser);
        User friend = userService.getUser(nameProposer);
        Optional<UserSubscriber> subscriber = userSubscriberRep.findByUserAndFriend(user, friend);
        if (subscriber.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Не найдено запроса на дружбу"
            );
        }
        try {
            if (answer) {
                subscriber.get().setIsFriend(true);
                subscriber.get().setIsRequestFriendship(false);
                UserSubscriber newSubscriber = new UserSubscriber(user, true, false, true, friend);
                userSubscriberRep.save(newSubscriber);
            } else {
                subscriber.get().setIsFriend(false);
                subscriber.get().setIsRequestFriendship(false);
            }
            userSubscriberRep.save(subscriber.get());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public void rejectionFriendship(String nameUser, String nameProposer) {
        User user = userService.getUser(nameUser);
        User friend = userService.getUser(nameProposer);
        Optional<UserSubscriber> friendSubscriber = userSubscriberRep.findByUserAndFriend(friend, user);
        Optional<UserSubscriber> mySubscriber = userSubscriberRep.findByUserAndFriend(user, friend);
        if (friendSubscriber.isEmpty() || mySubscriber.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Не найдено подписки"
            );
        }
        try {
            mySubscriber.get().setIsFriend(false);
            userSubscriberRep.save(mySubscriber.get());
            userSubscriberRep.delete(friendSubscriber.get());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public void unsubscribe(String nameUser, String nameProposer) {
        User user = userService.getUser(nameUser);
        User friend = userService.getUser(nameProposer);
        Optional<UserSubscriber> subscriber = userSubscriberRep.findByUserAndFriend(friend, user);
        if (subscriber.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Не найдено подписки"
            );
        }
        try {
            userSubscriberRep.delete(subscriber.get());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public HashMap<String, Integer> getCountSubscribeAndFriend(String nameUser) {
        User user = userService.getUser(nameUser);
        List<UserSubscriber> subscribers = userSubscriberRep.findAllByUser(user);
        List<UserSubscriber> friends = userSubscriberRep.findAllByUserAndIsFriend(user, true);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("subscribers", subscribers.size());
        map.put("friends", friends.size());
        return map;
    }
}
