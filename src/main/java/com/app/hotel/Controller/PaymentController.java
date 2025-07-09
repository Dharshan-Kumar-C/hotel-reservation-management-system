package com.app.hotel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hotel.Entity.Payment;
import com.app.hotel.Service.PaymentService;
import com.app.hotel.Repository.PaymentRepository;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment){
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @GetMapping
    public List<Payment> getAll(){
        return paymentService.getAllPayments();
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Payment> update(@PathVariable Long id, @RequestBody Payment payment){
        return ResponseEntity.ok(paymentService.updatePayment(id, payment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public Page<Payment> getPaymentsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDir,
            @RequestParam(required = false) String reservationId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        if (reservationId != null && !reservationId.isEmpty()) {
            return paymentRepository.findByReservationIdLike(reservationId, pageable);
        } else {
            return paymentRepository.findAll(pageable);
        }
    }

    @GetMapping("/guest/{guestId}")
    public List<Payment> getByGuest(@PathVariable Long guestId) {
        return paymentService.getPaymentsByGuestId(guestId);
    }

}
