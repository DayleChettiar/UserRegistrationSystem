package com.apress.dayle.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="users")
public class UserInfo {

    @Id
    @GeneratedValue
    @Column(name="userId")
    private Long id;

    @Column(name="username")
    @NotEmpty
    private String username;

    @Column(name="password")
    @NotEmpty
    private String password;

    @Column(name="enabled")
    private boolean isEnabled;

    @Column(name="role")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
