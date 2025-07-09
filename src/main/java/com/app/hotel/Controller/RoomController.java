package com.app.hotel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.app.hotel.Repository.RoomRepository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

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
    public Page<Room> getRoomsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDir,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        if (type != null && !type.isEmpty() && status != null && !status.isEmpty()) {
            return roomRepository.findByTypeAndStatus(type, status, pageable);
        } else if (type != null && !type.isEmpty()) {
            return roomRepository.findByType(type, pageable);
        } else if (status != null && !status.isEmpty()) {
            return roomRepository.findByStatus(status, pageable);
        } else {
            return roomRepository.findAll(pageable);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable String type){
        return ResponseEntity.ok(roomService.getRoomsByType(type));
    }

}
