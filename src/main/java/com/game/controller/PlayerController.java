package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class PlayerController {

    private final PlayerServiceImpl playerService;

    @Autowired
    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }
    
    @GetMapping("/players")
    public List<Player> getAllPlayers(@RequestParam (required = false) String name,
                                      @RequestParam (required = false) String title,
                                      @RequestParam (required = false) Race race,
                                      @RequestParam (required = false) Profession profession,
                                      @RequestParam (required = false) Long after,
                                      @RequestParam (required = false) Long before,
                                      @RequestParam (required = false) Integer minExperience,
                                      @RequestParam (required = false) Integer maxExperience,
                                      @RequestParam (required = false) Integer minLevel,
                                      @RequestParam (required = false) Integer maxLevel,
                                      @RequestParam (required = false) Boolean banned,
                                      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                      @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        return playerService.getAllPlayers(
                        Specification.where(playerService.filterByName(name)
                                .and(playerService.filterByTitle(title)))
                                .and(playerService.filterByRace(race))
                                .and(playerService.filterByProfession(profession))
                                .and(playerService.filterByDate(before, after))
                                .and(playerService.filterByExperience(minExperience, maxExperience))
                                .and(playerService.filterByLevel(minLevel, maxLevel))
                                .and(playerService.filterByAccessRestriction(banned)), pageable)
                .getContent();

    }

    @GetMapping("/players/count")
    public Integer getPlayersCount(@RequestParam (required = false) String name,
                                   @RequestParam (required = false) String title,
                                   @RequestParam (required = false) Race race,
                                   @RequestParam (required = false) Profession profession,
                                   @RequestParam (required = false) Long after,
                                   @RequestParam (required = false) Long before,
                                   @RequestParam (required = false) Integer minExperience,
                                   @RequestParam (required = false) Integer maxExperience,
                                   @RequestParam (required = false) Integer minLevel,
                                   @RequestParam (required = false) Integer maxLevel,
                                   @RequestParam (required = false) Boolean banned
    ) {
        return playerService.getAllPlayers(
                        Specification.where(playerService.filterByName(name)
                                .and(playerService.filterByTitle(title)))
                                .and(playerService.filterByRace(race))
                                .and(playerService.filterByProfession(profession))
                                .and(playerService.filterByDate(before, after))
                                .and(playerService.filterByExperience(minExperience, maxExperience))
                                .and(playerService.filterByLevel(minLevel, maxLevel))
                                .and(playerService.filterByAccessRestriction(banned)))
                .size();
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
        Player currentPlayer = playerService.getPlayer(id);
        return playerService.updatePlayer(currentPlayer, updatedPlayer);
    }

}