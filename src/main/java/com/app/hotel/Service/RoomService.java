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
import com.app.hotel.Entity.Room;
import com.app.hotel.Exception.ResourceNotFoundException;
import com.app.hotel.Repository.PaymentRepository;
import com.app.hotel.Repository.ReservationRepository;
import com.app.hotel.Repository.RoomRepository;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Room createRoom(Room room){
        return roomRepository.save(room);
    }

    public Room getRoom(Long id){
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room updateRoom(Long id, Room updatedRoom){
        Room existing = getRoom(id);
        existing.setRoomNumber(updatedRoom.getRoomNumber());
        existing.setType(updatedRoom.getType());
        existing.setAmount(updatedRoom.getAmount());
        existing.setStatus(updatedRoom.getStatus());
        return roomRepository.save(existing);
    }

    @Transactional
    public void deleteRoom(Long id){
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found with id: "+id));
        List<Reservation> reservations = reservationRepository.findByRoom(room);
        for(Reservation reservation : reservations){
            paymentRepository.deleteByReservation(reservation);
            reservationRepository.delete(reservation);
        }
        roomRepository.delete(room);
    }

    public Page<Room> getRoomsWithPagination(int page, int size, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return roomRepository.findAll(pageable);
    }

    public List<Room> getRoomsByType(String type){
        return roomRepository.findByType(type);
    }
    
}
