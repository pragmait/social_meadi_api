package api.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_chat")
@Getter
@Setter
@NoArgsConstructor
public class UserChat {

    @Id
    @Column(name = "id_message")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text_message", nullable = false)
    private String textMessage;

    @Column(name = "time_dispatch", nullable = false)
    private LocalDateTime timeDispatch;

    @ManyToOne
    @JoinColumn(name = "id_recipient_user", nullable = false)
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "id_sender_user", nullable = false)
    private User user;

    public UserChat(String textMessage, LocalDateTime timeDispatch, User recipient, User user) {
        this.textMessage = textMessage;
        this.timeDispatch = timeDispatch;
        this.recipient = recipient;
        this.user = user;
    }
}
