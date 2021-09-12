package com.game.service;

import com.game.entity.Player;
import com.game.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class PlayerValidator {

    private final Logger log = LoggerFactory.getLogger(PlayerValidator.class);

    long validateId(String id) {
        String logMessage = "Bad query parameter: id = {}";
        long longId;

        if (id == null || id.equals("")) {
            log.warn(logMessage, id);
            throw new BadRequestException("Incorrect parameter: Id");
        }

        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.warn(logMessage, id);
            throw new BadRequestException("Id must be positive integer", e);
        }

        if (longId <= 0) {
            log.warn(logMessage, id);
            throw new BadRequestException("Id must be positive integer");
        }
        return longId;
    }

    void validateName(Player player) {
        String name = player.getName();
        if (name == null || name.isEmpty() || name.length() > 12) {
            log.warn("Bad query parameter: name = {}", name);
            throw new BadRequestException("Name must not be empty or greater then 12 symbols");
        }
    }

    void validateTitle(Player player) {
        String title = player.getTitle();
        if (title == null || title.isEmpty() || title.length() > 30) {
            log.warn("Bad query parameter: title = {}", title);
            throw new BadRequestException("Title must not be empty or greater then 30 symbols");
        }
    }

    void validateExperience(Player player) {
        Integer exp = player.getExperience();
        if (exp == null || exp < 0 || exp > 10_000_000) {
            log.warn("Bad query parameter: experience = {}", exp);
            throw new BadRequestException("Experience must not be empty or less then 0 or greater then 10.000.000 symbols");
        }
    }

    void validateBirthday(Player player) {
        int minPossibleYear = 2000;
        int maxPossibleYear = 3000;
        Date birthday = player.getBirthday();

        if (birthday != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthday);
            int year = calendar.get(Calendar.YEAR);
            if (year < minPossibleYear || year > maxPossibleYear) {
                log.warn("Bad query parameter: birthday = {}", birthday);
                throw new BadRequestException("Birthday must be within the range from 2000 year to 3000 year");
            }
        } else {
            log.warn("Bad query parameter: birthday = {}", birthday);
            throw new BadRequestException("Birthday must not be empty");
        }
    }



}
