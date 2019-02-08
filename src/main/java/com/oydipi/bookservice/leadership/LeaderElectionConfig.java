package com.oydipi.bookservice.leadership;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;

@Configuration
public class LeaderElectionConfig {

    @Bean
    public JdbcLockRegistry jdbcLockRegistry(LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }

    @Bean
    public LockRegistryLeaderInitiator leaderInitiator(LockRegistry locks,
                                                       Candidate candidate, ApplicationEventPublisher applicationEventPublisher) {
        LockRegistryLeaderInitiator initiator = new LockRegistryLeaderInitiator(locks,
                candidate);
        initiator.setLeaderEventPublisher(
                new DefaultLeaderEventPublisher(applicationEventPublisher));
        return initiator;
    }
}
