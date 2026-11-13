package org.memestream.api.votes.commandhandlers;

import org.memestream.api.Command;
import org.memestream.api.exception.BaseException;
import org.memestream.api.exception.ErrorResponse;
import org.memestream.api.exception.NotFoundException;
import org.memestream.api.memes.MemeMetadataRepository;
import org.memestream.api.votes.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class DownVoteCommandHandler implements Command<String, ResponseEntity<Map<String, Object>>> {

    private final MemeMetadataRepository memeMetadataRepository;

    private final VoteRepository voteRepository;

    public DownVoteCommandHandler(MemeMetadataRepository memeMetadataRepository,
                                  VoteRepository voteRepository) {
        this.memeMetadataRepository = memeMetadataRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> execute(String id) {
        var memeId = UUID.fromString(id);
        var meme = memeMetadataRepository.findById(memeId).orElseThrow(() -> new NotFoundException(memeId + " not found"));

        if (meme.getVotes().isEmpty()) {
            throw new BaseException(HttpStatus.CONFLICT,
                    new ErrorResponse("NoVotes",
                            "This meme doesn't have any votes",
                            HttpStatus.CONFLICT.value(),
                            Instant.now())
            );
        }

        var user = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (!voteRepository.existsByChefUsernameAndMemeId(user.getUsername(), memeId)) {
            throw new BaseException(HttpStatus.CONFLICT,
                    new ErrorResponse("DidNotVote",
                            "You haven't voted this meme",
                            HttpStatus.CONFLICT.value(),
                            Instant.now())
            );
        }

        voteRepository.deleteByChefUsernameAndMemeId(user.getUsername(), memeId);

        return ResponseEntity.ok(
                Map.of("message", "Success")
        );
    }
}
