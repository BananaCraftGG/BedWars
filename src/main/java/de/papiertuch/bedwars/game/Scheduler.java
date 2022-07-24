package de.papiertuch.bedwars.game;

import lombok.Getter;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */
@Getter
public class Scheduler {

    private Lobby lobby;
    private Game game;
    private Border border;
    private Ending ending;

    public Scheduler() {
        this.lobby = new Lobby();
        this.game = new Game();
        this.border = new Border();
        this.ending = new Ending();
    }

}
