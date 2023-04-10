package inu.appcenter.study4.controller;

import inu.appcenter.study4.config.security.LoginMember;
import inu.appcenter.study4.model.todo.TodoSaveRequest;
import inu.appcenter.study4.model.todo.TodoUpdateRequest;
import inu.appcenter.study4.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity saveTodos(@LoginMember User user, @RequestBody TodoSaveRequest request) {
        // @AuthenticationPrincipal도 가능
        String email = user.getUsername();

        todoService.saveTodo(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PatchMapping("/todos")
//    public ResponseEntity updateTodos(@RequestBody TodoUpdateRequest request) {
//        Authentication authenication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authenication.getPrincipal();
//        String email = user.getUsername();
//
//        return ResponseEntity.status(HttpStatus.o).build();
//    }
}
