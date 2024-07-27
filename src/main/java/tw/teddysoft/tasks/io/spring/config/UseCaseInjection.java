package tw.teddysoft.tasks.io.spring.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tw.teddysoft.tasks.adapter.controller.web.HelpWebPresenter;
import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ShowPresenter;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.service.AddTaskService;
import tw.teddysoft.tasks.usecase.service.ErrorService;
import tw.teddysoft.tasks.usecase.service.HelpService;
import tw.teddysoft.tasks.usecase.service.SetDoneService;
import tw.teddysoft.tasks.usecase.service.ShowService;

@Configuration("UseCaseInjection")
@AutoConfigureAfter({RepositoryInjection.class})
public class UseCaseInjection {

  private final ToDoListRepository toDoListRepository;

  @Autowired
  public UseCaseInjection(ToDoListRepository toDoListRepository) {
    this.toDoListRepository = toDoListRepository;
  }

  @Bean
  public BufferedReader getIn() {
    return new BufferedReader(new InputStreamReader(System.in));
  }

  @Bean
  public PrintWriter getOut() {
    return new PrintWriter(System.out);
  }

  @Bean
  public AddProjectUseCase addProjectUseCase() {
    return new AddProjectService(toDoListRepository);
  }

  @Bean
  public AddTaskUseCase addTaskUseCase() {
    return new AddTaskService(toDoListRepository);
  }

  @Bean
  public SetDownUseCase setDoneUseCase() {
    return new SetDoneService(toDoListRepository);
  }

  @Bean
  public ErrorUseCase errorUseCase() {
    return new ErrorService();
  }

  @Bean
  public ShowUseCase showUseCase() {
    return new ShowService(toDoListRepository);
  }

  @Bean(name = "consoleHelp")
  public HelpUseCase consoleHelpUseCase() { return new HelpService(new HelpConsolePresenter(getOut())); }

  @Bean(name = "webHelp")
  public HelpUseCase webHelpUseCase() { return new HelpService(new HelpWebPresenter()); }

  @Bean
  public ShowPresenter showPresenter() {
    return new ShowConsolePresenter(getOut());
  }
}
