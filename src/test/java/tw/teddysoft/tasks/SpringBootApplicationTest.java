package tw.teddysoft.tasks;

import static java.lang.System.lineSeparator;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.config.SpringBootTestContextProvider;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.io.standard.TodoListApp;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todoList.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.service.AddTaskService;
import tw.teddysoft.tasks.usecase.service.ErrorService;
import tw.teddysoft.tasks.usecase.service.HelpService;
import tw.teddysoft.tasks.usecase.service.SetDoneService;
import tw.teddysoft.tasks.usecase.service.ShowService;

public final class SpringBootApplicationTest extends SpringBootTestContextProvider {
  public static final String PROMPT = "> ";
  private Thread applicationThread;

  private final PipedOutputStream inStream = new PipedOutputStream();
  private final PrintWriter inWriter = new PrintWriter(inStream, true);
  private final PipedInputStream outStream = new PipedInputStream();
  private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

  private final ShowUseCase showUseCase;
  private final AddProjectUseCase addProjectUseCase;
  private final AddTaskUseCase addTaskUseCase;
  private final SetDownUseCase setDoneUseCase;
  private final ErrorUseCase errorUseCase;

  @Autowired
  public SpringBootApplicationTest(
      ToDoListRepository repository,
      ShowUseCase showUseCase,
      AddProjectUseCase addProjectUseCase,
      AddTaskUseCase addTaskUseCase,
      SetDownUseCase setDoneUseCase,
      HelpUseCase helpUseCase,
      ErrorUseCase errorUseCase) {

    this.showUseCase = showUseCase;
    this.addProjectUseCase = addProjectUseCase;
    this.addTaskUseCase = addTaskUseCase;
    this.setDoneUseCase = setDoneUseCase;
    this.errorUseCase = errorUseCase;
    repository.save(new TodoList(TodoListId.of(TodoListApp.DEFAULT_TO_DO_LIST_ID)));
  }

  @BeforeEach
  public void start_the_application() throws IOException {

    BufferedReader in = new BufferedReader(new InputStreamReader(new PipedInputStream(inStream)));
    PrintWriter out = new PrintWriter(new PipedOutputStream(outStream), true);
    var helpUseCase = new HelpService(new HelpConsolePresenter(out));
    var showPresenter = new ShowConsolePresenter(out);

    TodoListApp todoListApp =
        new TodoListApp(
            in,
            out,
            showUseCase,
            showPresenter,
            addProjectUseCase,
            addTaskUseCase,
            setDoneUseCase,
            helpUseCase,
            errorUseCase);
    applicationThread = new Thread(todoListApp);
    applicationThread.start();
  }

  @AfterEach
  public void kill_the_application() throws IOException, InterruptedException {
    if (!stillRunning()) {
      return;
    }

    Thread.sleep(1000);
    if (!stillRunning()) {
      return;
    }

    applicationThread.interrupt();
    throw new IllegalStateException("The application is still running.");
  }

  @Test
  @Timeout(10000)
  public void it_works() throws IOException {
    execute("show");

    execute("add project secrets");
    execute("add task secrets Eat more donuts.");
    execute("add task secrets Destroy all humans.");

    execute("show");
    readLines("secrets", "    [ ] 1: Eat more donuts.", "    [ ] 2: Destroy all humans.", "");

    execute("add project training");
    execute("add task training Four Elements of Simple Design");
    execute("add task training SOLID");
    execute("add task training Coupling and Cohesion");
    execute("add task training Primitive Obsession");
    execute("add task training Outside-In TDD");
    execute("add task training Interaction-Driven Design");

    execute("check 1");
    execute("check 3");
    execute("check 5");
    execute("check 6");

    execute("show");
    readLines(
        "secrets",
        "    [x] 1: Eat more donuts.",
        "    [ ] 2: Destroy all humans.",
        "",
        "training",
        "    [x] 3: Four Elements of Simple Design",
        "    [ ] 4: SOLID",
        "    [x] 5: Coupling and Cohesion",
        "    [x] 6: Primitive Obsession",
        "    [ ] 7: Outside-In TDD",
        "    [ ] 8: Interaction-Driven Design",
        "");

    // Added by Teddy to verify error handling messages
    execute("add task DDD learn CA.");
    readLines("Could not find a project with the name \"DDD\".");

    execute("check 99");
    readLines("Could not find a task with an ID of 99.");

    execute("help");
    readLines(
        "Commands:",
        "  show",
        "  add project <project name>",
        "  add task <project name> <task description>",
        "  check <task ID>",
        "  uncheck <task ID>",
        "");

    execute("not-a-command");
    readLines("I don't know what the command \"not-a-command\" is.");

    execute("quit");
  }

  private void execute(String command) throws IOException {
    read(PROMPT);
    write(command);
  }

  private void read(String expectedOutput) throws IOException {
    int length = expectedOutput.length();
    char[] buffer = new char[length];
    outReader.read(buffer, 0, length);
    assertEquals(expectedOutput, String.valueOf(buffer));
  }

  private void readLines(String... expectedOutput) throws IOException {
    for (String line : expectedOutput) {
      read(line + lineSeparator());
    }
  }

  private void write(String input) {
    inWriter.println(input);
  }

  private boolean stillRunning() {
    return applicationThread != null && applicationThread.isAlive();
  }
}
