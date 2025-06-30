package com.app.hotel.Service;

import com.app.hotel.Entity.Guest;
import com.app.hotel.Repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestDetailsService implements UserDetailsService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Guest guest = guestRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Guest not found"));

        return new org.springframework.security.core.userdetails.User(
            guest.getEmail(),
            guest.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_GUEST"))
        );
    }
}
