package br.edu.ifpr.todo.dto;

import br.edu.ifpr.todo.model.Todo;

import java.time.LocalDateTime;

// Saída da API: inclui finishedAt para o cliente conferir a data registrada.
public record TodoResponseDTO(
        Long id,
        String title,
        LocalDateTime deadLine,
        LocalDateTime createdAt,
        LocalDateTime finishedAt) {

    public static TodoResponseDTO from(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDeadLine(),
                todo.getCreatedAt(),
                todo.getFinishedAt());
    }
}
