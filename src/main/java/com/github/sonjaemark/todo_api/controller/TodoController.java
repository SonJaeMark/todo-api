package com.github.sonjaemark.todo_api.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.github.sonjaemark.todo_api.repository.TodoRepository;
import com.github.sonjaemark.todo_api.entity.Todo;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping("/")
    public String landingPage() {
        return "API is working!";
    }
    // CREATE TASK
    @PostMapping("/create-task")
    public Todo createTask(@RequestBody Map<String, String> body) {
        Todo todo = new Todo();
        todo.setTask(body.get("task"));
        // created_at and is_done will be set by @PrePersist
        return todoRepository.save(todo);
    }
    // GET ALL TASK
    @GetMapping("/get-all-task")
    public List<Todo> getAllTask() {
        return todoRepository.findAll();
    }

    // MARK AS DONE
    @PutMapping("/mark-as-done/{id}")
    public Todo markAsDone(@PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow();
        todo.setIs_done(true);
        return todoRepository.save(todo);
    }

    // UPDATE TASK
    @PutMapping("/update-task/{id}")
    public Todo updateTask(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow();
        todo.setTask(updatedTodo.getTask());
        return todoRepository.save(todo);
    }
}