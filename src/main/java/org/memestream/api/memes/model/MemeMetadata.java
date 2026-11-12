package org.memestream.api.memes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.memestream.api.chefs.model.Chef;
import org.memestream.api.votes.model.Vote;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "memes_metadata")
@Data
@NoArgsConstructor
public class MemeMetadata {

    @Id
    @Column(columnDefinition = "binary(16)", length = 16)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;
    private String title;
    private String description;
    private String storedFilename;
    private String mimeType;
    private long size;
    private Instant creationTime;

    public MemeMetadata(UUID id,
                        String title,
                        String description,
                        String storedFilename,
                        String mimeType,
                        long size,
                        Chef chef) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.storedFilename = storedFilename;
        this.mimeType = mimeType;
        this.size = size;
        this.chef = chef;
    }

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @OneToMany(mappedBy = "meme", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @PrePersist
    private void onCreate() {
        this.creationTime = Instant.now();
    }
}
