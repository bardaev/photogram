package com.my.photogram.repository;

import com.my.photogram.entity.SubscriberId;
import com.my.photogram.entity.Subscribers;
import org.springframework.data.repository.CrudRepository;

public interface ISubscribersRepository extends CrudRepository<Subscribers, SubscriberId> {

}
