package org.memestream.api.memes;

import org.memestream.api.memes.model.MemeMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemeMetadataRepository extends JpaRepository<MemeMetadata, UUID> {
}
