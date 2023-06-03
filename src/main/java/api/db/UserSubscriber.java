package api.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_subscriber")
@Getter
@Setter
@NoArgsConstructor
public class UserSubscriber {

    @Id
    @Column(name = "id_subscriber")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_friend", nullable = false)
    private User friend;

    @Column(name = "is_friend", nullable = false)
    private Boolean isFriend;

    @Column(name = "is_request_friendship", nullable = false)
    private Boolean isRequestFriendship;

    @Column(name = "is_subscriber", nullable = false)
    private Boolean isSubscriber;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public UserSubscriber(User friend, Boolean isFriend, Boolean isRequestFriendship, Boolean isSubscriber, User user) {
        this.friend = friend;
        this.isFriend = isFriend;
        this.isRequestFriendship = isRequestFriendship;
        this.isSubscriber = isSubscriber;
        this.user = user;
    }
}
