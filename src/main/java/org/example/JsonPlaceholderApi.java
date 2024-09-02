package org.example;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface JsonPlaceholderApi {
    @GET("/todos")
    Call<List<Todo>> getAllTodos();

    @GET("/todos/{id}")
    Call<Todo> getTodo(@Path("id") int todoId);

    @POST("/todos")
    Call<Todo> createTodo(@Body Todo todo);

    @PUT("/todos/{id}")
    Call<Todo> updateTodo(@Path("id") int todoId, @Body Todo todo);

    @DELETE("/todos/{id}")
    Call<Todo> deleteTodo(@Path("id") int todoId);

}
