package org.memestream.api.memes;

import org.memestream.api.memes.model.MemeMetadata;
import org.memestream.api.memes.model.MemeMetadataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemeMetadataRepository extends JpaRepository<MemeMetadata, UUID> {

    @Query(value = """
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
            """,
            countQuery = "select count(m) from MemeMetadata m"
    )
    Page<MemeMetadataDTO> findAllMemeMetadataDTO(Pageable pageable);
}
