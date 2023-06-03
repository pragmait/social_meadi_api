package api.db.repository;

import api.db.User;
import api.db.UserChat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRepository extends CrudRepository<UserChat, Integer> {

    List<UserChat> findAllByUserAndRecipientOrUserAndRecipient(User user, User recipient, User sender, User recipientSernder);
}
