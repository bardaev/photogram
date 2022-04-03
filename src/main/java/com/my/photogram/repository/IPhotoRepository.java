package com.my.photogram.repository;

import com.my.photogram.entity.Photo;
import org.springframework.data.repository.CrudRepository;

public interface IPhotoRepository extends CrudRepository<Photo, Long> {

}
