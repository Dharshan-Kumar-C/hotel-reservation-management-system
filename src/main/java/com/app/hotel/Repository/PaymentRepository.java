package com.app.hotel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.hotel.Entity.Payment;
import com.app.hotel.Entity.Reservation;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    @Query("SELECT p FROM Payment p WHERE LOWER(p.paymentMethod) LIKE LOWER(CONCAT('%', :method, '%'))")
    List<Payment> searchByPaymentMethod(@Param("method") String method);

    @Query("SELECT p FROM Payment p ORDER BY p.amountPaid DESC")
    List<Payment> sortByAmountPaidDesc();
    
    void deleteByReservation(Reservation reservation);

}
