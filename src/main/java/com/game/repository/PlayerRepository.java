package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    List<Player> findAll();

    List<Player> findByNameContainingIgnoreCase(String name);

}
