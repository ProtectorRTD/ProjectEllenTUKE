package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps  implements SceneListener
{
    private Ripley ripley;
    private Energy energy;
    private Ammo ammo;



    @Override
    public void sceneInitialized(@NotNull Scene scene)
    {
        ripley = new Ripley();
        Hammer hammer = new Hammer();
        Wrench wrench = new Wrench();
        FireExtinguisher fireExtinguisher =  new FireExtinguisher();
        scene.addActor(ripley, 0, 0);


//        move = new Move(Direction.SOUTH, 0.9f);
//        move.setActor(ripley);
        //ripley.startedMoving(Direction.SOUTH);
        //new Loop<>(new Invoke<>(this::action)).scheduleFor(ripley);
        energy = new Energy();
        scene.addActor(energy,-100, 50);

        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);


        ammo = new Ammo();
        scene.addActor(ammo,-200, 50);
//        ripley.setAmmo(499);
        new When<>(
            () -> ripley.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(ripley))
        ).scheduleFor(ripley);
        ripley.getBackpack().add(hammer);
        scene.addActor(wrench, 200, -50);
        scene.addActor(fireExtinguisher, 50, 50);

        //sssss
        scene.getGame().pushActorContainer(ripley.getBackpack());
        //ripley.getBackpack().shift();

        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);

        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);

    }
//    public void action()
//    {
//        move.execute(0.05f);
//    }
    public void checkexecute()
    {
//        new ActionSequence<>(
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action),
//            new Wait<>(0.1f),
//            new Invoke(this::action)
//        ).scheduleFor(ripley);
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene)
    {
        SceneListener.super.sceneUpdating(scene);
        scene.getGame().getOverlay();
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText("| Energy: " +ripley.getHealth().getValue(), 120, yTextPos);
        scene.getGame().getOverlay().drawText("Ammo: " +ripley.getAmmo(), 320, yTextPos);
    }
}
