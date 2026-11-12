package org.memestream.api.votes;

import org.memestream.api.votes.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    boolean existsByChefUsernameAndMemeId(String username, UUID memeId);
}
