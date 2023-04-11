package br.com.cronos.redesocial.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String state;
    private String city;
    private String number;
    private String zipcode;
    private String district;
    private String complement;
    @OneToOne
    private User user;
    @CreationTimestamp
    private LocalDateTime creation;
    @UpdateTimestamp
    private LocalDateTime updated;
}
