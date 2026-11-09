package org.memestream.api.chefs;

import org.memestream.api.chefs.commandhandlers.CreateChefCommandHandler;
import org.memestream.api.chefs.commandhandlers.LoginChefCommandHandler;
import org.memestream.api.chefs.model.ChefDTO;
import org.memestream.api.security.jwt.JwtDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chefs")
public class ChefController {

    private final CreateChefCommandHandler createChefCommandHandler;
    private final LoginChefCommandHandler loginChefCommandHandler;

    public ChefController(CreateChefCommandHandler createChefCommandHandler,
                          LoginChefCommandHandler loginChefCommandHandler) {
        this.createChefCommandHandler = createChefCommandHandler;
        this.loginChefCommandHandler = loginChefCommandHandler;
    }

    @PostMapping("/create")
    public ResponseEntity<JwtDTO> createChef(@RequestBody ChefDTO chefDTO) {
        return createChefCommandHandler.execute(chefDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> loginChef(@RequestBody ChefDTO chefDTO) {
        return loginChefCommandHandler.execute(chefDTO);
    }

    @GetMapping("/headchef")
    @PreAuthorize("hasRole('HEAD_CHEF')")
    public String onlyHeadChefs() {
        return "HEAD CHEF";
    }

    @GetMapping
    @PreAuthorize("hasRole('CHEF')")
    public String allChefs() {
        return "CHEF";
    }
}
