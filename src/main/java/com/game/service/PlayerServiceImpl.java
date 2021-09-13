package com.game.service;

import com.game.dto.PlayerFilterDTO;
import com.game.entity.Player;
import com.game.exception.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerValidator playerValidator;
    private final PlayerFiltration playerFiltration;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerValidator playerValidator, PlayerFiltration playerFiltration) {
        this.playerRepository = playerRepository;
        this.playerValidator = playerValidator;
        this.playerFiltration = playerFiltration;
    }

    @Override
    public Player getPlayer(String id) {
        long idAsLong = playerValidator.validateId(id);
        return playerRepository.findById(idAsLong).orElseThrow(NotFoundException::new);
    }

    private Integer calculateCurrentLevel(Integer experience) {
        return (int) (Math.sqrt(2500.0 + 200 * experience) - 50) / 100;
    }

    private Integer calculateUntilNextLevel(Integer level, Integer exp) {
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

    @Override
    public void deletePlayerById(String id) {
        Player player = getPlayer(id);
        playerRepository.delete(player);
    }

    @Override
    public Page<PlayerFilterDTO> filterFor(PlayerFilterDTO playerFilterDTO, Pageable pageable) {
        return playerFiltration.getAllPlayers(playerFilterDTO, pageable);
    }

    @Override
    public List<PlayerFilterDTO> filterFor(PlayerFilterDTO playerFilterDTO) {
        return playerFiltration.getAllPlayers(playerFilterDTO);
    }
}