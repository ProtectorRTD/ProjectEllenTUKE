package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;


public class MissionImpossible implements SceneListener
{

    @Override
    public void sceneInitialized(@NotNull Scene scene)
    {
//        Door door = new Door();
        Ripley ellen= scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
        Disposable movable=scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeper=scene.getInput().registerListener(new KeeperController(ellen));
//        ellen = scene.getFirstActorByType(Ripley.class);

        Locker locker = new Locker();
        scene.addActor(locker, 170, 60);

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) ->  ellen.lowEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeper.dispose());

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ellen.stop());
    }
    public static class Factory implements ActorFactory
    {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name)
        {
            switch (name)
            {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "door":
                    return new LockedDoor();
                case "access card":
                    return new AccessCard();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();

                default: return null;
            }

        }
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene)
    {
        SceneListener.super.sceneUpdating(scene);
        scene.getLastActorByType(Ripley.class).showRipleyState();
    }

}
