package com.app.hotel.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    @Query("SELECT r FROM Reservation r " +
           "WHERE (:guestName IS NULL OR LOWER(r.guest.name) LIKE LOWER(CONCAT('%', :guestName, '%'))) " +
           "AND (:roomType IS NULL OR LOWER(r.room.type) LIKE LOWER(CONCAT('%', :roomType, '%'))) " +
           "AND (:status IS NULL OR r.status = :status)")
    Page<Reservation> findWithFilters(
        @Param("guestName") String guestName,
        @Param("roomType") String roomType,
        @Param("status") String status,
        Pageable pageable
    );
}
