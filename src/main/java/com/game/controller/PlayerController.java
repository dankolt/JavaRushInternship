package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class PlayerController {

    @Autowired
    private GameService gameService;

    @GetMapping("/players")
    public List<Player> findAllOrAny(
             @RequestParam (required = false) String name,
             @RequestParam (required = false) String title,
             @RequestParam (required = false) Race race,
             @RequestParam (required = false) Profession profession,
             @RequestParam (required = false) Long after,
             @RequestParam (required = false) Long before,
             @RequestParam (required = false) Boolean banned,
             @RequestParam (required = false) Integer minExperience,
             @RequestParam (required = false) Integer maxExperience,
             @RequestParam (required = false) Integer minLevel,
             @RequestParam (required = false) Integer maxLevel,
             @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
             @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
             @RequestParam(value = "order", required = false) PlayerOrder order
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        if (name != null) {
            return gameService.findByName(name, page);
        } else {
            return gameService.findAll(page);
        }
    }

    @GetMapping("/players/count")
    public long getPlayersCount(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return gameService.count();
    }

}