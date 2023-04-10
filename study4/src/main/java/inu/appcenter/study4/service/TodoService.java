package inu.appcenter.study4.service;

import inu.appcenter.study4.domain.Member;
import inu.appcenter.study4.domain.Todo;
import inu.appcenter.study4.model.todo.TodoResponse;
import inu.appcenter.study4.model.todo.TodoSaveRequest;
import inu.appcenter.study4.repository.MemberRepository;
import inu.appcenter.study4.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveTodo(String email, TodoSaveRequest request) {

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException());

        Todo todo = Todo.createTodo(request.getContent(), findMember);

        todoRepository.save(todo);

    }
}
