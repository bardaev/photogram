package com.my.photogram.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberId implements Serializable {
    @Column(name = "id_subscriber")
    private Long idSubscriber;
    @Column(name = "id_author")
    private Long idAuthor;
}
