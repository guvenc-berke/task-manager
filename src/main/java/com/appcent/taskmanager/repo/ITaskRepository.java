package com.appcent.taskmanager.repo;

import com.appcent.taskmanager.model.Task;
import com.appcent.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserAndIsCompleted(User user, Boolean isCompleted);

    Optional<Task> findByTitleAndIsCompletedAndUser(String title, Boolean isCompleted, User user);
}
