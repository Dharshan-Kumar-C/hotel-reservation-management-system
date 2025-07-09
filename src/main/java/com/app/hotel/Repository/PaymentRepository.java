package com.app.hotel.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT p FROM Payment p WHERE p.reservation.guest.guestId = :guestId")
    List<Payment> findByGuestId(@Param("guestId") Long guestId);

    @Query("SELECT p FROM Payment p WHERE (:reservationId IS NULL OR str(p.reservation.reservationId) LIKE CONCAT(:reservationId, '%'))")
    Page<Payment> findByReservationIdLike(@Param("reservationId") String reservationId, Pageable pageable);

}
