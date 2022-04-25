package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.List;

public class Door extends AbstractActor implements Openable, Usable<Actor>
{
    private boolean open;
    private Animation animationdoor;
    private Orientation orientation;
    private String name;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public static final Topic<Door> DOOR_END = Topic.create("exit door", Door.class);

    public static final Topic<Door> DOOR_FIRST = Topic.create("frond door", Door.class);
    public static final Topic<Door> DOOR_BACK = Topic.create("back door", Door.class);



    public enum Orientation {HORIZONTAL, VERTICAL};
    private boolean for_end_door;
    public Door(Orientation orientation)
    {
        this.orientation = orientation;
        if (this.orientation == Orientation.VERTICAL)
        {
            animationdoor = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        }
        if (this.orientation == Orientation.HORIZONTAL)
        {
            animationdoor = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
        }
        setAnimation(animationdoor);
        animationdoor.pause();
        this.open = false;
    }

    public Door(String name, Orientation orientation)
    {
        super(name);
        for_end_door = false;
        if(name.equals("exit door")) for_end_door = true;
        this.name = name;
        this.orientation = orientation;
        if (this.orientation == Orientation.VERTICAL)
        {
            animationdoor = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        }
        if (this.orientation == Orientation.HORIZONTAL)
        {
            animationdoor = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
        }
        setAnimation(animationdoor);
        animationdoor.pause();
        this.open = false;
    }

    @Override
    public void useWith(Actor actor)
    {
        if (isOpen())
        {
            close();
        }
        else
        {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass()
    {
        return Actor.class;
    }

    @Override
    public void open()
    {
        if (!this.open)
        {
            animationdoor.stop();
            animationdoor.setPlayMode(Animation.PlayMode.ONCE);
            animationdoor.play();
            this.open = true;
            duplicate();
            getScene().getMessageBus().publish(DOOR_OPENED, this);
            if(this.name.equals("front door"))
            {
                getScene().getMessageBus().publish(DOOR_FIRST, this);
            }
            if(this.name.equals("back door"))
            {
                getScene().getMessageBus().publish(DOOR_BACK, this);
            }
            if(for_end_door == true)
            {
                getScene().getMessageBus().publish(DOOR_END, this);
            }


        }

    }

    @Override
    public void close()
    {
        if (this.open)
        {
            animationdoor.stop();
            animationdoor.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            animationdoor.play();
            this.open = false;
            duplicate();
            findRipley();
            getScene().getMessageBus().publish(DOOR_CLOSED, this);

        }
    }
    private void findRipley()
    {
        Ripley ripley = null; //для того чтобы не застревать
        List<Actor> list = getScene().getActors();
        for (Actor actor : list)
        {
            if(actor instanceof Ripley) //чтобы найти игрока
            {
                ripley = (Ripley) actor;
                break;
            }
        }
        if (this.orientation == Orientation.VERTICAL && (ripley != null && getScene().getMap().intersectsWithWall(ripley)))
        {
            ripley.setPosition(getPosX()+17, getPosY());
        }
        else if(this.orientation == Orientation.HORIZONTAL && (ripley != null && getScene().getMap().intersectsWithWall(ripley)))
        {
            ripley.setPosition(getPosX(), getPosY()+17);
        }
    }
    private void duplicate()
    {
        if(this.open == false)
        {
            if (this.orientation == Orientation.VERTICAL)
            {
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.WALL);
            }
            else if(this.orientation == Orientation.HORIZONTAL)
            {
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
            }

        }
        else
        {
            if (this.orientation == Orientation.VERTICAL)
            {
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
            }
            else if (this.orientation == Orientation.HORIZONTAL)
            {
                getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            }
        }
    }

    @Override
    public boolean isOpen()
    {
        return this.open;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        duplicate();
    }
}

