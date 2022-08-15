package br.com.cronos.redesocial.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
