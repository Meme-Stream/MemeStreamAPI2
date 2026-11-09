package org.memestream.api.chefs;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChefUserDetailsService implements UserDetailsService {
    private final ChefRepository chefRepository;

    public ChefUserDetailsService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var savedChef = chefRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return User.withUsername(savedChef.getUsername())
                .password(savedChef.getPassword())
                .roles(savedChef.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .disabled(savedChef.isBanned())
                .build();
    }
}
