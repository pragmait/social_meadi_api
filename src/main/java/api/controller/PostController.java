package api.controller;

import api.core.service.PostService;
import api.model.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping(value = "/api/post", produces = "application/json")
@Tag(name = "Посты пользователя", description = "Создание, изминения, удаление постов и лента активности")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(
            summary = "Лента активности",
            description = "Лента активности пользователей"
    )
    @GetMapping("/feed/{user}")
    public List<PostDto> getActivityFeed(@PathVariable(value = "user") @Parameter(description = "Имя пользователя") String nameUser,
                                         @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы") Integer pageNo,
                                         @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") @Parameter(description = "Сортировка по времени") LocalDateTime date
    ) {
        int pageSize = 3;
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return postService.getActivityFeed(nameUser, paging, date);
    }

    @Operation(
            summary = "Получить пост",
            description = "Получить пост по id"
    )
    @GetMapping("/{id}")
    public PostDto getPost(@Valid @PathVariable(value = "id") @Parameter(description = "Идентификатор поста") Integer idPost) {
        return postService.getPost(idPost);
    }

    @Operation(
            summary = "Создать пост"
    )
    @PostMapping(value = "/create")
    public PostDto createPost(@RequestParam("image") @Parameter(description = "Изображение") MultipartFile image,
                              @RequestParam("userName") @Parameter(description = "Имя пользователя") String userName,
                              @RequestParam("title") @Parameter(description = "Заголовок поста") String title,
                              @RequestParam("textPost") @Parameter(description = "Текст поста") String textPost) {
        return postService.createPost(userName, title, textPost, image);
    }

    @Operation(
            summary = "Изменить пост"
    )
    @PutMapping("/change/{id}")
    public void editPost(@Valid @PathVariable(value = "id") @Parameter(description = "Идентификатор поста") Integer idPost,
                         @RequestParam("image") @Parameter(description = "Изображение") MultipartFile image,
                         @RequestParam("userName") @Parameter(description = "Имя пользователя") String userName,
                         @RequestParam("title") @Parameter(description = "Заголовок поста") String title,
                         @RequestParam("textPost") @Parameter(description = "Текст поста") String textPost) {
        postService.editPost(idPost, userName, title, textPost, image);
    }

    @Operation(
            summary = "Удалить пост"
    )
    @DeleteMapping("/remove/{id}")
    public void deletePost(@Valid @PathVariable(value = "id") @Parameter(description = "Идентификатор поста") Integer idPost) {
        postService.deletePost(idPost);
    }
}
