package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop <A extends Keeper> extends AbstractAction<A>
{
    public Drop()
    {

    }
    @Override
    public void execute(float deltaTime)
    {
        if(isDone() == false && getActor() != null && getActor().getScene() != null)
        {
            Collectible subject = (Collectible) getActor().getBackpack().peek();
            if(subject != null)
            {
                int x_pos_subject = getActor().getPosX() + (16-subject.getWidth()/4); // vrode centr igroka
                int y_pos_subject = getActor().getPosY() + (16-subject.getHeight()/4); // center player
                getActor().getScene().addActor(subject, x_pos_subject , y_pos_subject);
                getActor().getBackpack().remove(subject);
            }
        }
        setDone(true);
        setDone(true);
    }
}
