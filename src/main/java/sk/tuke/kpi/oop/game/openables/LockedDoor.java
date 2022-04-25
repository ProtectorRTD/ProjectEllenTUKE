package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door
{
    private boolean locked;
    public LockedDoor()
    {
        super(Orientation.HORIZONTAL);
        locked = true;
    }
    @Override
    public void open()
    {
        if(locked == false) super.open();
    }
    public void lock()
    {
        locked = false;
        this.close();
    }
    public void unlock()
    {
        locked = false;
        this.open();
    }
    boolean isLocked()
    {
        return locked;
    }
}
