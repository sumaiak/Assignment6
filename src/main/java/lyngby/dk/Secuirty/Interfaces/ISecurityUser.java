package lyngby.dk.Secuirty.Interfaces;

import lyngby.dk.Secuirty.Models.Role;

import java.util.Set;

public interface ISecurityUser {
    Set<String> getRolesAsStrings();
    boolean verifyPassword(String pw);
    void addRole(Role role);
    void removeRole(Role role);
}