package com.sangam.Task.Management.repository;

import com.sangam.Task.Management.Entity.TasksDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TasksDetails, Long> {
}
