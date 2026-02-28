package com.github.sonjaemark.todo_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created_at;

    private Boolean is_done;

    private String task;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
        is_done = false;
    }
}