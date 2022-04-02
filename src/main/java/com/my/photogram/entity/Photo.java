package com.my.photogram.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_photo")
    private Long id;

    private String description;

    private LocalDateTime created = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        setUser(user, false);
    }

    public void setUser(User user, boolean otherSideHasBeenSet) {
        this.user = user;
        if (otherSideHasBeenSet) {
            return;
        }
        user.addPhoto(this, true);
    }
}
