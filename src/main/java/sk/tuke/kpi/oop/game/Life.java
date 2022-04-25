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

public class Life extends AbstractActor
{
    private  Ripley ellen;
    public Life()
    {
        setAnimation(new Animation("sprites/life.png", 16, 16));
    }
    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::touchLife),//функция,
                new Wait<>(0.1f)//чтобы не мгновенно убивало
            )).scheduleFor(this);
    }

    private void touchLife()
    {
        if(getScene() == null) return;
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof  Ripley)
            {
                ellen = (Ripley) actor;
                break;
            }
        }
        if(ellen.intersects(this))
        {
            int getHp = ellen.getLife();
            ellen.setLife(getHp+1);
            this.getScene().removeActor(this);
        }
    }
}
