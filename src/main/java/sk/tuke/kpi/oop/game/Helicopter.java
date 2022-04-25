package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private int x_pos = 0;
    private int y_pos = 0;

    public Helicopter()
    {
        setAnimation(new Animation("sprites/heli.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }
    public void searchAndDestroy()
    {
        new Loop<>(new Invoke<>(this::find)).scheduleFor(this);
    }

    private int searchX(Player player)
    {
        if (this.getPosX() != player.getPosX())
        {
            if (this.getPosX() > player.getPosX())
            {
                x_pos = this.getPosX() - 1;
            }
            else
            {
                x_pos = this.getPosX() + 1;
            }
        }
        return x_pos;
    }

    private int searchY(Player player)
    {
        if (this.getPosY() != player.getPosY())
        {
            if (this.getPosY() > player.getPosY())
            {
                y_pos = this.getPosY() - 1;
            }
            else
            {
                y_pos = this.getPosY() + 1;
            }
        }
        return y_pos;
    }

    public void find()
    {
        Player game_player = getScene().getLastActorByType(Player.class);
        //this.addedToScene(game_player.getScene());
        this.setPosition(searchX(game_player), searchY(game_player));
        if (this.intersects(game_player)) game_player.setEnergy(game_player.getEnergy() - 1);

    }
}
