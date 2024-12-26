package com.example.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.persistence.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class WorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(PostgresRepository repository, StringRedisTemplate redisTemplate) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();

            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);

                    ValueOperations<String, String> ops = redisTemplate.opsForValue();
                    String json = redisTemplate.opsForList().leftPop("votes");

                    if (json != null) {
                        Vote vote = objectMapper.readValue(json, Vote.class);
                        System.out.println("Processing vote for '" + vote.getVote() + "' by '" + vote.getVoterId() + "'");

                        // Save or update vote in the database
                        repository.saveOrUpdateVote(vote);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
