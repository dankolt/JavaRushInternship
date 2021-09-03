package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private PlayerRepository repo;

    public List<Player> findAll(Pageable pageable) {
        return repo.findAll(pageable).toList();
    }

    public long count() {
        return repo.count();
    }

    public List<Player> findByName(String name, Pageable pageable) {
        return repo.findByNameContainingIgnoreCase(name, pageable);
    }

}