package com.example.demo.service;

import com.example.demo.entity.ContractorAssignment;
import com.example.demo.entity.ConstructionTask;
import com.example.demo.entity.SystemUser;
import com.example.demo.entity.enums.AssignmentStatus;
import com.example.demo.repository.ContractorAssignmentRepository;
import com.example.demo.repository.ConstructionTaskRepository;
import com.example.demo.repository.SystemUserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentService {

    private final ContractorAssignmentRepository assignmentRepository;
    private final ConstructionTaskRepository taskRepository;
    private final SystemUserRepository userRepository;

    public AssignmentService(ContractorAssignmentRepository assignmentRepository,
                             ConstructionTaskRepository taskRepository,
                             SystemUserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ContractorAssignment createAssignment(Map<String, Object> body) {
        Long taskId = Long.valueOf(body.get("taskId").toString());
        Long contractorId = Long.valueOf(body.get("contractorId").toString());
        ConstructionTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        SystemUser contractor = userRepository.findById(contractorId)
                .orElseThrow(() -> new RuntimeException("Contractor not found"));
        ContractorAssignment assignment = ContractorAssignment.builder()
                .task(task)
                .contractor(contractor)
                .assignedDate(LocalDate.now())
                .hourlyRate(new BigDecimal(body.getOrDefault("hourlyRate", "0").toString()))
                .hoursAllocated(Integer.valueOf(body.getOrDefault("hoursAllocated", "0").toString()))
                .status(AssignmentStatus.ACTIVE)
                .build();
        return assignmentRepository.save(assignment);
    }

    public List<ContractorAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public ContractorAssignment updateAssignmentStatus(Long id, AssignmentStatus status) {
        ContractorAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignment.setStatus(status);
        return assignmentRepository.save(assignment);
    }
}
