package org.memestream.api.chefs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.memestream.api.memes.model.MemeMetadata;

import java.util.List;

@Entity
@Table(name = "chefs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chef {

    @Id
    @Column(length = 30)
    private String username;

    private String password;

    private boolean isBanned;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "chef_roles",
            joinColumns = @JoinColumn(name = "chef_username")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50)
    private List<ChefRole> roles;

    @OneToMany(mappedBy = "chef")
    private List<MemeMetadata> memes;
}
