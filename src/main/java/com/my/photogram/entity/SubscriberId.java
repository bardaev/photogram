package com.my.photogram.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberId implements Serializable {
    private Long idSubscriber;
    private Long idAuthor;
}
