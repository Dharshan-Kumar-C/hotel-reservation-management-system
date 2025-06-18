package com.app.hotel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.hotel.Entity.Guest;
import com.app.hotel.Entity.Reservation;
import com.app.hotel.Entity.Room;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    @Query("SELECT r FROM Reservation r JOIN r.guest g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Reservation> searchByGuestName(@Param("name") String name);

    @Query("SELECT r FROM Reservation r ORDER BY r.checkInDate ASC")
    List<Reservation> sortByCheckInDateAsc();
    
    void deleteByGuest(Guest guest);

    void deleteByRoom(Room room);
    
    List<Reservation> findByGuest(Guest guest);

    List<Reservation> findByRoom(Room room);
    
}
