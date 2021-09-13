package com.game.controller;

import com.game.dto.PlayerFilterDTO;
import com.game.entity.Player;
import com.game.service.PlayerService;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }
    
    @GetMapping("/players")
    public List<PlayerFilterDTO> getAllPlayers(PlayerFilterDTO playerFilterDTO,
                                               @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                               @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        return playerService.filterFor(playerFilterDTO, pageable).getContent();
    }

    @GetMapping("/players/count")
    public Integer getPlayersCount(PlayerFilterDTO playerFilterDTO) {
        return playerService.filterFor(playerFilterDTO).size();
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable("id") String id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/players/")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable("id") String id,
                               @RequestBody Player updatedPlayer) {
        return playerService.updatePlayer(id, updatedPlayer);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable("id") String id) {
        playerService.deletePlayerById(id);
    }

}