package com.miromori.project_sympla_entrega_2.repositories;

import com.miromori.project_sympla_entrega_2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.id = (SELECT s.userId FROM Subscription s WHERE s.eventId = (SELECT f.event.id FROM Feedback f WHERE f.id = :feedbackId))")
    User findUserByFeedback(Long feedbackId);
}
