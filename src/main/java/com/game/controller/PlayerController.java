package com.game.controller;

import com.game.entity.Player;
import com.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
//             @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
//             @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
             @RequestParam (required = false) String name
    ) {
//        Pageable page = PageRequest.of(pageNumber, pageSize);
        if (name != null) {
            return gameService.findByName(name);
        } else {
            return gameService.findAll();
        }
    }

    @GetMapping("/players/count")
    public long getPlayersCount(
//            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
//            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize
    ) {
//        Pageable page = PageRequest.of(pageNumber, pageSize);
        return gameService.count();
    }

}