package org.memestream.api.chefs.commandhandlers;

import org.memestream.api.Command;
import org.memestream.api.chefs.model.ChefDTO;
import org.memestream.api.security.jwt.JwtDTO;
import org.memestream.api.security.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginChefCommandHandler implements Command<ChefDTO, ResponseEntity<JwtDTO>> {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginChefCommandHandler(AuthenticationManager authenticationManager,
                                   JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<JwtDTO> execute(ChefDTO chefDTO) {
        var authToken = new UsernamePasswordAuthenticationToken(
                chefDTO.getUsername(),
                chefDTO.getPassword()
        );
        var auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var token = jwtService.generateToken(((UserDetails) auth.getPrincipal()));
        return ResponseEntity.ok(new JwtDTO(token));
    }
}
