package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;



import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable
{
    private int temperature;
    private int damage;
    private Animation turn_of;
    private Animation normalAnimation;
    private Animation overheated;
    private Animation broken;
    private Animation reactor_extinguished;
    private boolean broke = false;
    private boolean reactor_work = false;
    private boolean extinguishWith = false;
    private EnergyConsumer device;
    //private  Iterator<EnergyConsumer> it;

    // declaration of instance variable for set of connected devices
    private Set<EnergyConsumer> devices;

    // instantiating a set in constructor
    // (type parameter for HashSet is derived based on type of variable devices)

    public Reactor() //от температуры получал урон подправить в 1 цвике вроде сделал неправильно
    {
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        //setAnimation(normalAnimation);
        overheated = new Animation("sprites/reactor_hot.png", 80,80, -temperature * 0.00001f+0.1f,Animation.PlayMode.LOOP_PINGPONG);
        broken = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        turn_of = new Animation("sprites/reactor.png", 80, 80);
        reactor_extinguished = new Animation("sprites/reactor_extinguished.png", 80, 80);
        this.temperature = 0;
        this.damage = 0;
        devices = new HashSet<>(); //for collection
        updateAnimation();


    }
    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);

        new PerpetualReactorHeating(1).scheduleFor(this);
    }
    public boolean extinguish() // не совсем понимаю если он потушен он может ещё работать как все это делается непонятно, оставлю на потом
    {
        if(this.damage != 100) return false;
        if(temperature > 4000)
        {
            temperature = 4000;
        }
        this.extinguishWith = true;
        updateAnimation();
        return true;

    }
    public void addDevice(EnergyConsumer device)
    {
        //this.device = device;
        if(this.broke == false && this.reactor_work == true)  device.setPowered(true);
        devices.add(device);
    }
    public void updatelight()
    {
        if(device == null) return ;
        if(this.broke == false && this.reactor_work == true ) device.setPowered(true);
        else
        {
            device.setPowered(false);
        }

    }
    public void removeDevice(EnergyConsumer device)
    {
        devices.remove(device);
        device.setPowered(false);
        //this.device = null;
    }
    public void turnOn()
    {
        for(EnergyConsumer s: devices)
        {
            s.setPowered(true);
        }
        reactor_work = true;
        updateAnimation();
    }
    public void turnOff()
    {
        //it = devices.iterator();
        reactor_work = false;
        for(EnergyConsumer s: devices)
        {
            s.setPowered(false);
        }

        if(this.damage > 100 || this.temperature >= 6000) //потом проверить может это и не нужно
        {
            this.damage = 100;
            setAnimation(broken);
            return;
        }
        updateAnimation();
    }
    public boolean repair()   //1 процент damage = 40 tempature
    {
        if(broke == false  && this.damage > 0)
        {
                int number = this.damage;
                this.damage -= 50;
                if(this.damage < 0) this.damage = 0;
                else if(this.damage == 0) this.temperature = 2000 - (Math.abs(number-50)*40); //перенсти в отдельный калькулятор температуры
                else
                {
                    this.temperature = 2000+(this.damage * 40); //changed for the tests
                }
                //System.out.println(this.temperature);
                updateAnimation();
                return true;
        }
        return false;
    }
    public boolean isOn()
    {
        if(this.reactor_work == true) return true;
        return false;
    }
    public int getTemperature()
    {
        return  temperature;
    }
    public int getDamage()
    {
        return damage;
    }
    public void increaseTemperature(int x) // пофиксить что-то нетак с добавлением и чинилкой
    {
            if(x > 0 && this.reactor_work == true)
            {
                if(damage < 33) this.temperature += x;
                if(damage >=33 && damage <= 66)
                {
                    this.temperature += Math.ceil(1.5 * x);
                }
                if(damage > 66)
                {
                   this.temperature  += 2 * x;
                }
                if(this.damage < 100)
                {
                    int new_damage = Math.max((this.temperature-2000)/40, this.damage);
                    if(new_damage > 100) this.damage = 100 ;
                    else this.damage = new_damage;
                }
                //updateAnimation(this.temperature, this.damage, this.reactor_work);
            }
        updateAnimation();
    }
    public void decreaseTemperature(int decrement)
    {
        if(decrement > 0 && this.reactor_work  == true)
        {
            //int newdecrement = decrement;
            if(this.damage > 50) this.temperature -= decrement/2;
            else this.temperature -= decrement;
            updateAnimation();
        }
    }
    private void firstpartAnimation()
    {
        if((this.temperature >= 6000 || this.damage == 100 || this.broke == true)  && this.extinguishWith == false)
        {
            setAnimation(broken);
        }
        if (this.extinguishWith == true )
        {
            setAnimation(reactor_extinguished);
        }

    }
    private void lastPart()
    {
        setAnimation(normalAnimation); // когда огнетушитель не меняется
        if(this.temperature >= 4000 && this.temperature < 6000 && this.broke == false )
        {
            setAnimation(overheated);
        }
        if((this.temperature >= 6000 || this.damage == 100 || this.broke == true)  && this.extinguishWith == false)
        {
            setAnimation(broken);
            this.broke = true;
        }
    }
    private void updateAnimation()
    {
        if(this.temperature >= 6000 || this.damage == 100)
        {
            this.broke = true;
            turnOff();
        }
        if(this.reactor_work == false ) // спросить подумать, они вставляют какой реактор обычный а как тогда они заменяют подумать
        {
            setAnimation(turn_of);
            firstpartAnimation();
        }
        else
        {
            setAnimation(normalAnimation); // когда огнетушитель не меняется
            lastPart();
            if (this.extinguishWith == true)
            {
                setAnimation(reactor_extinguished);
            }
        }

        updatelight();
    }
}
