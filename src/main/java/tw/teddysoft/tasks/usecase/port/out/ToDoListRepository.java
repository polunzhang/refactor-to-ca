package tw.teddysoft.tasks.usecase.port.out;

import tw.teddysoft.ezddd.core.usecase.Repository;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;

public interface ToDoListRepository extends Repository<TodoList, TodoListId> {
}
