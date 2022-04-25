package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley>
{

    private boolean use;

    public Locker()
    {
        setAnimation(new Animation("sprites/locker.png", 16,16));
        use = false;
    }
    @Override
    public Class<Ripley> getUsingActorClass()
    {
        return Ripley.class;
    }

    @Override
    public void useWith(Ripley actor)
    {
        if(use == false)
        {
            use=true;
            (getScene()).addActor(new Hammer(),getPosX(),getPosY());

        }
    }
}
