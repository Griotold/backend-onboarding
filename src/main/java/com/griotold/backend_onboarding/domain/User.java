package com.griotold.backend_onboarding.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "p_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_USER", columnNames = {"username"})
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", length = 10, nullable = false)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    public static User create(String username, String password, Role role, String nickname) {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .nickname(nickname)
                .build();
    }

    public static User create(String username, String password, String nickname) {
        return create(username, password, Role.USER, nickname);
    }
}
