package com.app.hotel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.hotel.Entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    @Query("SELECT r FROM Room r WHERE LOWER(r.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    List<Room> searchByType(@Param("type") String type);

    @Query("SELECT r FROM Room r ORDER BY r.amount ASC")
    List<Room> sortByAmountAsc();
    
}
