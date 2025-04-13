package com.programmerpagi.toko_online.config;

import com.programmerpagi.toko_online.model.User;
import com.programmerpagi.toko_online.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findUser = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        List<GrantedAuthority> authorities = List.of(
             new SimpleGrantedAuthority(findUser.getRole())
        );

        return new org.springframework.security.core.userdetails.User(
                findUser.getEmail(),
                findUser.getPassword(),
                authorities);
    }
}
