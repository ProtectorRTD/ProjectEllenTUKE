package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift <A extends Keeper> extends AbstractAction<A>
{

    @Override
    public void execute(float deltaTime)
    {
        if(getActor() != null && getActor().getScene() != null && getActor() instanceof Keeper && isDone() == false)
        {
            getActor().getBackpack().shift();
        }
        setDone(true);
    }
}
