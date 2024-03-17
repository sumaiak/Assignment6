package lyngby.dk.Secuirty.Interfaces;

import io.javalin.validation.ValidationException;
import lyngby.dk.Secuirty.Models.Role;
import lyngby.dk.Secuirty.Models.User;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    Role createRole(String role);
    User addUserRole(String username, String role);
}