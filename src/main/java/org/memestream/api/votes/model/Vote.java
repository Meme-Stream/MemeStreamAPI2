package org.memestream.api.votes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.memestream.api.memes.model.MemeMetadata;

import java.util.UUID;

@Entity
@Table(name = "votes",
        uniqueConstraints = @UniqueConstraint(name = "memeId_chefUsername_uc",
                columnNames = {"meme_id", "chef_username"}
        )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @Id
    @Column(columnDefinition = "binary(16)", length = 16)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "meme_id")
    private MemeMetadata meme;

    private String chefUsername;
}
