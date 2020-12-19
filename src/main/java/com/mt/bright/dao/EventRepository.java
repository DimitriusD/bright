package com.mt.bright.dao;

import com.mt.bright.entity.Event;
import com.mt.bright.entity.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
