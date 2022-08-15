package br.com.cronos.redesocial.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "followers")
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User follower;
}
