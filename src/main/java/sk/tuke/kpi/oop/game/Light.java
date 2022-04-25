package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable,EnergyConsumer
{
    private Animation lightof;
    private Animation lighton;
    private boolean check = false;
    private boolean potok = false;

    //private boolean broke_light = false;

    public Light()
    {
        lightof = new Animation("sprites/light_off.png", 16, 16);
        lighton = new Animation("sprites/light_on.png", 16, 16);
        setAnimation(lightof);
    }
    public void toggle()
    {
        if(this.check == false)
        {
            this.check = true;
        }
        else if(this.check)
        {
            this.check = false;
        }
        setAnimation(lightof);
        if(this.check == this.potok && this.potok) setAnimation(lighton);
    }
    public void setPowered(boolean check)
    {
        this.potok = check;
        if(this.check == this.potok && this.potok)
        {
            setAnimation(lighton);
        }
        else
        {
            setAnimation(lightof);
        }
    }

    public void turnOn()
    {
        this.check = true;
        if(this.check == this.potok == true) setAnimation(lighton);
    }
    public void turnOff()
    {
        this.check = false;
        setAnimation(lightof);
    }
    public boolean isOn()
    {
        return this.check;
    }
}
