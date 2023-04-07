package br.com.cronos.redesocial.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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
    @OneToOne
    private User user;
    @CreationTimestamp
    private LocalDateTime creation;
    @UpdateTimestamp
    private LocalDateTime updated;
}
