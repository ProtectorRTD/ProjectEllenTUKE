package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible>
{
    private String naming;
    private int capacity;
    private List<Collectible> back = new ArrayList<>();
    public Backpack(String name, int capacity)
    {
        naming = name;
        this.capacity = capacity;
    }
    @Override
    public @NotNull List<Collectible> getContent()
    {
        return List.copyOf(back);
    }

    @Override
    public int getCapacity()
    {
        return capacity;
    }

    @Override
    public int getSize()
    {
        return back.size();
    }

    @Override
    public @NotNull String getName()
    {
        return naming;
    }

    @Override
    public void add(@NotNull Collectible actor)
    {
        if(back.size() < capacity && actor instanceof Collectible)
        {
            back.add((Collectible) actor);
        }
        else
        {
            throw new IllegalStateException(getName()+" is full");
        }
    }

    @Override
    public void remove(@NotNull Collectible actor)
    {
        if(!back.isEmpty() && actor instanceof Collectible)
        {
            back.remove(actor);
        }

//        else
//        {
//            throw new IllegalStateException(getName()+"Empty");
//        }
    }
    public  void removeBreakble(Actor actor)
    {
        if(actor instanceof  BreakableTool)
        {

            int result = ((BreakableTool<?>) actor).getRemainingUses() - 1;
            ((BreakableTool<?>) actor).setRemainingUses(result);
            System.out.println(result);
            if(((BreakableTool<?>) actor).getRemainingUses() <= 0)
            {
                back.remove(actor);
            }

        }
    }

    @Override
    public @Nullable Collectible peek()
    {
        if (getSize()>0)
        {
            return back.get(getSize()-1);
        }
        return null;
    }
    @Override
    public void shift()
    {
        Collections.rotate(back, 1);
    }

    @NotNull @Override
    public Iterator<Collectible> iterator()
    {
        return back.iterator();
    }
}
