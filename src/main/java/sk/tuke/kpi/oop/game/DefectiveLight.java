package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;


public class DefectiveLight extends Light implements Repairable
{

    private Disposable disposable;
    private boolean check = false;
    public DefectiveLight()
    {
        super();
    }

    public void generate()
    {
        if((int)(Math.random()*20) + 1 == 5)
        {
            toggle();
            check = false;
        }
    }
    private void refreshLoop()
    {
        disposable = new Loop<>(new Invoke<>(this::generate)).scheduleFor(this);
    }
    public void addedToScene(Scene scene)
    {
        super.addedToScene(scene);
        disposable = new Loop<>(new Invoke<>(this::generate)).scheduleFor(this);
    }
    public boolean repair()
    {
        if(check == true || this.disposable == null) return false;
        disposable.dispose();
        check = true;
        new ActionSequence<>(new Wait<>(10), new Invoke<>(this::refreshLoop)).scheduleFor(this);
        //check = false;
        return true;
    }
}
