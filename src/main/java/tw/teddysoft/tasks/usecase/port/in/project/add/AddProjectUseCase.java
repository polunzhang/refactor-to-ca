package tw.teddysoft.tasks.usecase.port.in.project.add;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.ezddd.cqrs.usecase.command.Command;


public interface AddProjectUseCase extends Command<AddProjectInput, CqrsOutput> {

}

