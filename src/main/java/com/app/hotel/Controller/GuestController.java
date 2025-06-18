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

import com.app.hotel.Entity.Guest;
import com.app.hotel.Service.GuestService;

@RestController
@RequestMapping("/api/guests")
public class GuestController {
    
    @Autowired
    private GuestService guestService;

    @PostMapping
    public ResponseEntity<Guest> create(@RequestBody Guest guest){
        return new ResponseEntity<>(guestService.createGuest(guest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> get(@PathVariable Long id){
        return ResponseEntity.ok(guestService.getGuest(id));
    }

    @GetMapping
    public List<Guest> getAll(){
        return guestService.getAllGuests();
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Guest> update(@PathVariable Long id, @RequestBody Guest guest){
        return ResponseEntity.ok(guestService.updateGuest(id, guest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public Page<Guest> getPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir){
            return guestService.getGuestsWithPagination(page, size, sortBy, sortDir);
    }

}
