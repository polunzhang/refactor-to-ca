package tw.teddysoft.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.Execute;
import tw.teddysoft.tasks.usecase.out.ToDoListRepository;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final BufferedReader in;
    private final PrintWriter out;
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
    private final TodoList todoList = new TodoList(new TodoListId(DEFAULT_TO_DO_LIST_ID));
    private final ToDoListRepository toDoListRepository;

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
          new Execute(todoList, out, toDoListRepository).execute(command);
        }
    }


}
