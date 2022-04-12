package com.my.photogram.service;

import com.my.photogram.entity.Photo;
import com.my.photogram.entity.User;
import org.springframework.data.domain.Page;

public interface IPhotoService {
    Photo uploadPhoto(Photo photo);
    Photo getPhoto(Long id);
    Page<Photo> getPhotos(User user, int page);
}
