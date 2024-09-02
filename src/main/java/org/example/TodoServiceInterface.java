package org.example;

import java.io.IOException;

public interface TodoServiceInterface {
    void printTodos(int number);
    void createTodo(Todo todo);
    Todo getTodo(int todoId);
    void deleteTodoAsync(int todoId);
    void updateTodo(Todo todo);
}
