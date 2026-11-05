package br.edu.ifpr.todo.dto;

import java.time.LocalDateTime;

// Entrada do POST /api/todos: apenas título e prazo.
public record TodoRequestDTO(String title, LocalDateTime deadLine) {
}
