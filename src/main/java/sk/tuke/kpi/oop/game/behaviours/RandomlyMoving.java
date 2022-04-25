package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable>
{
    private Movable Enemy;
    private int number_first_x;
    private int number_second_y;
    private Direction direction;
    private Disposable move;
    private void random()
    {
        direction = Direction.NONE;
        Random random = new Random();
        number_first_x = random.nextInt(3) - 1;
        number_second_y= random.nextInt(3) - 1;

        createDirection();
    }
    private void createDirection()
    {
        for (Direction value : Direction.values())
        {
            if (number_first_x  == value.getDx() &&  number_second_y == value.getDy())
            {
               direction = value;
            }
        }
        move();
    }
    private void move()
    {
        Enemy.getAnimation().setRotation(direction.getAngle());
        if(move != null) move.dispose();
        move = new Move<>(direction).scheduleFor(Enemy);
        System.out.println(move);
    }
    @Override
    public void setUp(Movable actor)
    {
        if (actor==null)
        {
            return;
        }
        else
        {
            Enemy = actor;
            new Loop<>
                (new ActionSequence<>
                    (
                        new Invoke<>(this::random),
                        new Wait<>(0.1f)
                    )).scheduleFor(actor);
        }
    }
}
