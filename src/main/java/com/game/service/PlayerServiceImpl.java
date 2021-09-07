package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Page<Player> getAllPlayers(Specification<Player> specification, Pageable sortedByName) {
        return playerRepository.findAll(specification, sortedByName);
    }

    @Override
    public List<Player> getAllPlayers(Specification<Player> specification) {
        return playerRepository.findAll(specification);
    }

    @Override
    public Specification<Player> filterByName(String name) {
        return (r, q, cb) -> name == null ? null : cb.like(r.get("name"), "%" + name + "%");
    }

    @Override
    public Specification<Player> filterByTitle(String title) {
        return (r, q, cb) -> title == null ? null : cb.like(r.get("title"), "%" + title + "%");
    }

    @Override
    public Specification<Player> filterByRace(Race race) {
        return (r, q, cb) -> race == null ? null : cb.equal(r.get("race"), race);
    }

    @Override
    public Specification<Player> filterByProfession(Profession profession) {
        return (r, q, cb) -> profession == null ? null : cb.equal(r.get("profession"), profession);
    }

    @Override
    public Specification<Player> filterByDate(Long before, Long after) {
        return (r, q, cb) -> {
            if (before == null && after == null)
                return null;
            if (after == null) {
                Date beforeAsDateObj = new Date(before);
                return cb.lessThanOrEqualTo(r.get("birthday"), beforeAsDateObj);
            }
            if (before == null) {
                Date afterAsDateObj = new Date(after);
                return cb.greaterThanOrEqualTo(r.get("birthday"), afterAsDateObj);
            }
            Date beforeAsDateObj = new Date(before);
            Date afterAsDateObj = new Date(after);
            return cb.between(r.get("birthday"), afterAsDateObj, beforeAsDateObj);
        };
    }

    @Override
    public Specification<Player> filterByExperience(Integer minExperience, Integer maxExperience) {
        return (r, q, cb) -> {
            if (minExperience == null && maxExperience == null)
                return null;
            if (minExperience == null)
                return cb.lessThanOrEqualTo(r.get("experience"), maxExperience);
            if (maxExperience == null)
                return cb.greaterThanOrEqualTo(r.get("experience"), minExperience);
            return cb.between(r.get("experience"), minExperience, maxExperience);
        };
    }

    @Override
    public Specification<Player> filterByLevel(Integer minLevel, Integer maxLevel) {
        return (r, q, cb) -> {
            if (minLevel == null && maxLevel == null)
                return null;
            if (minLevel == null)
                return cb.lessThanOrEqualTo(r.get("level"), maxLevel);
            if (maxLevel == null)
                return cb.greaterThanOrEqualTo(r.get("level"), minLevel);
            return cb.between(r.get("level"), minLevel, maxLevel);
        };
    }

    @Override
    public Specification<Player> filterByAccessRestriction(Boolean banned) {
        return (r, q, cb) -> {
            if ( banned == null)
                return null;
            if (banned)
                return cb.isTrue(r.get("banned"));
            else return cb.isFalse(r.get("banned"));
        };
    }

}