package tw.teddysoft.tasks.adapter.controller.web;

import static com.google.common.base.Strings.isNullOrEmpty;
import static tw.teddysoft.ucontract.Contract.reject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.spring.config.UseCaseInjection;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownInput;
import tw.teddysoft.tasks.usecase.port.in.task.set_down.SetDownUseCase;

@RestController
@AutoConfigureAfter({UseCaseInjection.class})

public class SetDoneController {
  private final SetDownUseCase setDoneUseCase;

  @Autowired
  public SetDoneController(SetDownUseCase setDoneUseCase) {
    this.setDoneUseCase = setDoneUseCase;
  }

  @PostMapping(path = "/setdone")
  public ResponseEntity<CqrsOutput> addProject(
      @RequestParam("todolistId") String todolistId,
      @RequestParam("taskId") String taskId,
      @RequestParam("done") Boolean done) {

    if (reject("todolistId is null or empty", () -> isNullOrEmpty(todolistId)))
      return new ResponseEntity<>(CqrsOutput.create().setMessage("Query parameter should contain key: 'todolistId'"), HttpStatus.BAD_REQUEST);

    if (reject("taskId is null or empty", () -> isNullOrEmpty(taskId)))
      return new ResponseEntity<>(CqrsOutput.create().setMessage("Query parameter should contain key: 'taskId'"), HttpStatus.BAD_REQUEST);

    if (reject("done is null", () -> null == done))
      return new ResponseEntity<>(CqrsOutput.create().setMessage("Query parameter should contain key: 'done'"), HttpStatus.BAD_REQUEST);

    try {
      SetDownInput input = new SetDownInput();
      input.todoListId = todolistId;
      input.taskId = taskId;
      input.isDone = done;
      var output = setDoneUseCase.execute(input);
      if (output.getExitCode() == ExitCode.SUCCESS)
        return new ResponseEntity<>(output, HttpStatus.OK);
      return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      var output = CqrsOutput.create()
          .setId(taskId)
          .setExitCode(ExitCode.FAILURE)
          .setMessage("Set a task failed caused by " + e.getMessage());
      return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

