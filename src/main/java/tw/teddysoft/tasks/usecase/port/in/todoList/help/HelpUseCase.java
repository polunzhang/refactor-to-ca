package tw.teddysoft.tasks.usecase.port.in.todoList.help;

import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.ezddd.cqrs.usecase.query.Query;

public interface HelpUseCase extends Query<Input.NullInput,HelpOutput> {

}
