package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;



public class PowerSwitch extends AbstractActor
{
    private Switchable device;
    public PowerSwitch(Switchable device)
    {
        this.device = device;
        setAnimation(new Animation("sprites/switch.png", 16, 16));
    }
    public Switchable getDevice()
    {
        return this.device;
    }
    public void switchOn()
    {
        if(this.device == null) return;
        this.device.turnOn();
        getAnimation().setTint(Color.WHITE);
    }
    public void switchOff()
    {
        if(this.device == null) return;
        this.device.turnOff();
        getAnimation().setTint(Color.GRAY);
    }
}
