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

    // Let JPA handle default, nullable to avoid binding issues
    @Column(nullable = true)
    private LocalDateTime created_at;

    @Column(nullable = true)
    private Boolean is_done;

    @Column(nullable = false)
    private String task;

    // Automatically set defaults before saving
    @PrePersist
    protected void onCreate() {
        if (created_at == null) {
            created_at = LocalDateTime.now();
        }
        if (is_done == null) {
            is_done = false;
        }
    }
}