package br.edu.ifpr.todo.controller;

import br.edu.ifpr.todo.dto.TodoConclusaoDTO;
import br.edu.ifpr.todo.dto.TodoRequestDTO;
import br.edu.ifpr.todo.dto.TodoResponseDTO;
import br.edu.ifpr.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDTO> inserir(@RequestBody TodoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.inserir(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/conclusao")
    public ResponseEntity<TodoResponseDTO> concluir(@PathVariable Long id,
                                                    @RequestBody TodoConclusaoDTO dto) {
        return ResponseEntity.ok(service.concluir(id, dto));
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<TodoResponseDTO>> listarConcluidas() {
        return ResponseEntity.ok(service.listarConcluidas());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<TodoResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(service.listarPendentes());
    }
}
