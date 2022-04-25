package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.util.List;

public class Generator extends AbstractActor implements Break
{
    private int animationtime;
    private int hp;
    public Generator()
    {
        animationtime = 15;
        hp = 2;
        setAnimation(new Animation("sprites/generator.png", 16, 16,  this.animationtime/6, Animation.PlayMode.ONCE));
    }
    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::touch)//функция,
            )).scheduleFor(this);
    }
    public void touch()
    {
        List<Actor> list = this.getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof Bullet && actor.intersects(this) )
            {
                hp--;
                actor.getScene().removeActor(actor);
                if(hp == 0) this.getScene().removeActor(this);
                break;
            }
        }
    }

    @Override
    public void setHp(int count)
    {
        hp = count;
    }

    @Override
    public int getHp()
    {
        return hp;
    }
}
