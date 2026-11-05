package org.memestream.api.security.chefs.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChefDTO {
    private String username;
    private String password;
}
