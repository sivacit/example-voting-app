package com.example.worker;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vote {

    @Id
    private String voterId;

    private String vote;

    // Getters and Setters
    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
