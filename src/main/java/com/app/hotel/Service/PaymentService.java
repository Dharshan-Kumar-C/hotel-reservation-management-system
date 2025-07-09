package com.app.hotel.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hotel.Entity.Payment;
import com.app.hotel.Exception.ResourceNotFoundException;
import com.app.hotel.Repository.PaymentRepository;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public Payment getPayment(Long id){
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    public Payment updatePayment(Long id, Payment updatedPayment){
        Payment existing = getPayment(id);
        existing.setReservation(updatedPayment.getReservation());
        existing.setPaymentDate(updatedPayment.getPaymentDate());
        existing.setAmountPaid(updatedPayment.getAmountPaid());
        existing.setPaymentMethod(updatedPayment.getPaymentMethod());
        existing.setPaymentStatus(updatedPayment.getPaymentStatus());
        return paymentRepository.save(existing);
    }

    @Transactional
    public void deletePayment(Long id){
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: "+id));
        paymentRepository.delete(payment);
    }

    public Page<Payment> getPaymentsWithPagination(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return paymentRepository.findAll(pageable);
    }

    public List<Payment> getPaymentsByGuestId(Long guestId) {
        return paymentRepository.findByGuestId(guestId);
    }
    
}
