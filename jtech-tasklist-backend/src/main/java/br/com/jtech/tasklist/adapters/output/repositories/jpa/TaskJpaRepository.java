package br.com.jtech.tasklist.adapters.output.repositories.jpa;

import br.com.jtech.tasklist.adapters.output.repositories.protocols.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {

    @Query("SELECT t FROM TaskEntity t WHERE t.tasklist.id = :tasklistId")
    List<TaskEntity> findByTasklistId(@Param("tasklistId") UUID tasklistId);

    @Query("SELECT COUNT(t) > 0 FROM TaskEntity t WHERE t.tasklist.id = :tasklistId AND t.title = :title")
    boolean existsByTasklistIdAndTitle(@Param("tasklistId") UUID tasklistId, @Param("title") String title);

}

