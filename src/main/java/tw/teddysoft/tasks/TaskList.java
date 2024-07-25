package tw.teddysoft.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.Add;
import tw.teddysoft.tasks.usecase.Error;
import tw.teddysoft.tasks.usecase.Execute;
import tw.teddysoft.tasks.usecase.Help;
import tw.teddysoft.tasks.usecase.SetDone;
import tw.teddysoft.tasks.usecase.Show;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final BufferedReader in;
    private final PrintWriter out;
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
    private final TodoList todoList = new TodoList(new TodoListId(DEFAULT_TO_DO_LIST_ID));

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
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
          new Execute(todoList, out).execute(command);
        }
    }


}
