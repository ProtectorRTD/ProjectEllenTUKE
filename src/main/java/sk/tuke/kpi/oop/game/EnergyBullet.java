package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.List;

public class EnergyBullet extends AbstractActor implements Fireable
{

    private int speed;
    private Ripley ripley;
    private Animation animation;
    private int x;
    private int y;
    public EnergyBullet()
    {
        speed = 2;
        animation = (new Animation("sprites/energy_wave.png", 16, 16));
        setAnimation(animation);
        animation.setRotation(180);
    }
    @Override
    public int getSpeed()
    {
        return speed;
    }
    @Override
    public void addedToScene(Scene scene)
    {
        x = getPosX(); // -8
        if(scene != null)
        {
            super.addedToScene(scene);
            new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::shoot)//функция,
                )).scheduleFor(this);
        }

    }
    public void shoot()
    {

        if(getScene() == null) return;
        y = getPosY()-1;
        this.setPosition(x,y);
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof Ripley)
            {
                ripley = (Ripley) actor;
                if(this.intersects(ripley))
                {
                    ripley.hpminus(15);
                    this.getScene().removeActor(this);
                }
            }
        }
        if(this.getScene().getMap().intersectsWithWall(this))
        {
            collidedWithWall();
        }
    }
    public void collidedWithWall()
    {
        if(this != null && this instanceof Movable)
        {
            getScene().removeActor(this);
        }
    }
}
