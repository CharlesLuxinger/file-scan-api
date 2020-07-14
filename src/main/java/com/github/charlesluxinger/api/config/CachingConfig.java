package com.github.charlesluxinger.api.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

	public static final String CACHE_NAME = "gitRepository";

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(CACHE_NAME);
	}
}
