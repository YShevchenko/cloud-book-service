package com.oydipi.bookservice.leadership;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpMethod;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HookPinger extends DefaultCandidate implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(HookPinger.class);

    private final HookRepository repository;
    private Scheduler scheduler;
    private RestTemplate restTemplate = new RestTemplate();
    private Set<Long> hooks = new HashSet<>();

    public HookPinger(HookRepository repository, Scheduler scheduler) {
        this.repository = repository;
        this.scheduler = scheduler;
    }

    @Override
    public void onGranted(Context ctx) {
        super.onGranted(ctx);
        scheduler.start();
    }

    @Override
    public void onRevoked(Context ctx) {
        scheduler.stop();
        super.onRevoked(ctx);
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new Hook(HttpMethod.GET, "http://localhost:8080/health",
                    "*/10 * * * * *"));
        }
        for (Hook hook : repository.findAll()) {
            if (!hooks.contains(hook.getId())) {
                hooks.add(hook.getId());
                scheduler.addTask(getTask(hook.getId()), hook.getCron());
            }
        }
    }

    private Runnable getTask(Long id) {
        return () -> {
            Hook hook = repository.findById(id).get();
            long version = hook.getVersion();
            try {
                checkVersion(hook, version);
                logger.info("Pinging: " + hook);
                restTemplate.exchange(hook.getUri(), hook.getMethod(), null, Map.class);
                updateVersion(hook, version);
            }
            catch (Exception e) {
                // Don't care
                logger.info("Missed: " + e.getMessage());
            }
        };
    }

    private void checkVersion(Hook hook, long version) {
        Hook check =  repository.findById(hook.getId()).get();
        if (check.getVersion() != version) {
            throw new RuntimeException("Version does not match: expected " + version
                    + " but found " + check.getVersion());
        }
    }

    private void updateVersion(Hook hook, long version) {
        hook.setVersion(version + 1);
        repository.save(hook);
    }

}

