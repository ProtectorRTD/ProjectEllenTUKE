package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Bullet;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Trapsystem extends AbstractActor implements Break
{
    private String key;
    private int hp;
    public Trapsystem(String key)
    {
        this.key = key;
        hp = 5;
        setAnimation(new Animation("sprites/trap_system.png", 16, 16));
    }

    @Override
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::touch)//функция,
                //new Wait<>(1)//чтобы не мгновенно убивало
            )).scheduleFor(this);
    }
    public void touch()
    {
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
                    hp--;
                    if(hp == 0)
                    {
                        this.getScene().removeActor(this);
                        for(Actor actor1 : list)
                        {
                            if(actor1 instanceof Electricity)
                            {
                                Electricity electricity = (Electricity) actor1;
                                if(electricity.getDirection().equals("Vertical") && key.equals("First"))
                                {
                                    electricity.getScene().removeActor(electricity);
                                }
                                else if(electricity.getDirection().equals("Horizontal") && key.equals("Second"))
                                {
                                    electricity.getScene().removeActor(electricity);
                                }
                                if(electricity.getDirection().equals("three") && key.equals("Third"))
                                {
                                    electricity.getScene().removeActor(electricity);
                                }
                            }
                        }
                    }
                    break;
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
        return new Ellipse2D.Float(x, y, 20, 20);
    }

    private Rectangle2D.Float chainbombmethod(Actor actor)
    {
        int x_chain = actor.getPosX();
        int y_chain = actor.getPosY();
        int width = actor.getWidth();
        int height = actor.getHeight();
        return new Rectangle2D.Float(x_chain, y_chain, width, height);
    }
}
