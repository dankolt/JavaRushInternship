package com.game.service;

import com.game.dto.PlayerFilterDTO;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PlayerFiltration {

    Page<PlayerFilterDTO> getAllPlayers(PlayerFilterDTO playerFilterDTO, Pageable sortedByName);

    List<PlayerFilterDTO> getAllPlayers(PlayerFilterDTO playerFilterDTO);

    Specification<PlayerFilterDTO> filterByName(String name);

    Specification<PlayerFilterDTO> filterByTitle(String title);

    Specification<PlayerFilterDTO> filterByRace(Race race);

    Specification<PlayerFilterDTO> filterByProfession(Profession profession);

    Specification<PlayerFilterDTO> filterByDate(Long before, Long after);

    Specification<PlayerFilterDTO> filterByExperience(Integer minExperience, Integer maxExperience);

    Specification<PlayerFilterDTO> filterByLevel(Integer minLevel, Integer maxLevel);

    Specification<PlayerFilterDTO> filterByAccessRestriction(Boolean banned);

}
