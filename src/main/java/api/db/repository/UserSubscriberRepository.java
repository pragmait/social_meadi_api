package api.db.repository;

import api.db.User;
import api.db.UserSubscriber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriberRepository extends CrudRepository<UserSubscriber, Integer> {

    Optional<UserSubscriber> findByUserAndFriend(User user, User friend);

    List<UserSubscriber> findAllByUser(User user);


    List<UserSubscriber> findAllByUserAndIsFriend(User user, Boolean isFriend);

    List<UserSubscriber> findAllByFriend(User friend);

}
