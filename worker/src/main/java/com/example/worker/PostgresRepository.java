package com.example.worker;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PostgresRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrUpdateVote(Vote vote) {
        Vote existingVote = entityManager.find(Vote.class, vote.getVoterId());
        if (existingVote == null) {
            entityManager.persist(vote);
        } else {
            existingVote.setVote(vote.getVote());
            entityManager.merge(existingVote);
        }
    }
}
