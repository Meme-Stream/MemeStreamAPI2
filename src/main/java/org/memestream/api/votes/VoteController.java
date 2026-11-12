package org.memestream.api.votes;

import org.memestream.api.votes.commandhandlers.UpVoteCommandHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final UpVoteCommandHandler upVoteCommandHandler;

    public VoteController(UpVoteCommandHandler upVoteCommandHandler) {
        this.upVoteCommandHandler = upVoteCommandHandler;
    }

    @PostMapping("/upvote/{id}")
    public ResponseEntity<Map<String, Object>> upVoteMeme(@PathVariable(name = "id") String memeId) {
        return upVoteCommandHandler.execute(memeId);
    }
}
