package tw.teddysoft.tasks.io;

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
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.service.AddTaskService;
import tw.teddysoft.tasks.usecase.service.ErrorService;
import tw.teddysoft.tasks.usecase.service.HelpService;
import tw.teddysoft.tasks.usecase.service.SetDoneService;
import tw.teddysoft.tasks.usecase.service.ShowService;

public final class TodoListApp implements Runnable {
    private static final String QUIT = "quit";

    private final BufferedReader in;
    private final PrintWriter out;
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
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
        var toDoListRepository = new ToDoListInMemoryRepository();
        if (toDoListRepository.findById(TodoListId.of(DEFAULT_TO_DO_LIST_ID)).isEmpty()) {
            toDoListRepository.save(new TodoList(new TodoListId(DEFAULT_TO_DO_LIST_ID)));
        }

        var showUseCase = new ShowService(toDoListRepository);
        var showPresenter = new ShowConsolePresenter(out);
        var addProjectUseCase = new AddProjectService(toDoListRepository);
        var addTaskUseCase = new AddTaskService(toDoListRepository);
        var setDoneUseCase = new SetDoneService(toDoListRepository);
        var helpUseCase = new HelpService(new HelpConsolePresenter(out));
        var errorUseCase = new ErrorService();

        new TodoListApp(in, out, showUseCase, showPresenter, addProjectUseCase, addTaskUseCase,
            setDoneUseCase, helpUseCase, errorUseCase).run();

    }

    public TodoListApp(BufferedReader reader, PrintWriter writer, ShowUseCase showUseCase,
        ShowPresenter showPresenter, AddProjectUseCase addProjectUseCase,
        AddTaskUseCase addTaskUseCase, SetDownUseCase setDoneUseCase, HelpUseCase helpUseCase,
        ErrorUseCase errorUseCase) {
        this.in = reader;
        this.out = writer;
        this.showUseCase = showUseCase;
        this.showPresenter = showPresenter;
        this.addProjectUseCase = addProjectUseCase;
        this.addTaskUseCase = addTaskUseCase;
        this.setDoneUseCase = setDoneUseCase;
        this.helpUseCase = helpUseCase;
        this.errorUseCase = errorUseCase;
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
