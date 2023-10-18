package co.luisjavm3.credit;

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
public class CreditController {
	private final CreditService service;

	@GetMapping
	public ResponseEntity<?> getAll() {
		var result = service.getAllCredits();

		if (result.size() == 0)
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(result);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getCredit(@PathVariable("id") Long id) {
		try {
			var result = service.getCreditById(id);

			if (result == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> addCredit(@RequestBody Credit credit) {
		try {
			Credit c = service.addCredit(credit);

			if (c == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.ok(c);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@DeleteMapping("delete-credits/{userId}")
	public ResponseEntity<?> deleteCreditsByUserId(@PathVariable("userId") Long userId) {
		try {
			service.removeCreditsByUserId(userId);

			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCreditById(@PathVariable("id") Long id) {
		try {
			service.removeCredit(id);

			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PutMapping("soft-delete/{id}")
	public ResponseEntity<?> softDeleteCredit(@PathVariable("id") Long id) {
		try {
			Credit credit = service.softDeleteCreditById(id);

			if (credit == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
