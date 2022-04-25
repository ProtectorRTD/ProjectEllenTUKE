package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb
{

    // они активируются если они находятся на расстояние от 50 метров от данной бомбы
    public ChainBomb(float time)
    {
        super(time);
    }

    @Override
    public void explode() // возможно ошибка в том, что я рисую элипс лишь один раз // update
    {
        super.explode();
        calculation();
    }

    private void calculation()
    {
        Ellipse2D.Float Ellipse = ellipseMethod(); // pravilno
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if (actor instanceof ChainBomb && (((ChainBomb) actor).isActivated() == false))
            {
                Rectangle2D.Float chain_bomb = chainbombmethod(actor);
                boolean a = Ellipse.intersects(chain_bomb) ? startExplode(((ChainBomb) actor)) : false;
                System.out.println(a);
            }
        }
    }

    private boolean startExplode(ChainBomb actor)
    {
        if (actor.takeactived() == true) return false;
        actor.activate();
        return true;
    }

    private Ellipse2D.Float ellipseMethod()
    {
        int x = this.getPosX(); // int x = this.getPosX()
        int y = this.getPosY();
        return new Ellipse2D.Float(x - 42, y - 58, 100, 100);
    }

    private Rectangle2D.Float chainbombmethod(Actor actor)
    {
        int x_chain = actor.getPosX();
        int y_chain = actor.getPosY();
        int width = actor.getWidth();
        int height = actor.getHeight();
        return new Rectangle2D.Float(x_chain, y_chain - height, width, height);
    }

}
    //neviem preco nefunguje aj tento
//        new When<>(
//            () ->
//            {
//                return this.takeinformation();
//            },
//            new Invoke<>(() ->
//            {
//                //System.out.println("2 this "+this.takeactived()+ " actor take "+actor.takeactived() + " inf this " + this.takeinformation() + " inf actor " + actor.takeinformation());
//                actor.activate();
//                //System.out.println("4 this "+this.takeactived()+ " actor take "+actor.takeactived() + " inf this " + this.takeinformation() + " inf actor " + actor.takeinformation() );
//            })).scheduleFor(this);
//    private void debug( Ellipse2D.Float Ellipse, Rectangle2D.Float chain_bomb)
//    {
//        System.out.println("Elipse - get center x " + Ellipse.getCenterX());
//        System.out.println("Elipse - get center y " +Ellipse.getCenterY());
//        System.out.println("Elipse - min X " +Ellipse.getMinX());
//        System.out.println("Elipse - min Y "+Ellipse.getMinY());
//        System.out.println("Elipse - max X "+Ellipse.getMaxX());
//        System.out.println("Elipse - max y "+Ellipse.getMaxY());
//        System.out.println("chain_bomb - get center x " + chain_bomb.getCenterX());
//        System.out.println("chain_bomb - get center y " +chain_bomb.getCenterY());
//        System.out.println("chain_bomb - min X " +chain_bomb.getMinX());
//        System.out.println("chain_bomb - min Y "+chain_bomb.getMinY());
//        System.out.println("chain_bomb - max X "+chain_bomb.getMaxX());
//        System.out.println("chain_bomb - max y "+chain_bomb.getMaxY());
//    }
    //  previous,  neviem prečo nefunguje
//        private void startExplode(ChainBomb actor)
//        {
//            float time = this.getdata();
//            if(this.explode == true)
//            {
//                new ActionSequence<>(
//                    new Wait<>(time),
//                    new Invoke<>(actor::activate)
//                ).scheduleFor(this);
//            }
//        }


