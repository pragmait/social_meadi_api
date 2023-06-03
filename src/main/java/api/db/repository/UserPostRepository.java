package api.db.repository;

import api.db.User;
import api.db.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserPostRepository extends PagingAndSortingRepository<UserPost, Integer>, CrudRepository<UserPost, Integer> {


    @Query(value = "from UserPost where user.id in ?1 and dateCreation < ?2 order by dateCreation desc ")
    Page<UserPost> findAllByUserIn(List<Integer> userIds, LocalDateTime data,
                                 Pageable pageable);


}
