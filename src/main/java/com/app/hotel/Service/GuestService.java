package com.app.hotel.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.hotel.Entity.Guest;
import com.app.hotel.Entity.Reservation;
import com.app.hotel.Exception.ResourceNotFoundException;
import com.app.hotel.Repository.GuestRepository;
import com.app.hotel.Repository.PaymentRepository;
import com.app.hotel.Repository.ReservationRepository;

@Service
public class GuestService {
    
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Guest createGuest(Guest guest){
        return guestRepository.save(guest);
    }

    public Guest getGuest(Long id){
        return guestRepository.findById(id).orElseThrow(() -> new RuntimeException("Guest not found"));
    }

    public List<Guest> getAllGuests(){
        return guestRepository.findAll();
    }

    public Guest updateGuest(Long id, Guest updatedGuest){
        Guest existing = getGuest(id);
        existing.setName(updatedGuest.getName());
        existing.setEmail(updatedGuest.getEmail());
        existing.setPhone(updatedGuest.getPhone());
        existing.setAddress(updatedGuest.getAddress());
        return guestRepository.save(existing);
    }

    @Transactional
    public void deleteGuest(Long id){
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: "+id));
        List<Reservation> reservations = reservationRepository.findByGuest(guest);
        for(Reservation reservation : reservations){
            paymentRepository.deleteByReservation(reservation);
            reservationRepository.delete(reservation);
        }
        guestRepository.delete(guest);
    }

    public Page<Guest> getGuestsWithPagination(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return guestRepository.findAll(pageable);
    }
    
}
