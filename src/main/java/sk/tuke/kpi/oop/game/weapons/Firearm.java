package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm
{
    private int start_ammo;
    private int max_ammo;
    public Firearm(int start, int max)
    {
        start_ammo = start;
        max_ammo = max;
    }
    public Firearm(int ammo)
    {
        start_ammo = ammo;
        max_ammo = ammo;
    }
    public int getAmmo()
    {
        return start_ammo;
    }
    public void reload(int newAmmo)
    {
        this.start_ammo += newAmmo;
        if(start_ammo > max_ammo)
        {
            start_ammo = max_ammo;
        }
    }
    protected abstract Fireable createBullet();
    public Fireable fire()
    {
        if (start_ammo != 0)
        {
            start_ammo--;
            return createBullet();
        }
        else
        {
            return null;
        }
    }

    public int getMaxAmmo()
    {
        return max_ammo;
    }
}
