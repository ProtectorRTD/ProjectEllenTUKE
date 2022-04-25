package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.weapons.Bullet;

public class Move<A extends Movable> implements Action<A>
{
    private Direction direction;
    private float duration;
    private A actor;
    private boolean done;
    private boolean first;
    public Move(Direction direction, float duration)
    {
        first = false;
        this.direction = direction;
        this.duration = duration;
        done = false;
    }
    public Move(Direction direction)
    {
        first = false;
        done = false;
        this.direction = direction;
    }
    @Override
    public @Nullable A getActor()
    {
        return actor;
    }

    @Override
    public void setActor(@Nullable A actor)
    {
        this.actor = actor;
    }

    @Override
    public boolean isDone()
    {
        return done;
    }
    @Override
    public void execute(float deltaTime)
    {
        if (this.actor == null) return;
        if (!isDone())
        {
            if (first == false)
            {
                first = true;
                this.actor.startedMoving(direction);
            }
            this.duration -= deltaTime;
            if (this.duration < 1e-5)
            {
                stop();
            }
            else
            {
                int posX = direction.getDx() * actor.getSpeed();
                int posY = direction.getDy() * actor.getSpeed();
                actor.setPosition(actor.getPosX() + direction.getDx() * actor.getSpeed(), actor.getPosY() + direction.getDy() * actor.getSpeed());
                if (actor.getScene().getMap().intersectsWithWall(actor))
                {
                    if (actor instanceof Bullet) actor.collidedWithWall();
                    else
                    {
                        actor.setPosition(actor.getPosX() - posX, actor.getPosY() - posY);
                        actor.collidedWithWall();
                    }
                }
            }
        }
    }
    public void stop()
    {
        if (actor != null)
        {
            done = true;
            actor.stoppedMoving();
        }
    }
    @Override
    public void reset()
    {
        if(actor != null) actor.stoppedMoving();
        done = false;
        duration = 0;
        first = false;
    }
}
//        System.out.println(isDone());
//    private void debugBefore()
//    {
//        System.out.println(actor.getPosX());
//        System.out.println(actor.getPosX() + direction.getDx() * actor.getSpeed());
//    }
//    private void debugAfter()
//    {
//        System.out.println("For y : "+ actor.getPosY() + " " + (actor.getPosY() + direction.getDy() * actor.getSpeed()));
//        System.out.println(getActor());
//    }

//        if(this.actor == null) return;
//            if(first == false)
//            {
//            actor.startedMoving(direction);
//            first = true;
//            }
//            if(deltaTime >= duration)
//            {
//            reset();
//            }
//            int old_X = actor.getPosX();
//            int old_y = actor.getPosY();
//            actor.setPosition(actor.getPosX() + direction.getDx() * actor.getSpeed(), actor.getPosY() + direction.getDy() * actor.getSpeed());
//            if((getActor().getScene()).getMap().intersectsWithWall(actor))
//            {
//            if(getActor() instanceof Bullet) getActor().getScene().removeActor(getActor());
//            else
//            actor.setPosition(old_X, old_y);
//            }
//            this.duration -= deltaTime;
//            if(Math.abs(deltaTime - this.duration) <= 1e-5)
//            {
//            done = true;
//            actor.stoppedMoving();
//            first = true;
//            }
