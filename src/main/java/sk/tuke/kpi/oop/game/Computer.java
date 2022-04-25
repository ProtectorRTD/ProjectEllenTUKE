package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer
{
    private Animation normalAnimation;
    private boolean check_computer = false;
    public Computer()
    {
        this.check_computer = false;
        normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        normalAnimation.pause();
    }
    public int add(int x, int b)
    {
        if(check_computer == false) return 0;
        return x+b;
    }
    public float add(float x, float y)
    {
        if(check_computer == false) return 0;
        return x+y;
    }
    public int sub(int x, int b)
    {
        if(check_computer == false) return 0;
        return x-b;
    }
    public float sub(float x, float y)
    {
        if(check_computer == false) return 0;
        return x-y;
    }
    public void setPowered(boolean potok)
    {
        this.check_computer = potok;
        normalAnimation.pause();
        if(potok == true)normalAnimation.play();

    }
}
