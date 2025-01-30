package com.example.demo.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user_details", schema = "user_management", catalog= "h602116")
public class UserDetails {
    @Id
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private UserAuthentication userAuthentication;

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserAuthentication getUserAuthentication() {
        return userAuthentication;
    }

    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }
}
