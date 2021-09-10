package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PlayerService {

    Page<Player> getAllPlayers(Specification<Player> specification, Pageable sortedByName);

    List<Player> getAllPlayers(Specification<Player> specification);

    Specification<Player> filterByName(String name);

    Specification<Player> filterByTitle(String title);

    Specification<Player> filterByRace(Race race);

    Specification<Player> filterByProfession(Profession profession);

    Specification<Player> filterByDate(Long before, Long after);

    Specification<Player> filterByExperience(Integer minExperience, Integer maxExperience);

    Specification<Player> filterByLevel(Integer minLevel, Integer maxLevel);

    Specification<Player> filterByAccessRestriction(Boolean banned);

    Player getPlayer(Long longId);
}
