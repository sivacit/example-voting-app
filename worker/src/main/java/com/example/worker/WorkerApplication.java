package com.example.worker;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class WorkerApplication {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }

    @PostConstruct
    public void logRedisConfig() {
        System.out.println("Configured Redis Host: " + redisHost);
        System.out.println("Configured Redis Port: " + redisPort);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, Integer.parseInt(redisPort));
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        System.out.println("Initializing StringRedisTemplate with Redis Host: " + redisHost + " and Port: " + redisPort);
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public CommandLineRunner run(StringRedisTemplate redisTemplate) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();

            while (true) {
                try {
                    System.out.println("=========================================");
                    System.out.println("Reading data from Redis...");

                    // Attempt to pop an item from the Redis list
                    String json = redisTemplate.opsForList().leftPop("votes");
                    System.out.println("========================================= JSON retrieved: " + json);

                    if (json != null) {
                        // Deserialize the JSON to a Vote object
                        Vote vote = objectMapper.readValue(json, Vote.class);
                        System.out.println("Processing vote for '" + vote.getVote() + "' by '" + vote.getVoterId() + "'");
                        // Simulate saving the vote (this would be your DB interaction)
                        System.out.println("Vote saved successfully.");
                    }

                    // Sleep for 1 second before polling again
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
