package br.edu.ifpr.todo.dto;

import java.time.LocalDateTime;

// Entrada do PATCH /api/todos/{id}/conclusao: apenas a data de conclusão.
public record TodoConclusaoDTO(LocalDateTime finishedAt) {
}
