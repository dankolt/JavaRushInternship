package com.game.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {

    @Id
    private Long id;                // ID игрока

    //Todo: понять нужно ли использовать nullable
    @Column(name = "name", length = 12, nullable = false)
    private String name;            // Имя персонажа (до 12 знаков включительно)

    @Column(name = "title", length = 30, nullable = false)
    private String title;           // Титул персонажа (до 30 знаков включительно)

    @Enumerated(EnumType.STRING)
    @Column(name = "race", nullable = false)
    private Race race;              // Раса персонажа


    @Enumerated(EnumType.STRING)
    @Column(name = "profession", nullable = false)
    private Profession profession;  // Профессия персонажа

    //Todo: разобраться с диапазоном
    //Todo: нужно ли добавлять @Temporal
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday", nullable = false)
    private Date birthday;          // Дата регистрации (Диапазон значений года 2000..3000)

    //Todo: разобраться с диапазоном
    @Column(name = "experience", nullable = false)
    private Integer experience;     // Опыт персонажа (Диапазон значений 0..10,000,000)

    @Column(name = "level", nullable = false)
    private Integer level;          // Уровень персонажа

    @Column(name = "untilNextLevel", nullable = false)
    private Integer untilNextLevel; // Остаток опыта до следующего уровня

    @Column(name = "banned", nullable = false)
    private Boolean banned;         // Забанен / не забанен

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
