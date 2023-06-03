package api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность поста(dto)")
public class PostDto {

    @Schema(description = "Имя пользователя")
    private String userName;

    @Schema(description = "Заголовок поста")
    private String title;

    @Schema(description = "Основной текст поста")
    private String textPost;

    @Schema(description = "Изображение")
    private byte[] image;

    @Schema(description = "Дата создания поста")
    private LocalDateTime dateCreation;
}
