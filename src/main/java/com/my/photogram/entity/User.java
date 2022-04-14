package com.my.photogram.entity;

import com.my.photogram.validation.PasswordMatches;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.springframework.util.ResourceUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Entity
@Table(name = "users")
@PasswordMatches
public class User {

    public User() {
        try {
            File file = ResourceUtils.getFile("classpath:static/user.png");
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            this.avatar = buffer;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] avatar;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Username is required")
    private String password;

    private String description;

    @Transient
    @NotEmpty(message = "Password confirmation is required")
    private String passwordConfirmation;

    private LocalDateTime created = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhoto(Photo photo) {
        setPhoto(photo, false);
    }

    public void setPhoto(Photo photo, boolean otherSideHasBeenSet) {
        this.getPhotos().add(photo);
        if (otherSideHasBeenSet) {
            return;
        }
        photo.setUser(this, true);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", passwordConfirmation='" + passwordConfirmation + '\'' +
                ", created=" + created +
                '}';
    }
}
