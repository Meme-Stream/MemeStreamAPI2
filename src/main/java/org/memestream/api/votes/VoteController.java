package org.memestream.api.votes;

import lombok.RequiredArgsConstructor;
import org.memestream.api.votes.commandhandlers.DownVoteCommandHandler;
import org.memestream.api.votes.commandhandlers.UpVoteCommandHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final UpVoteCommandHandler upVoteCommandHandler;
    private final DownVoteCommandHandler downVoteCommandHandler;

    @PostMapping("/upvote/{id}")
    public ResponseEntity<Map<String, Object>> upVoteMeme(@PathVariable(name = "id") String memeId) {
        return upVoteCommandHandler.execute(memeId);
    }

    @DeleteMapping("/downvote/{id}")
    public ResponseEntity<Map<String, Object>> downVoteMeme(@PathVariable(name = "id") String memeId) {
        return downVoteCommandHandler.execute(memeId);
    }

}
