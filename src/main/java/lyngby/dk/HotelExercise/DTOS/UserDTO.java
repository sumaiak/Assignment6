package lyngby.dk.HotelExercise.DTOS;




import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lyngby.dk.Secuirty.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private Set<String> roles;


    public UserDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username, String[] roles) {
        this.username = username;
        this.roles = Set.of(roles);
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.roles = user.getRolesAsStrings();
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(new UserDTO(user.getUsername(), user.getRolesAsStrings().toArray(new String[0])));
        }
        return userDTOList;

    }

    public String getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        for (String role : roles) {
            sb.append(role).append(",");
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }

}