package api.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_posts")
@Getter
@Setter
@NoArgsConstructor
public class UserPost {

    @Id
    @Column(name = "id_post")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text_post", nullable = false)
    private String text;


    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_change", nullable = false)
    private LocalDateTime dateChange;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public UserPost(String title, String text, byte[] image, LocalDateTime dateCreation, User user) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.dateCreation = dateCreation;
        this.dateChange = dateCreation;
        this.user = user;
    }
}
