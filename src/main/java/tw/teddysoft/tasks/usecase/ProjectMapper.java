package tw.teddysoft.tasks.usecase;

import java.util.List;
import tw.teddysoft.tasks.entity.Project;

public class ProjectMapper {

  public static ProjectDto toDto(Project project) {
    ProjectDto projectDto = new ProjectDto();
    projectDto.name = project.getName().string();
    projectDto.taskDtos = TaskMapper.toDto(project.getTasks());
    return projectDto;
  }

  public static List<ProjectDto> toDto(List<Project> projects) {
    return projects.stream().map(ProjectMapper::toDto).toList();
  }
}
