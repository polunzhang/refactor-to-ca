package tw.teddysoft.tasks.usecase.port.in.task.add;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.ezddd.cqrs.usecase.command.Command;

public interface AddTaskUseCase extends Command<AddTaskInput, CqrsOutput> {

}
