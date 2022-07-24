package de.papiertuch.bedwars.game;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Scheduler {

    private Lobby lobby;
    private Game game;
    private Border boarder;
    private Ending ending;

    public Scheduler() {
        this.lobby = new Lobby();
        this.game = new Game();
        this.boarder = new Border();
        this.ending = new Ending();
    }

    public Border getBoarder() {
        return boarder;
    }

    public Ending getEnding() {
        return ending;
    }

    public Game getGame() {
        return game;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
