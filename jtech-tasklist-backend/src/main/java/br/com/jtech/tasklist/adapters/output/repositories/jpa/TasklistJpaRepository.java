package br.com.jtech.tasklist.adapters.output.repositories.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.protocols.TasklistEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TasklistJpaRepository extends JpaRepository<TasklistEntity, UUID> {
    
    List<TasklistEntity> findByUserId(UUID userId);
    
    boolean existsByUserIdAndTitle(UUID userId, String title);
    
}