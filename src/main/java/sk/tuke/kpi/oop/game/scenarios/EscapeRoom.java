package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.function.Consumer;


public class EscapeRoom implements SceneListener
{

    public static class Factory implements ActorFactory
    {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            switch (name)
            {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "access card":
                    return new AccessCard();
                case "exit door":
                    return new Door("exit door", Door.Orientation.VERTICAL);
                case "front door":
                    return new Door("front door", Door.Orientation.VERTICAL);
                case "back door":
                    return  new Door("back door", Door.Orientation.HORIZONTAL);
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
//                case "alien":
//                    if (type.equals("running"))
//                        return new Alien(100, new RandomlyMoving());
//                    else if (type.equals("waiting1"))
//                        return new Alien(100, new Observing<>(Door.DOOR_FIRST, door -> door instanceof Door , new RandomlyMoving()));
//                    else if (type.equals("waiting2"))
//                        return new Alien(100, new Observing<>(Door.DOOR_BACK, door -> door instanceof Door, new RandomlyMoving()));
//
//                    else
//                        return new Alien(0, null);
//                case "alien mother":
//                    if (type.equals("waiting1"))
//                        return new AlienMother(200,new Observing<>(Door.DOOR_FIRST, door -> door instanceof Door , new RandomlyMoving()));
//                    if (type.equals("waiting2"))
//                        return new AlienMother(200,new Observing<>(Door.DOOR_BACK, door -> door instanceof Door, new RandomlyMoving()));
//                    else return new AlienMother(200,null);
                case "ammo":
                    return new Ammo();
                default: return null;
            }
            //  return null;
        }
    }


    @Override
    public void sceneCreated(@NotNull Scene scene)
    {
        Consumer<Actor> new_actor = (a) -> System.out.println("Add actor");

        scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC,new_actor);
    }

    @Override
    public void sceneInitialized(Scene scene)
    {

        Ripley ellen= scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
        scene.getGame().pushActorContainer(ellen.getBackpack());

        Disposable movable =scene.getInput().registerListener(new MovableController(ellen));
        Disposable keep =scene.getInput().registerListener(new KeeperController(ellen));

        Disposable shoot = scene.getInput().registerListener(new ShooterController(ellen));

        FireExtinguisher fireExtinguisher= new FireExtinguisher();
        ellen.getBackpack().add(fireExtinguisher);
        scene.getGame().pushActorContainer(ellen.getBackpack());

        AccessCard accessCard = new AccessCard();
        ellen.getBackpack().add(accessCard);

        scene.getMessageBus().subscribe(Door.DOOR_END, (Game)->scene.getGame().stop()); //чтобы когда открылась конечная дверь
        //сделать

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keep.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shoot.dispose());


        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ellen.stop());
    }
    //    private void messageEnd()
//    {
//
//    }
    @Override
    public void sceneUpdating(@NotNull Scene scene)
    {
        SceneListener.super.sceneUpdating(scene);
        scene.getLastActorByType(Ripley.class).showRipleyState();
    }
}
