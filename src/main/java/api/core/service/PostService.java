package api.core.service;

import api.db.User;
import api.db.UserPost;
import api.db.UserSubscriber;
import api.db.repository.UserPostRepository;
import api.db.repository.UserSubscriberRepository;
import api.mapping.ApiMapper;
import api.model.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPostRepository userPostRep;

    @Autowired
    private UserSubscriberRepository userSubscriberRep;

    @Autowired
    private ApiMapper apiMapper;


    public List<PostDto> getActivityFeed(String userName, Pageable paging, LocalDateTime date) {
        User user = userService.getUser(userName);
        List<UserSubscriber> subscribers = userSubscriberRep.findAllByFriend(user);
        ArrayList<Integer> userSubscriptions = new ArrayList<>();
        for (UserSubscriber subscriber : subscribers) {
            userSubscriptions.add(subscriber.getUser().getId());
        }
        Page<UserPost> pagePosts = userPostRep.findAllByUserIn(userSubscriptions, date, paging);
        return apiMapper.postsToDto(pagePosts.getContent());
    }

    public PostDto getPost(Integer idPost) {
        try {
            Optional<UserPost> post = userPostRep.findById(idPost);
            if (post.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Не найден пост"
                );
            }
            return apiMapper.postToDto(post.get());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public PostDto createPost(String userName, String titlePost, String textPost, MultipartFile image) {
        User user = userService.getUser(userName);
        try {
            UserPost userPost = new UserPost(titlePost, textPost, image.getBytes(), LocalDateTime.now(), user);
            UserPost post = userPostRep.save(userPost);
            PostDto postDto = apiMapper.postToDto(post);
            return postDto;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public void editPost(Integer idPost, String userName, String titlePost, String textPost, MultipartFile image) {
        User user = userService.getUser(userName);
        Optional<UserPost> userPost = userPostRep.findById(idPost);
        if (userPost.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Не найден пост"
            );
        }
        try {
            userPost.get().setTitle(titlePost);
            userPost.get().setText(textPost);
            userPost.get().setDateChange(LocalDateTime.now());
            if (!image.isEmpty()) userPost.get().setImage(image.getBytes());
            userPostRep.save(userPost.get());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    public void deletePost(Integer idPost) {
        Optional<UserPost> userPost = userPostRep.findById(idPost);
        if (userPost.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Не найден пост"
            );
        }
        try {
            userPostRep.delete(userPost.get());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

}
