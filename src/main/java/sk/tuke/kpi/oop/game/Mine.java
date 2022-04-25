package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import java.util.List;

public class Mine extends AbstractActor
{
    private Ripley ripley;
    public Mine()
    {
        setAnimation(new Animation("sprites/mine.png", 16, 16, 4/4));
        getAnimation().pause();
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
    private void touch() //
    {
        if(getScene() == null) return;
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof Ripley)
            {
                ripley = (Ripley) actor;
                if(actor.intersects(this))
                {
                    getAnimation().play();
                    new ActionSequence<>(
                        new Wait<>(1),
//                        new Invoke<>(this::drain),
                       new Invoke<>(this::delete),
                        new Wait<>(1)
                    ).scheduleFor(this);
                }
            }
        }
    }
    private void delete()
    {
        new When<>(
            () ->
                this.getAnimation().getCurrentFrameIndex() >=2,
            new Invoke<>(() ->
            {
                drain();
                this.getScene().removeActor(this);
            })).scheduleFor(this);
    }
    private void drain()
    {
        if(this != null && ripley.intersects(this))ripley.hpminus(15);
    }
}
