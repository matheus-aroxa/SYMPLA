package com.miromori.project_sympla_entrega_2.repositories;

import com.miromori.project_sympla_entrega_2.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
}
