package tw.teddysoft.tasks.usecase.port.in.todoList.show;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.TodoListDto;

public class ShowOutput extends CqrsOutput<ShowOutput> {

  public TodoListDto todoListDto;

}
