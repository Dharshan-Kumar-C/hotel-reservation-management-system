package com.app.hotel.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hotel.Entity.Reservation;
import com.app.hotel.Exception.ResourceNotFoundException;
import com.app.hotel.Repository.PaymentRepository;
import com.app.hotel.Repository.ReservationRepository;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Reservation createReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Reservation getReservation(Long id){
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation updateReservation(Long id, Reservation upadtedReservation){
        Reservation existing = getReservation(id);
        existing.setCheckInDate(upadtedReservation.getCheckInDate());
        existing.setCheckOutDate(upadtedReservation.getCheckOutDate());
        existing.setPrice(upadtedReservation.getPrice());
        existing.setStatus(upadtedReservation.getStatus());
        existing.setGuest(upadtedReservation.getGuest());
        existing.setRoom(upadtedReservation.getRoom());
        return reservationRepository.save(existing);
    }

    @Transactional
    public void deleteReservation(Long id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: "+id));
        paymentRepository.deleteByReservation(reservation);
        reservationRepository.delete(reservation);
    }

    public Page<Reservation> getReservationsWithPagination(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return reservationRepository.findAll(pageable);
    }
    
}
