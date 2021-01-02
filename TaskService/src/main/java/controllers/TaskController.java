package controllers;

import domain.ITaskService;
import domain.ProductivityTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/tasks")
@RestController
public class TaskController {

	private final ITaskService service;

	@Autowired
	TaskController(ITaskService service) {
		this.service = service;
	}

	@GetMapping
	List<ProductivityTaskDto> getAllTasks() {
		return service.getAllTasks();
	}

	@PostMapping
	ResponseEntity createNewTask(@RequestBody ProductivityTaskDto newTask) {
		try {
			ProductivityTaskDto dto = service.createTask(newTask);
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(dto.getId())
					.toUri();
			return ResponseEntity.created(location).body(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/{id}")
	ProductivityTaskDto getTask(@PathVariable String id) {
		return service.getTask(id);
	}

	@PutMapping("/{id}")
	ResponseEntity updateTask(@RequestBody ProductivityTaskDto updatedTask) {
		try {
			service.updateTask(updatedTask);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	void deleteTask(@PathVariable String id) {
		boolean result = service.deleteTask(id);
	}
}
