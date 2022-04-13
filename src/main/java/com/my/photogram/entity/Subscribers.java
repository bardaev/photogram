package com.my.photogram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(SubscriberId.class)
@Table(name = "subscribers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscribers implements Serializable {

    @Id
    @Column(name = "id_subscriber")
    private Long idSubscriber;
    @Id
    @Column(name = "id_author")
    private Long idAuthor;

}
