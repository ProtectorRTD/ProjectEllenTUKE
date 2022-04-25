package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed
{
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    private Animation normalAnimation;
    private int speed;

    private Backpack backpack;
    private Disposable disposable;
    private Health health;
    private int countlife;
    private Firearm gun;
    public Ripley()
    {
        super("Ellen");
        speed = 2;
        countlife = 0;
        health = new Health(100, 100);
        health.onExhaustion(() -> {
            this.setAnimation(new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE));
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
        });
        gun = new Gun (50,300);
//        ammo = gun.getAmmo();
        backpack = new Backpack("Ripley's backpack",10);
        normalAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        normalAnimation.pause();

    }
    public int getAmmo()
    {
        //amo
        //amo = 100;
        return gun.getAmmo();
    }
//    public void setAmmo(int value)
//    {
//        ammo = value;
//    }
//    public int getEnergy()
//    {
//        return energy;
//    }
//    public void setEnergy(int value)
//    {
//        energy = value;
//        if(energy <= 0)
//        {
//            normalAnimation.pause();
//            dieAnimation();
//        }
//    }
//    public void dieAnimation()
//    {
//        setAnimation(new Animation("sprites/player.png", 32,32, 0.1f, Animation.PlayMode.ONCE));
//        getScene().getMessageBus().publish(RIPLEY_DIED, this);
//
//    }
    @Override
    public int getSpeed()
    {
        return speed;
    }
    @Override
    public void startedMoving(Direction direction)
    {
        normalAnimation.setRotation(direction.getAngle());
        normalAnimation.play();
    }
    @Override
    public void stoppedMoving()
    {
        normalAnimation.stop();
    }
    @Override
    public Backpack getBackpack()
    {
        return backpack;
    }
    public void showRipleyState()
    {
        getScene().getGame().pushActorContainer(this.getBackpack());
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Energy " + health.getValue(), 120, yTextPos);
        getScene().getGame().getOverlay().drawText("Ammo " +this.gun.getAmmo(), 320, yTextPos);
        getScene().getGame().getOverlay().drawText("Life counter " +health.getCount(), 560, yTextPos);
    }
    public void lowEnergy()
    {
            checkHealth();
            this.health.drain(2); //
            checkHealth();
    }
    public void checkHealth()
    {
        if(this.health.getValue() <= 0 )
        {
            health.onExhaustion(() ->
            {
                System.out.println("die");
                this.setAnimation(new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE));
                (getScene()).getMessageBus().publish(RIPLEY_DIED,this);
                stop();
            });
        }

    }
    public void stop()
    {
        if(disposable != null)disposable.dispose();
    }
    @Override
    public Health getHealth()
    {
        return health;
    }
    @Override
    public Firearm getFirearm()
    {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon)
    {
        gun = weapon;
    }

    public int getLife()
    {
        return countlife;
    }
    public void setLife(int set)
    {
        countlife = set;
        health.setCountLife(countlife);
    }
    public void hpminus(int value)
    {
        health.drain(value);
    }
}

