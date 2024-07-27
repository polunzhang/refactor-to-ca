package tw.teddysoft.tasks.adapter.controller.web;

import static com.google.common.base.Strings.isNullOrEmpty;
import static tw.teddysoft.ucontract.Contract.reject;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.spring.config.UseCaseInjection;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;

@RestController
@AutoConfigureAfter({UseCaseInjection.class})
public class AddProjectController {
  private final AddProjectUseCase addProjectUseCase;

  public AddProjectController(AddProjectUseCase addProjectUseCase) {
    this.addProjectUseCase = addProjectUseCase;
  }

  @PostMapping(path = "/projects")
  public ResponseEntity<CqrsOutput> addProject(
      @RequestParam("todolistId") String todolistId,
      @RequestParam("projectName") String projectName) {

    if (reject("todolistId is null or empty", () -> isNullOrEmpty(todolistId)))
      return new ResponseEntity<>(
          CqrsOutput.create().setMessage("Query parameter should contain key: 'todolistId'"),
          HttpStatus.BAD_REQUEST);

    if (reject("projectName is null or empty", () -> isNullOrEmpty(projectName)))
      return new ResponseEntity<>(
          CqrsOutput.create().setMessage("Query parameter should contain key: 'projectName'"),
          HttpStatus.BAD_REQUEST);

    try {
      AddProjectInput input = new AddProjectInput();
      input.toDoListId = todolistId;
      input.projectName = projectName;
      var output = addProjectUseCase.execute(input);
      if (output.getExitCode() == ExitCode.SUCCESS)
        return new ResponseEntity<>(output, HttpStatus.OK);
      return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      var output =
          CqrsOutput.create()
              .setId(projectName)
              .setExitCode(ExitCode.FAILURE)
              .setMessage("Add a project a to to do list failed caused by " + e.getMessage());
      return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
