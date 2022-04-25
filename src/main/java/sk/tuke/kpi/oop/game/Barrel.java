package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;

public class Barrel extends AbstractActor implements Break
{
    private Bullet bullet;
    private int for_item;
    private Hammer hammer;
    private int hp;
    private FireExtinguisher fireExtinguisher;
    private  Ellipse2D.Float check;
    public Barrel()
    {
        hp = 1;
       setAnimation(new Animation("sprites/barrel.png", 16, 16));
    }
    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::touch)//функция,
            )).scheduleFor(this);
    }
    public void touch()
    {
        if(getScene() == null) return;
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof Bullet)
            {
                Ellipse2D.Float check = ellipseMethod();
                Rectangle2D.Float bullet = chainbombmethod(actor);
                if(check.intersects(bullet))
                {
                    actor.getScene().removeActor(actor);
                    Random();
                }
            }
        }
    }

    @Override
    public void setHp(int count)
    {
        hp = count;
    }

    @Override
    public int getHp()
    {
        return hp;
    }

    private Ellipse2D.Float ellipseMethod()
    {
        int x = this.getPosX(); // int x = this.getPosX()
        int y = this.getPosY();
        return new Ellipse2D.Float(x, y, 16, 16);
    }

    private Rectangle2D.Float chainbombmethod(Actor actor)
    {
        int x_chain = actor.getPosX();
        int y_chain = actor.getPosY();
        int width = actor.getWidth();
        int height = actor.getHeight();
        return new Rectangle2D.Float(x_chain, y_chain, width, height);
    }
    private void Random()
    {
        Random random = new Random();
        for_item = random.nextInt(2);
        int x = this.getPosX();
        int y = this.getPosY();
        if(for_item == 1)
        {
            hammer =  new Hammer();
            getScene().addActor(hammer);
            hammer.setPosition(x,y);
            fireExtinguisher = null;
        }
        else
        {
            hammer = null;
            fireExtinguisher = new FireExtinguisher();
            getScene().addActor(fireExtinguisher);
            fireExtinguisher.setPosition(x,y);
        }
        this.getScene().removeActor(this);
        //если 1 то появляется хаммер если 2 то появляется огнетушитель

    }
}
