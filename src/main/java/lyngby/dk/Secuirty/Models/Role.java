package lyngby.dk.Secuirty.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lyngby.dk.Secuirty.Models.User;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name ="role")


public class Role {
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();

}
