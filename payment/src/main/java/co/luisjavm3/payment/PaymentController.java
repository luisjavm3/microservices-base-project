package co.luisjavm3.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService service;

	@PostMapping
	public ResponseEntity<?> addPayment(@RequestBody Payment paymentCreate) {
		try {
			Payment payment = service.addPayment(paymentCreate);

			if (payment == null)
				return ResponseEntity.badRequest().build();

			return ResponseEntity.ok(payment);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("credit/{creditId}")
	public ResponseEntity<?> getPaymentsByCreditId(@PathVariable("creditId") Long creditId) {
		try {
			var result = service.getPaymentsByCreditId(creditId);

			if (result.size() == 0)
				return ResponseEntity.noContent().build();

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@DeleteMapping("credit/{creditId}")
	public ResponseEntity<?> deletePaymentsByCreditId(@PathVariable("creditId") Long creditId) {
		try {
			service.deletePaymentsByCreditId(creditId);

			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
