package sk.tuke.kpi.oop.game.controllers;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
//import sk.tuke.kpi.oop.game.characters.Health;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MovableController implements KeyboardListener
{
    private Actor actor;
    private Set<Input.Key> key;
    private Move<Movable> move;
    private Input.Key countkey[] = new Input.Key[4];
    private int counter;
//    private Health health;
    public MovableController(Movable actor)
    {
        counter = 0;
        move = null;
        this.actor = actor;
        key= new HashSet<>();
//        if(this.actor != null && ripley != null)
//        {
//            health = ripley.getHealth();
//        }
    }
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
        // another entries of mapping ...
    );
    public void keyPressed(@NotNull Input.Key key)
    {
        if (keyDirectionMap.containsKey(key))
        {
            this.key.add(key);
            if(countkey[0] == null)
            {
                countkey[0] = key;
            }
            else if (countkey[1] == null) countkey[1] = key;
            starting();
        }
    }
    public void starting()
    {
            Direction direction = Direction.NONE;
            counter = 0;
            for(Input.Key iterator : this.key)
            {
                if(counter == 0)
                {
                    direction = keyDirectionMap.get(iterator);
                }
                if(counter > 0)
                {
                    direction = direction.combine(keyDirectionMap.get(iterator));
                }
                counter++;
            }
            if(move != null)  this.move.stop();
            if(direction != Direction.NONE)
            {
                move = new Move<Movable>(direction, Float.MAX_VALUE);
                move.scheduleFor((Movable) actor);
            }

    }

    public void keyReleased(@NotNull Input.Key key)
    {
//        if(health.getValue() <=0)
//        {
//            stop();
//        }
        if (keyDirectionMap.containsKey(key))
        {
            this.key.remove(key);
            if(key == countkey[0]) countkey[0] = null;
            if(key == countkey[1]) countkey[1] = null;
            starting();
        }
    }
}
