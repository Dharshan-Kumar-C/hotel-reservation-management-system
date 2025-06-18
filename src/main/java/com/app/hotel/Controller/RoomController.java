package com.app.hotel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.hotel.Entity.Room;
import com.app.hotel.Service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room){
        return new ResponseEntity<>(roomService.createRoom(room), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> get(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @GetMapping
    public List<Room> getAll(){
        return roomService.getAllRooms();
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room room){
        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public Page<Room> getPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "amount") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir){
            return roomService.getRoomsWithPagination(page, size, sortBy, sortDir);
    }

}
