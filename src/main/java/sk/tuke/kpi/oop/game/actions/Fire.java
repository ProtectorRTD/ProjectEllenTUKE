package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;
public class Fire <A extends Armed> extends AbstractAction<A>
{
    @Override
    public void execute(float deltaTime)
    {
        if (getActor() == null || isDone() == true)
        {
            setDone(true); //for getActor null
            return;
        }

        Fireable fireable = getActor().getFirearm().fire();
        int dx = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
        int dy = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();

        if (fireable!=null)
        {
            (getActor().getScene()).addActor(fireable, getActor().getPosX() + 8 + dx*24, getActor().getPosY() + 8 + dy*24);
            fireable.startedMoving(Direction.fromAngle(getActor().getAnimation().getRotation()));
            new Move<Fireable>(Direction.fromAngle(getActor().getAnimation().getRotation()),Float.MAX_VALUE).scheduleFor(fireable);
        }
        setDone(true);
    }
}

