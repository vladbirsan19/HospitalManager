package com.siit.hospital_manager.config;


import com.siit.hospital_manager.model.User;

import com.siit.hospital_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user.map(MyUserDetails::new).get();
    }
}
