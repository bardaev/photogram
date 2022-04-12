package com.my.photogram.repository;

import com.my.photogram.entity.Photo;
import com.my.photogram.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IPhotoRepository extends PagingAndSortingRepository<Photo, Long> {

    Optional<Photo> getPhotoById(Long id);
    Page<Photo> findByUser(User user, Pageable pageable);

}
