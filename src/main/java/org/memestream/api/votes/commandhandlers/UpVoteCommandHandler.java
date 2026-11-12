package org.memestream.api.votes.commandhandlers;

import org.memestream.api.Command;
import org.memestream.api.exception.BaseException;
import org.memestream.api.exception.ErrorResponse;
import org.memestream.api.exception.NotFoundException;
import org.memestream.api.memes.MemeMetadataRepository;
import org.memestream.api.votes.VoteRepository;
import org.memestream.api.votes.model.Vote;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class UpVoteCommandHandler implements Command<String, ResponseEntity<Map<String, Object>>> {

    private final MemeMetadataRepository memeMetadataRepository;

    private final VoteRepository voteRepository;

    public UpVoteCommandHandler(MemeMetadataRepository memeMetadataRepository,
                                VoteRepository voteRepository) {
        this.memeMetadataRepository = memeMetadataRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public ResponseEntity<Map<String, Object>> execute(String id) {
        var memeId = UUID.fromString(id);
        var meme = memeMetadataRepository.findById(memeId).orElseThrow(() -> new NotFoundException(memeId + " not found"));

        var user = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (voteRepository.existsByChefUsernameAndMemeId(user.getUsername(), memeId)) {
            throw new BaseException(HttpStatus.CONFLICT, new ErrorResponse("AlreadyUpvoted",
                    "You have already upvoted this meme",
                    HttpStatus.CONFLICT.value(),
                    Instant.now()));
        }

        var newUpVote = new Vote(
                UUID.randomUUID(),
                meme,
                user.getUsername()
        );

        voteRepository.save(newUpVote);

        return ResponseEntity.ok(
                Map.of("message", "Success")
        );
    }
}
