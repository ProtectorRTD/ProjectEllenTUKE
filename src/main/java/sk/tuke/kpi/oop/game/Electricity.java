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

import java.util.List;

public class Electricity extends AbstractActor
{
    private String direction;
    public Electricity(String direction)
    {
        this.direction = direction;
        if(direction.equals("Vertical"))setAnimation(new Animation("sprites/electricity.png", 16, 16));
        else
        {
            setAnimation(new Animation("sprites/electricity.png", 16, 16));
            getAnimation().setRotation(90);
        }
    }
    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::touch),//функция,
                new Wait<>(0.2f)//чтобы не мгновенно убивало
            )).scheduleFor(this);
    }

    private  void touch()
    {
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof Ripley && this.intersects(actor))
            {
               Ripley ripley = (Ripley) actor;
               ripley.hpminus(100);
            }
        }
    }
    public String getDirection()
    {
        return direction;
    }
}
