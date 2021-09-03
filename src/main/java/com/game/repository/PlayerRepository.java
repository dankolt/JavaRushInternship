package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    Page<Player> findAll(Pageable pageable);

    List<Player> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
