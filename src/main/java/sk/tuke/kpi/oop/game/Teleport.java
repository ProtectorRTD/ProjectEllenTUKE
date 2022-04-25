package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

//import java.awt.geom.Rectangle2D;


//поменять нейминг
public class Teleport extends AbstractActor
{
    private Teleport destination;
    private Player player;
    private boolean isteleport;

    private int mid; // вынести в отдельный метод
    private int ymid;

    private int midteleport;
    private int yteleport;

    public Teleport(Teleport teleport)
    {
        this.destination = teleport;

        setAnimation(new Animation ("sprites/lift.png", 48, 48));
        this.isteleport = true;
    }

    public Teleport getDestination() // return value of teleport // правильно
    {
        return destination;
    }

    public void setDestination(Teleport destinationTeleport) //нету смысла что-то менять
    {
        if(this == destinationTeleport) return;
        this.destination = destinationTeleport;
    }

    private void teleportOn()
    {
        //Rectangle2D.Float teleportArea = new Rectangle2D.Float(this.getPosX(), this.getPosY()+this.getHeight(), this.getWidth(), this.getHeight());
        if (playerin() && this.destination != null) //если игрок находится в центре и destination не равно пустое
        {
            this.destination.teleportPlayer(this.player); // отправляем его в другую функцию
        }
    }
    private void calculate()
    {
        mid = player.getPosX() + player.getWidth()/2; // вынести в отдельный метод
        ymid = player.getPosY()+ player.getHeight()/2;
        midteleport = this.getPosX() + this.getWidth()/2;
        yteleport = this.getPosY() + this.getHeight()/2;
    }
    private boolean checkinareaoftp()
    {
        if(!this.player.intersects(this)) return false; // пересечение
        if( Math.abs(midteleport - mid) > this.getWidth()/2) return false; // не находится в центре
        if(Math.abs(yteleport - ymid) > this.getHeight()/2) return false;
        if (this.isteleport == false) return false;
        return true;
    }
    private boolean playerin()
    {
        calculate();
        if(checkinareaoftp() == true) return true;
        return false;
    }

    public void teleportPlayer(Player player)
    {
        if (this.player != null )
        {
            this.player.setPosition(this.getPosX()+this.getWidth()/2 - (this.player.getWidth()/2), this.getPosY()+this.getHeight()/2-(this.player.getHeight()/2));
            this.isteleport = false;
            new When<>(
                () ->
                {
                    //debug();
                    return (player.intersects(this) == false && this.playerin() == false) ;
                },
                new Invoke<>(() ->
                {
                    this.isteleport = true;
                })
            ).scheduleFor(this);
        }

    }
    //    private void debug()
//    {
//        System.out.println("IS TELEPORT ALLOWED" + isTeleportAllowed);
//        System.out.println("");
//        System.out.println(this.playerIsIn());
//    }
    @Override
    public void addedToScene(@NotNull Scene scene)
    {
        super.addedToScene(scene);
        this.player = getScene().getLastActorByType(Player.class);
        new Loop<>(new Invoke<>(this::teleportOn)).scheduleFor(player);
    }
}
