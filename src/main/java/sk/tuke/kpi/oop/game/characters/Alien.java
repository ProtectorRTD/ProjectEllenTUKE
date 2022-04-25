package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;

public class Alien extends AbstractActor implements Movable, Enemy, Alive
{
    private int speed;
    private Disposable loop;
    private Alive heroes;
//    private Disposable disposable;
    private Health health;
    private Behaviour<? super Alien> behaviour;
    public Alien()
    {
        speed = 2;
        health = new Health(100, 100);
        health.onExhaustion(() -> getScene().removeActor(this));
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }
    public Alien(int valuehealth, Behaviour<? super Alien> behaviour)
    {
        speed = 2;
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(valuehealth, 100);
        this.behaviour = behaviour;
        health.onExhaustion(() -> getScene().removeActor(this));
    }

    @Override
    public int getSpeed()
    {
        return speed;
    }
    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        addBehavior();
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::touchAlien),//функция,
                new Wait<>(0.1f)//чтобы не мгновенно убивало
            )).scheduleFor(this);
    }
    public void addBehavior()
    {
        if(behaviour != null) behaviour.setUp(this);
    }
//    private void checkTouch()
//    {
//        if (disposable == null)
//        {
//            return;
//        }
//        else
//        {
//            disposable = null;
//            //attack.dispose();
//        }
//
//    }
    private void touchAlien()
    {
        if(getScene() == null) return;
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(checkClass(actor) == true)
            {
                if(actor instanceof Ripley)
                {
                    Ripley ellen = (Ripley) actor;
                    ellen.lowEnergy();
                }
                else
                {
                    heroes = (Alive) actor;
                    heroes.getHealth().drain(1);
                }
                disposeFuck();
                loop = new Loop<>(
                new ActionSequence<>(
                    new Wait<>(0),
                    new Invoke<>(this::touchAlien))
                ).scheduleFor(this);
            }

        }
    }
    private void disposeFuck()
    {
        if(loop != null)
        {
            loop.dispose();
        }
    }
//    private void forActionSequence()
//    {
//        new ActionSequence<>
//            (
//                new Invoke<>(this::damage),
//                new Wait<>(1),
//                new Invoke(this::touchAlien)
//            );
//    }
    private boolean checkClass(Actor actor)
    {
        if(actor instanceof Enemy) return false;
        if(!(actor instanceof Alive)) return false;
        if(!(this.intersects(actor))) return false;
        return true;
    }

    @Override
    public Health getHealth()
    {
        return health;
    }
}
