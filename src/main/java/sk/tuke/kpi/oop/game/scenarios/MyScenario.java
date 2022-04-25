package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.Electricity;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.List;
import java.util.function.Consumer;


public class MyScenario implements SceneListener
{
    private Ripley ellen;
    private Disposable movable;
    private Disposable keep;
    private Disposable shoot;
    public static class Factory implements ActorFactory
    {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name)
        {
            assert name != null;
            switch (name)
            {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "second door":
                    return new Door("second door", Door.Orientation.VERTICAL);
                case "end door":
                    return new Door("end door", Door.Orientation.VERTICAL);
                case "first door":
                    return  new Door("first door", Door.Orientation.HORIZONTAL);
                case "third door":
                    return  new Door("third door", Door.Orientation.HORIZONTAL);
                case "ventilator":
                    return new Ventilator();
                case "ammo":
                    return new Ammo();
                case "live":
                    return new Life();
                case "barrel":
                    return new Barrel();
                case "mine":
                    return new Mine();
                case "energy-bullet":
                    return new EnergyBullet();
                case "generator":
                    return new Generator();
                case "electricity":
                    return new Electricity("Vertical");
                case "electricity2":
                    return new Electricity("Horizontal");
                case "trap-system-one":
                    return new Trapsystem("First");
                case "trap-system":
                    return new Trapsystem("Second");
                case "card":
                    return new AccessCard();
                case "electricity3":
                    return new Electricity("three");
                case "trap-system-three":
                    return new Trapsystem("Third");
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

        ellen= scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
        if(ellen != null) scene.getGame().pushActorContainer(ellen.getBackpack());

        movable =scene.getInput().registerListener(new MovableController(ellen));
        keep =scene.getInput().registerListener(new KeeperController(ellen));

        shoot = scene.getInput().registerListener(new ShooterController(ellen));


        scene.getGame().pushActorContainer(ellen.getBackpack());

//        AccessCard accessCard = new AccessCard();
//        ellen.getBackpack().add(accessCard);
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

        boolean check = false;
        boolean generator = false;
        List<Actor> list = scene.getActors();
        for (Actor actor : list)
        {
            if(actor instanceof EnergyBullet)
            {
                check = true;
            }
            if(actor instanceof Generator)
            {
                generator = true;
            }
        }
        if(check == false && generator == true)
        {
            list = scene.getActors();
            EnergyBullet bullet = new EnergyBullet();
            bullet.setPosition(112, 370);
            scene.addActor(bullet);
            bullet.addedToScene(scene);

            EnergyBullet bullet2 = new EnergyBullet();
            bullet2.setPosition(98, 370);
            scene.addActor(bullet2);
            bullet2.addedToScene(scene);
        }
        List<Collectible> list_v2= ellen.getBackpack().getContent();
        for (Actor actor : list_v2)
        {
            if(actor instanceof AccessCard)
            {
                //доделать конец игры чтобы когда подбирал все закончивалось
                movable.dispose();
                keep.dispose();
                shoot.dispose();
                ellen.stop();
                scene.getGame().stop();
                break;
                //добавить класс который выведет крассивый текст
            }
        }
    }
}
