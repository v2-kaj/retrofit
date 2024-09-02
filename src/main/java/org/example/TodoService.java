package org.example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

public class TodoService implements TodoServiceInterface {

    private final JsonPlaceholderApi api;

    public TodoService(JsonPlaceholderApi api) {
        this.api = api;
    }

    // implementation of the getTodo method
    public Todo getTodo(int todoId) {
        try {
            Call<Todo> call = api.getTodo(todoId);
            Response<Todo> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // implementation of printTodos that sends a get request to the API and print the specified number of todos
    public void printTodos(int number){
        Call<List<Todo>> call = api.getAllTodos();
        try {
            // Execute the call synchronously
            Response<List<Todo>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                // Get the list of todos
                List<Todo> todos = response.body();
                if(number<=0){
                    System.out.println();
                }
                // if number is greater than the number of todos the print all the todos
                else  if(number>=todos.size()){
                    for (Todo todo : todos) {
                        System.out.println(todo);
                    }
                }
                else{
                    // print the first number of todos
                    for (int i = 0; i < number; i++) {
                        System.out.println(todos.get(i));
                    }
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // implementation of the create todo method
    public void createTodo(Todo todo){
        Call<Todo> call = api.createTodo(todo);
        try{
            Response<Todo> response = call.execute();
            if(response.isSuccessful()){
                Todo createdTodo = response.body();
                System.out.println(createdTodo);
                System.out.println("Todo has been created successfully");
            } else {
                System.out.println("Request failed: " + response.code());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // implement the update method
    public void updateTodo(Todo todo){
        Todo t = getTodo(todo.getId());
        if(t!=null){
            // if user did not type anything in the title of the
            // lets assume he/she wants to maintain the title
            if(todo.getTitle().isEmpty()){
                todo.setTitle(t.getTitle());
            }
            try{
                Call<Todo> call = api.updateTodo(todo.getId(), todo);
                Response<Todo> response = call.execute();
                    if(response.isSuccessful()){
                        Todo updatedTodo = response.body();
                        System.out.println(updatedTodo);
                        System.out.println("Todo has been updated successfully");
                    }else{
                        System.out.println("Request failed: " + response.code());
                    }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Todo you are attempting to delete is not found");
        }
    }

    public void deleteTodoAsync(int todoId) {
        Todo t = getTodo(todoId);
        if (t != null) {
            Call<Todo> call = api.deleteTodo(todoId);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Todo> call, Response<Todo> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Todo Successfully deleted");
                    }
                }

                @Override
                public void onFailure(Call<Todo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            System.out.println("Todo you are trying to delete does not exist");
        }
    }
}
