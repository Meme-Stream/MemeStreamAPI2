package org.memestream.api.memes.queryhandlers;

import org.memestream.api.Query;
import org.memestream.api.memes.MemeMetadataRepository;
import org.memestream.api.memes.model.MemeMetadataDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllMemesQueryHandler implements Query<Pageable, ResponseEntity<List<MemeMetadataDTO>>> {

    private final MemeMetadataRepository memeMetadataRepository;

    public GetAllMemesQueryHandler(MemeMetadataRepository memeMetadataRepository) {
        this.memeMetadataRepository = memeMetadataRepository;
    }

    @Override
    public ResponseEntity<List<MemeMetadataDTO>> execute(Pageable pageable) {
        return ResponseEntity.ok(memeMetadataRepository.findAll(pageable)
                .stream()
                .map(mapper -> new MemeMetadataDTO(
                        mapper.getTitle(),
                        mapper.getDescription(),
                        mapper.getCreationTime(),
                        mapper.getChef().getUsername(),
                        mapper.getId().toString(),
                        mapper.getVotes().size()
                )).toList()
        );
    }
}
