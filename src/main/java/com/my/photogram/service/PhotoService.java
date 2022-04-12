package com.my.photogram.service;

import com.my.photogram.entity.Photo;
import com.my.photogram.entity.User;
import com.my.photogram.repository.IPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PhotoService implements IPhotoService {

    @Autowired
    private IPhotoRepository photoRepository;

    @Override
    public Photo uploadPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public Photo getPhoto(Long id) {
        return photoRepository.getPhotoById(id).orElse(null);
    }

    @Override
    public Page<Photo> getPhotos(User user, int page) {
        Pageable pageable = PageRequest.of(page, 9, Sort.by("id").ascending());
        return photoRepository.findByUser(user, pageable);
    }
}
