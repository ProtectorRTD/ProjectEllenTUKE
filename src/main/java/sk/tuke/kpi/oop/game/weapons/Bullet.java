package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Alive;


import java.util.List;

public class Bullet  extends AbstractActor implements Fireable
{
    private int speed;
    private Alive actor_alive;
    public Bullet()
    {
        setAnimation(new Animation("sprites/bullet.png", 16, 16));
        speed = 4;
    }

    @Override
    public int getSpeed()
    {
        return speed;
    }
    @Override
    public void stoppedMoving()
    {
        Fireable.super.stoppedMoving();
    }
    @Override
    public void startedMoving(Direction direction)
    {
        if (direction != null && direction != Direction.NONE)
        {
            this.getAnimation().setRotation(direction.getAngle());
        }
    }

    @Override
    public void addedToScene(Scene scene)
    {
        if(scene != null)
        {
            super.addedToScene(scene);
            new Loop<>(
                new Invoke<>(this::shoot)
            ).scheduleFor(this);
        }

    }
    private void shoot()
    {
        if(getScene() == null) return;
        delete();
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if (checkActor(actor) == true )
            {
                actor_alive = (Alive)actor;
                actor_alive.getHealth().drain(10);
                collidedWithWall();
            }
        }
    }
    private void delete() //удаление со стен
    {
        if(this.getScene().getMap().intersectsWithWall(this))
        {
            collidedWithWall();
        }
    }
    private boolean checkActor(Actor actor)
    {
        if(!(this.intersects(actor))) return false;
        if(!(actor instanceof  Alive )) return false;
        return true;
    }
    @Override
    public void collidedWithWall()
    {
        if(this != null && this instanceof Movable)getScene().removeActor(this);
    }

}
