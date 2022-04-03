package com.my.photogram.service;

import com.my.photogram.entity.Photo;
import com.my.photogram.repository.IPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService implements IPhotoService {

    @Autowired
    private IPhotoRepository photoRepository;

    @Override
    public Photo uploadPhoto(Photo photo) {
        return photoRepository.save(photo);
    }
}
