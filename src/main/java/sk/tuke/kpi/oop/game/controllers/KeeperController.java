package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener
{
    private Keeper player;

    public KeeperController(Keeper keeper)
    {
        player = keeper;
    }

    public void keyPressed(@NotNull Input.Key key)
    {
        if (key == Input.Key.ENTER)
        {
            new Take<>().scheduleFor(player);
        }
        if (key == Input.Key.BACKSPACE)
        {
            new Drop<>().scheduleFor(player);
        }
        if (key == Input.Key.S)
        {
            new Shift<>().scheduleFor(player);
        }
        if (key == Input.Key.U)
        {
            use();
        }
        if (key == Input.Key.B)
        {
            useFromBack();
        }
    }
    private void use()
    {
        Usable<?> usable = player.getScene().getActors().stream().filter(Usable.class::isInstance).filter(player::intersects).map(Usable.class::cast).findFirst().orElse(null);
        if (usable != null)
        {
            new Use<>(usable).scheduleForIntersectingWith(player);
        }

    }
    private void useFromBack()
    {
        if (player.getBackpack().peek() instanceof Usable)
        {
            Usable<?> item = (Usable<?>)player.getBackpack().peek();
            Use <?> use = new Use<> ((Usable<?>)item);
            Disposable check = use.scheduleForIntersectingWith(player);
            if(check != null)player.getBackpack().removeBreakble((Actor) item);
           //удаляет даже не взаемодействия с объектом
        }

    }
}



