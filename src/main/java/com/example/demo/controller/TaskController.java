package com.example.demo.controller;

import com.example.demo.dto.TaskRequestDto;
import com.example.demo.entity.ConstructionTask;
import com.example.demo.entity.enums.TaskStatus;
import com.example.demo.service.TaskWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskWorkflowService taskService;

    public TaskController(TaskWorkflowService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SUPERVISOR')")
    public ResponseEntity<ConstructionTask> createTask(@RequestBody TaskRequestDto dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<List<ConstructionTask>> getTasks(@RequestParam(required = false) Long projectId) {
        if (projectId != null) {
            return ResponseEntity.ok(taskService.getTasksByProject(projectId));
        }
        return ResponseEntity.ok(List.of());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPERVISOR', 'ROLE_CONTRACTOR')")
    public ResponseEntity<ConstructionTask> updateTaskStatus(@PathVariable Long id,
                                                              @RequestBody Map<String, String> body) {
        TaskStatus status = TaskStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status));
    }
}
