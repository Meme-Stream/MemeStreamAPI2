package org.memestream.api.memes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.memestream.api.chefs.model.Chef;

import java.util.UUID;

@Entity
@Table(name = "memes_metadata")
@Data
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;
}
