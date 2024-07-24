package org.teadev.tunein.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class TokenBlacklistService {
    
    private Set<String> blacklist = ConcurrentHashMap.newKeySet();
    
    public void blackListToken(String token) {
        log.info("Blacklisting token {}", token);
        blacklist.add(token);
    }
    
    public boolean isTokenBlackListed(String token) {
        log.info("Verifying if token already blacklisted {}", token);
        return blacklist.contains(token);
    }
    
}
