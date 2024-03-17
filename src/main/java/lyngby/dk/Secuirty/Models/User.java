package lyngby.dk.Secuirty.Models;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lyngby.dk.Secuirty.Interfaces.ISecurityUser;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="´user`")
public  class User implements ISecurityUser {


    @Id
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_name", referencedColumnName = "username")},
            inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    private Set<Role> roles = new HashSet<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        BCrypt.gensalt();
        String salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, salt);
    }

    @Override
    public Set<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        Set<String> rolesAsStrings = new HashSet<>();
        for (Role role : roles) {
            rolesAsStrings.add(role.getName());
        }
        return rolesAsStrings;
    }
    @Override
    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(password, this.password);

    }

    @Override
    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    @Override
    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);


    }

}

