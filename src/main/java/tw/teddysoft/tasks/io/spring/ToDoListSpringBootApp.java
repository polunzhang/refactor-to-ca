package tw.teddysoft.tasks.io.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import tw.teddysoft.tasks.adapter.controller.console.ToDoListConsoleController;
import tw.teddysoft.tasks.io.spring.config.UseCaseInjection;
import tw.teddysoft.tasks.io.standard.TodoListApp;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ShowPresenter;

@SpringBootApplication
@ComponentScan(basePackages = {"tw.teddysoft.tasks"})
@EntityScan(basePackages = {"tw.teddysoft.tasks"})
@AutoConfigureAfter({UseCaseInjection.class})
public class ToDoListSpringBootApp extends SpringBootServletInitializer
    implements CommandLineRunner {

  private static final String QUIT = "quit";

  public static final String TO_DO_LIST_ID = "001";

  private final BufferedReader in;
  private final PrintWriter out;
  private final ShowUseCase showUseCase;
  private final ShowPresenter showPresenter;
  private final AddProjectUseCase addProjectUseCase;
  private final AddTaskUseCase addTaskUseCase;
  private final SetDownUseCase setDoneUseCase;
  private final ErrorUseCase errorUseCase;
  private final HelpUseCase helpUseCase;

  @Autowired
  public ToDoListSpringBootApp(
      BufferedReader in,
      PrintWriter out,
      ShowUseCase showUseCase,
      ShowPresenter showPresenter,
      AddProjectUseCase addProjectUseCase,
      AddTaskUseCase addTaskUseCase,
      SetDownUseCase setDoneUseCase,
      ErrorUseCase errorUseCase,
      @Qualifier("consoleHelp") HelpUseCase helpUseCase) {
    this.in = in;
    this.out = out;
    this.showUseCase = showUseCase;
    this.showPresenter = showPresenter;
    this.addProjectUseCase = addProjectUseCase;
    this.addTaskUseCase = addTaskUseCase;
    this.setDoneUseCase = setDoneUseCase;
    this.errorUseCase = errorUseCase;
    this.helpUseCase = helpUseCase;
  }

  public static void main(String[] args) {
    SpringApplication.run(TodoListApp.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ToDoListSpringBootApp.class);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Running Spring Boot Application");
    while (true) {
      out.print("> ");
      out.flush();
      String command;
      try {
        command = in.readLine();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      if (command.equals(QUIT)) {
        System.exit(0);
      }
      new ToDoListConsoleController(
          out,
          showUseCase,
          showPresenter,
          addProjectUseCase,
          addTaskUseCase,
          setDoneUseCase,
          helpUseCase,
          errorUseCase).execute(command);
    }
  }
}
