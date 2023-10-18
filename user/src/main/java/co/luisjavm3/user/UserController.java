package co.luisjavm3.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService service;

	@GetMapping
	public ResponseEntity<?> getAll() {
		var result = service.getAllUsers();
		return ResponseEntity.ok(result);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		try {
			var result = service.getUserById(id);

			if (result == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody User user) {
		var result = service.addUser(user);

		if (result == null)
			return ResponseEntity.badRequest().build();

		return ResponseEntity.ok(result);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> removeUser(@PathVariable("id") Long id) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		var result = service.updateUser(id, user);

		if (result == null)
			return ResponseEntity.badRequest().build();

		return ResponseEntity.ok(result);
	}
}
