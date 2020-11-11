package com.immfly.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Configurable
public class RedisService {
    private static final Logger logger = LogManager.getLogger(RedisService.class);

    private String host;
    private Integer port;
    private String password;

    private Jedis jedis;

    public RedisService(@Autowired Environment environment) {
        this.host = environment.getProperty("REDIS_HOST");
        this.port = Integer.valueOf(environment.getProperty("REDIS_PORT"));
        this.password = environment.getProperty("REDIS_PASSWORD");
    }

    private void initJedis() {
        if (this.jedis == null) {
            logger.debug("Initializing Jedis Instance...");
            this.jedis = new Jedis(new HostAndPort(host, port));
            try {
                this.jedis.auth(password);
            } catch (Exception e) {
                logger.error("Cannot authorize to Redis");
                throw e;
            }
            logger.debug("Jedis initialized successfully!");
        }
    }

    public Jedis getJedisInstance() {
        initJedis();
        return this.jedis;
    }
}
