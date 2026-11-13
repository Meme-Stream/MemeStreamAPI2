package org.memestream.api.memes;

import org.memestream.api.memes.model.MemeMetadata;
import org.memestream.api.memes.model.MemeMetadataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemeMetadataRepository extends JpaRepository<MemeMetadata, UUID> {

    @Query("""
                select new org.memestream.api.memes.model.MemeMetadataDTO(
                           m.title,
                           m.description,
                           m.creationTime,
                           m.chef.username,
                           m.id,
                           count(v)
                )
                from MemeMetadata m
                left join m.votes v
                group by m.id
            """)
    List<MemeMetadataDTO> findAllMemeMetadataDTO();
}
