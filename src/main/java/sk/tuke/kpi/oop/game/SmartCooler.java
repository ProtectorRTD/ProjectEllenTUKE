package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;

import sk.tuke.kpi.gamelib.framework.actions.Loop;


public class SmartCooler extends Cooler
{
    private Reactor reactor;
    public SmartCooler(Reactor reactor)
    {
        super(reactor);
        this.reactor = reactor;
    }
    public void smarttemp()
    {
        if(this.reactor == null) return ;
        if(this.reactor.getTemperature() < 1500)
        {
            turnOff();
        }
        if(this.reactor.getTemperature() > 2500)
        {
            turnOn();
        }
    }
    @Override
    public void addedToScene(Scene scene)
    {
        if(scene == null) return ;
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this:: smarttemp)).scheduleFor(this);
    }

}
