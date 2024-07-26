package tw.teddysoft.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.adapter.controller.ToDoListConsoleController;
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

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final BufferedReader in;
    private final PrintWriter out;
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
    private final TodoList todoList = new TodoList(new TodoListId(DEFAULT_TO_DO_LIST_ID));
    private final ToDoListRepository toDoListRepository;

    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDownUseCase setDoneUseCase;
    private final HelpUseCase helpUseCase;
    private final ErrorUseCase errorUseCase;
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        toDoListRepository = new ToDoListInMemoryRepository();
        if (toDoListRepository.findById(TodoListId.of(DEFAULT_TO_DO_LIST_ID)).isEmpty()) {
            toDoListRepository.save(todoList);
        }
        this.showUseCase = new ShowService(toDoListRepository);
        this.showPresenter = new ShowConsolePresenter(out);
        this.addProjectUseCase = new AddProjectService(toDoListRepository);
        this.addTaskUseCase = new AddTaskService(todoList, out, toDoListRepository);
        this.setDoneUseCase = new SetDoneService(todoList, out, toDoListRepository);
        this.helpUseCase = new HelpService(new HelpConsolePresenter(out));
        this.errorUseCase = new ErrorService();
    }

    public void run() {
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
                break;
            }
            new ToDoListConsoleController( out, showUseCase,
                showPresenter, addProjectUseCase, addTaskUseCase, setDoneUseCase, helpUseCase,
                errorUseCase).execute(command);
        }
    }


}
