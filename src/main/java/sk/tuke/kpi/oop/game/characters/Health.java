package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health
{
    private int start_hp; //current hp
    private int max_hp;
    private List <ExhaustionEffect> forEffect;
    private boolean die;
    private int countLife;
    public Health(int start, int max)
    {
        start_hp = start;
        max_hp = max;
        forEffect = new ArrayList<>();
        die = false;
        countLife = 0;
    }
    public Health(int start)
    {
        start_hp = start;
        max_hp = start;
        die = true;
        countLife = 0;
    }
    public int getValue()
    {
        return start_hp;
    }
    public void refill(int amount)
    {
        start_hp = amount + start_hp;
        if(start_hp > max_hp) start_hp = max_hp;
    }
    public void restore()
    {
        start_hp = max_hp;
        System.out.println(start_hp);
    }
    public void drain(int amount)
    {
        if (start_hp != 0)
        {
            if (start_hp > amount) start_hp -= amount;
            else if(countLife == 1)
            {
                setCountLife(0);
                restore();
            }
            else
            {
                exhaust();
                //что в рипли не меняем
            }
        }

    }
    public void exhaust()
    {
        if((start_hp != 0  || start_hp <= 0))
        {
            start_hp = 0;
            if (forEffect != null && die == false && countLife == 0)
            {
                die = true;
                forEffect.forEach(ExhaustionEffect::apply);
            }
        }

    }
    @FunctionalInterface
    public interface ExhaustionEffect
    {
        void apply();
    }
    public void onExhaustion(ExhaustionEffect effect)
    {
        if (forEffect!=null)
            forEffect.add(effect);
    }
    public void setCountLife(int set)
    {
        countLife = set;
    }
    public int getCount()
    {
        return countLife;
    }

}
