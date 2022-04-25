package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.MyScenario;

public class Main
{
    public static void main(String[] args)
    {
        // setting game window: window name and its dimensions
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 800);

        // creating instance of game application
        // using class GameApplication as implementation of interface Game
        Game game = new GameApplication(windowSetup, new LwjglBackend()); // in case of Mac OS system use "new Lwjgl2Backend()" as the second parameter

        // creating scene for game
        // using class World as implementation of interface Scene
        Scene EscapeRoom = new World("My-scenario", "maps/corect.tmx", new MyScenario.Factory());

        // adding scene into the game
        game.addScene(EscapeRoom);

        MyScenario mission = new MyScenario();
        EscapeRoom.addListener(mission);


        // running the game
        EscapeRoom.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();


    }
}
