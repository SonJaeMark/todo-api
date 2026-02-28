package com.github.sonjaemark.todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.sonjaemark.todo_api.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}