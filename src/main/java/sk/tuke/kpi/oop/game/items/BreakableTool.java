package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;



public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>
{
    private int remainingUse;

    public BreakableTool(int remainingUses)
    {
        this.remainingUse = remainingUses;
    }
    public int getRemainingUses()
    {
        return this.remainingUse;
    }
    public void setRemainingUses(int uses)
    {
        this.remainingUse = uses;
    }
    public void useWith(A actor)
    {
        this.remainingUse -= 1;
        if (this.remainingUse <= 0)
        {
            this.getScene().removeActor(this);

        }
    }
}
