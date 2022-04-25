package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;


public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible
{
    public FireExtinguisher()
    {
        super(1);
        setAnimation(new Animation("sprites/extinguisher.png", 16, 16));
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return  Reactor.class;
    }

    @Override
    public void useWith(Reactor actor)
    {
        if(actor == null) return;
        if(actor.extinguish())super.useWith(actor);
    }

}
