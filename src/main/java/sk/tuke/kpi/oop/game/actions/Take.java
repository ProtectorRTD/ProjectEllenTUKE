package sk.tuke.kpi.oop.game.actions;


import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;


public class Take<K extends Keeper> extends AbstractAction<K>
{

    public Take()
    {

    }
    @Override
    public void execute(float deltaTime)
    {
        if(getActor() == null || isDone() == true)
        {
            setDone(true);
            return;
        }
        else
        {
            Scene scene = getActor().getScene();
            List<Actor> list =getActor().getScene().getActors();
            for(Actor actor : list)
            {
                if(actor instanceof Collectible && actor.intersects(getActor()))
                try
                {
                    getActor().getBackpack().add(((Collectible) actor));
                    scene.removeActor(actor);
                    break;
                }catch (Exception ex)
                {
                    scene.getOverlay().drawText(ex.getMessage(), 0, 0).showFor(2);
                }
            }
            setDone(true);
        }
    }
}
