package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.NotFoundException;
import com.game.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerValidator playerValidator;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerValidator playerValidator) {
        this.playerRepository = playerRepository;
        this.playerValidator = playerValidator;
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
            if (banned == null)
                return null;
            if (banned)
                return cb.isTrue(r.get("banned"));
            else return cb.isFalse(r.get("banned"));
        };
    }

    @Override
    public Player getPlayer(String id) {
        long idAsLong = playerValidator.validateId(id);
        return playerRepository.findById(idAsLong).orElseThrow(NotFoundException::new);
    }

    private Integer calculateCurrentLevel(Integer experience) {
        //𝐿 = (√(2500 + 200·exp) − 50) / 100
        return (int) (Math.sqrt(2500.0 + 200 * experience) - 50) / 100;
    }

    private Integer calculateUntilNextLevel(Integer level, Integer exp) {
        //𝑁 = 50 ∙ (𝑙𝑣𝑙 + 1) ∙ (𝑙𝑣𝑙 + 2) − 𝑒𝑥𝑝
        return 50 * (level + 1) * (level + 2) - exp;
    }

    @Override
    public Player createPlayer(Player player) {

        playerValidator.validateName(player.getName());
        playerValidator.validateTitle(player.getTitle());
        playerValidator.validateExperience(player.getExperience());
        playerValidator.validateBirthday(player.getBirthday());

        player.setLevel(calculateCurrentLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));

        playerRepository.save(player);

        return player;
    }

    @Override
    public Player updatePlayer(String id, Player updatedPlayer) {
        Player currentPlayer = getPlayer(id);

        /*
        name”:[String],
        “title”:[String],
        “race”:[Race], --optional
        “profession”:[Profession], --optional
        “birthday”:[Long], --optional
        “banned”:[Boolean], --optional
        “experience”:[Integer] --optional
         */

        if (updatedPlayer.getName() != null && !updatedPlayer.getName().equals(currentPlayer.getName())) {
            playerValidator.validateName(updatedPlayer.getName());
            currentPlayer.setName(updatedPlayer.getName());
        }

        if (updatedPlayer.getTitle() != null && !updatedPlayer.getTitle().equals(currentPlayer.getTitle())) {
            playerValidator.validateName(updatedPlayer.getTitle());
            currentPlayer.setTitle(updatedPlayer.getTitle());
        }

        if (updatedPlayer.getRace() != null && !updatedPlayer.getRace().equals(currentPlayer.getRace())) {
            currentPlayer.setRace(updatedPlayer.getRace());
        }

        if (updatedPlayer.getProfession() != null && !updatedPlayer.getProfession().equals(currentPlayer.getProfession())) {
            currentPlayer.setProfession(updatedPlayer.getProfession());
        }

        if (updatedPlayer.getBirthday() != null && !updatedPlayer.getBirthday().equals(currentPlayer.getBirthday())) {
            playerValidator.validateBirthday(updatedPlayer.getBirthday());
            currentPlayer.setBirthday(updatedPlayer.getBirthday());
        }

        if (updatedPlayer.getExperience() != null && !updatedPlayer.getExperience().equals(currentPlayer.getExperience())) {
            playerValidator.validateExperience(updatedPlayer.getExperience());
            currentPlayer.setExperience(updatedPlayer.getExperience());

            Integer updatedLevel = calculateCurrentLevel(updatedPlayer.getExperience());
            currentPlayer.setLevel(updatedLevel);
            currentPlayer.setUntilNextLevel(calculateUntilNextLevel(updatedLevel, updatedPlayer.getExperience()));
        }

        if (updatedPlayer.getBanned() != null) {
            currentPlayer.setBanned(updatedPlayer.getBanned());
        }

        return playerRepository.save(currentPlayer);
    }
}