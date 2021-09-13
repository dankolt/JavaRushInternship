package com.game.service;

import com.game.dto.PlayerFilterDTO;
import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService {

    Player getPlayer(String id);

    Player createPlayer(Player player);

    Player updatePlayer(String id, Player newPlayer);

    void deletePlayerById(String id);

    Page<PlayerFilterDTO> filterFor(PlayerFilterDTO playerFilterDTO, Pageable pageable);

    List<PlayerFilterDTO> filterFor(PlayerFilterDTO playerFilterDTO);
}
