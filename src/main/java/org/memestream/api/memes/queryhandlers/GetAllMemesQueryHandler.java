package org.memestream.api.memes.queryhandlers;

import org.memestream.api.Query;
import org.memestream.api.memes.MemeMetadataRepository;
import org.memestream.api.memes.model.MemeMetadataDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetAllMemesQueryHandler implements Query<Pageable, ResponseEntity<Page<MemeMetadataDTO>>> {

    private final MemeMetadataRepository memeMetadataRepository;

    public GetAllMemesQueryHandler(MemeMetadataRepository memeMetadataRepository) {
        this.memeMetadataRepository = memeMetadataRepository;
    }

    @Cacheable(value = "allMemes",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()"
    )
    @Override
    public ResponseEntity<Page<MemeMetadataDTO>> execute(Pageable pageable) {
        return ResponseEntity.ok(memeMetadataRepository.findAllMemeMetadataDTO(pageable));
    }
}
