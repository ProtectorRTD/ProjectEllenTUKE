package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable
{
    private boolean is_on;
    private Animation normalAnimation;
    private Reactor reactor;
    public Cooler(Reactor reactor)
    {
        this.reactor = reactor;
        this.is_on = false;
        normalAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        normalAnimation.pause();

    }
    public void turnOn()
    {
        this.is_on = true;
        normalAnimation.play();
    }
    public void turnOff()
    {
        this.is_on = false;
        normalAnimation.pause();
    }
    private void coolReactor()
    {
        if(this.reactor == null) return;
        if(this.is_on)this.reactor.decreaseTemperature(1);
    }

    public boolean isOn()
    {
        return this.is_on;
    }

    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
