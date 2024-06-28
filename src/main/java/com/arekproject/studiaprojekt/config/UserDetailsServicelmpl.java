package com.arekproject.studiaprojekt.config;

import com.arekproject.studiaprojekt.entity.UserDtls;
import com.arekproject.studiaprojekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicelmpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDtls user = userRepo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not exist");
        }else{
            CustomUserDtls customUserDtls = new CustomUserDtls(user);
            return customUserDtls;
        }

    }
}
