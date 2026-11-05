package br.edu.ifpr.todo.repository;

import br.edu.ifpr.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByFinishedAtIsNotNull();

    List<Todo> findByFinishedAtIsNull();
}
