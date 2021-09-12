package com.game.service;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerServiceImplTest {

    @Test
    public void checkCurrentLevelCalculationWithMaxExperience() {
        int maxPossibleExperience = 10_000_000;
        int maxPossibleLevel = 447;

        Assert.assertEquals(maxPossibleLevel, BigDecimal.valueOf((Math.sqrt(2500.0 + 200 * maxPossibleExperience) - 50) / 100).setScale(0, RoundingMode.HALF_UP).intValue());
    }

}