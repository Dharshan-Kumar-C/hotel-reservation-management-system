package com.app.hotel.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.hotel.Entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    
    @Query("SELECT g FROM Guest g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Guest> searchByName(@Param("name") String name);

    @Query("SELECT g FROM Guest g ORDER BY g.name ASC")
    List<Guest> sortByNameAsc();

    Optional<Guest> findByEmailAndPassword(String email, String password);

    Optional<Guest> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
