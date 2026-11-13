package org.memestream.api.votes;

import org.memestream.api.votes.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    boolean existsByChefUsernameAndMemeId(String username, UUID memeId);

    @Transactional
    void deleteByChefUsernameAndMemeId(String username, UUID memeId);
}
