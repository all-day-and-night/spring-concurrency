package com.example.stock.facade;

import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    @Transactional
    public void decrease(Long key, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(key)){
            Thread.sleep(100);
        }
        try{
            stockService.decrease(key, quantity);
        } finally {
            redisLockRepository.unlock(key);
        }
    }
}
