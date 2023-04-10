package inu.appcenter.study4.model.todo;

import inu.appcenter.study4.domain.Todo;
import inu.appcenter.study4.domain.TodoStatus;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TodoResponse {

    private Long id;

    private String content;

    private TodoStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static TodoResponse from(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        todoResponse.id = todo.getId();
        todoResponse.content = todo.getContent();
        todoResponse.status = todo.getStatus();
        todoResponse.createdAt = todo.getCreatedAt();
        todoResponse.updatedAt = todo.getUpdatedAt();
        return todoResponse;
    }
}
