package br.com.udemy.tasks.controller.dto;

import br.com.udemy.tasks.model.TaskState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {

    private String id;
    private String title;
    private String description;
    private int priority;
    private TaskState state;
}
