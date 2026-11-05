package br.edu.ifpr.todo.service;

import br.edu.ifpr.todo.dto.TodoConclusaoDTO;
import br.edu.ifpr.todo.dto.TodoRequestDTO;
import br.edu.ifpr.todo.dto.TodoResponseDTO;
import br.edu.ifpr.todo.model.Todo;
import br.edu.ifpr.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public TodoResponseDTO inserir(TodoRequestDTO dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.title());
        todo.setDeadLine(dto.deadLine());
        todo.setCreatedAt(LocalDateTime.now()); // regra: sistema define a criação
        todo.setFinishedAt(null);               // regra: nasce sem conclusão
        return TodoResponseDTO.from(repository.save(todo));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public TodoResponseDTO concluir(Long id, TodoConclusaoDTO dto) {
        Todo todo = repository.findById(id).orElseThrow();
        todo.setFinishedAt(dto.finishedAt()); // altera somente finishedAt
        return TodoResponseDTO.from(repository.save(todo));
    }

    public List<TodoResponseDTO> listarConcluidas() {
        return repository.findByFinishedAtIsNotNull().stream()
                .map(TodoResponseDTO::from)
                .toList();
    }

    public List<TodoResponseDTO> listarPendentes() {
        return repository.findByFinishedAtIsNull().stream()
                .map(TodoResponseDTO::from)
                .toList();
    }
}
