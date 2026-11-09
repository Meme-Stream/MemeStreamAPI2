package org.memestream.api.chefs.commandhandlers;

import org.memestream.api.Command;
import org.memestream.api.chefs.ChefRepository;
import org.memestream.api.chefs.model.ChefRole;
import org.memestream.api.chefs.model.Chef;
import org.memestream.api.chefs.model.ChefDTO;
import org.memestream.api.security.jwt.JwtDTO;
import org.memestream.api.security.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateChefCommandHandler implements Command<ChefDTO, ResponseEntity<JwtDTO>> {

    private final ChefRepository chefRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public CreateChefCommandHandler(ChefRepository chefRepository,
                                    JwtService jwtService,
                                    PasswordEncoder passwordEncoder) {
        this.chefRepository = chefRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<JwtDTO> execute(ChefDTO chefDTO) {
        var newChef = new Chef(
                chefDTO.getUsername(),
                passwordEncoder.encode(chefDTO.getPassword()),
                false,
                List.of(ChefRole.CHEF),
                List.of()
        );

        chefRepository.save(newChef);

        var token = jwtService.generateToken(User.withUsername(newChef.getUsername()).build());

        return ResponseEntity.ok(new JwtDTO(token));
    }
}
