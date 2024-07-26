package tw.teddysoft.tasks.usecase.port.in.todoList.error;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.ezddd.cqrs.usecase.query.Query;

public interface ErrorUseCase extends Query<ErrorInput, CqrsOutput> {

}
