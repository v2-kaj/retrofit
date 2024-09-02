package org.example;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Creating an instance of Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Creating an implementation of the API endpoints defined by the interface
        JsonPlaceholderApi api = retrofit.create(JsonPlaceholderApi.class);

        TodoService todoService = new TodoService(api);
        // Create a Todo
        Scanner input = new Scanner(System.in);
        boolean run = true;
        while(run) {
            System.out.println();
            System.out.println("Menu");
            System.out.println("1: View Todos");
            System.out.println("2: Create a Todo");
            System.out.println("3: Update a Todo");
            System.out.println("4: Delete a Todo");
            System.out.println("5: Quit");
            System.out.println();
            System.out.print("Enter option: ");
            int option = input.nextInt();
            input.nextLine();
            switch (option) {
                case 1:
                    // Print the first n todos
                    System.out.println();
                    System.out.println("VIEW THE FIRST n TODOS");
                    System.out.print("How many todos do you want to see: ");
                    int number = input.nextInt();
                    todoService.printTodos(number);
                    break;
                case 2:
                    System.out.println("CREATE A TODO");
                    System.out.print("Title: ");
                    String title = input.nextLine();
                    Todo todo = new Todo(1, title, false);
                    todoService.createTodo(todo);
                    break;
                case 3:
                    // Update a Todo
                    System.out.println();
                    System.out.println("UPDATE A TODO");
                    System.out.print("Enter the id of the todo you want to update: ");
                    int updateTodoId = input.nextInt();
                    input.nextLine();
                    System.out.print("Edit title or leave blank to maintain the previous title: ");
                    String newTitle = input.nextLine();
                    System.out.print("Is it completed now? Yes or No: ");
                    String answer = input.nextLine();
                    boolean completed = answer.equalsIgnoreCase("yes");
                    Todo updatedTodo = new Todo(1, updateTodoId, newTitle, completed);
                    todoService.updateTodo(updatedTodo);
                    break;
                case 4:
                    // Delete a Todo
                    System.out.println();
                    System.out.println("DELETE A TODO");
                    System.out.print("Enter the id of the todo you want to delete: ");
                    int delTodoId = input.nextInt();
                    todoService.deleteTodoAsync(delTodoId);
                    break;
                case 5:
                    run=false;
                    break;
            }
        }
    }
}
