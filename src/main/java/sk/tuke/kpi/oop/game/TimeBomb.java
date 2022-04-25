package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;



public class TimeBomb extends AbstractActor
{
    private Animation activate_bomb;
    private Animation small_explosion;
    private boolean activated;
    private float time_for_explose;
    private boolean check; //explose
    public TimeBomb(float time)
    {
        activated = false;
        check = false;
        time_for_explose = time;
        if(time_for_explose < 0) time_for_explose = 0;
        this.activate_bomb = new Animation("sprites/bomb_activated.png", 16, 16, this.time_for_explose/6, Animation.PlayMode.ONCE);
        this.small_explosion = new Animation("sprites/small_explosion.png", 16,16, 0.125f, Animation.PlayMode.ONCE); //125 1/8 sec
        setAnimation(new Animation("sprites/bomb.png"));
    }
    public void activate() //таймер для активации когда равно 0 то запустит small и boboh
    {
        if(this.activated == true) return;
        this.activated = true;
        setAnimation(activate_bomb); //!
        moveAnimation();
    }
    private void moveAnimation()
    {
        new ActionSequence<>(
            new Wait<>(this.time_for_explose),
            new Invoke<>(this::explode)
//            new Invoke<>(this::delete)
        ).scheduleFor(this);

    }
    public boolean takeinformation()
    {
        return this.check;
    }
    public boolean takeactived()
    {
        return this.activated;
    }
    public void explode()
    {
        this.check = true;
        setAnimation(small_explosion);
        new When<>(
            () ->
            {
                return this.getAnimation().getCurrentFrameIndex() >=7;
            },
            new Invoke<>(() ->
            {
                //System.out.println("2 this "+this.takeactived()+ " actor take "+actor.takeactived() + " inf this " + this.takeinformation() + " inf actor " + actor.takeinformation());
                this.getScene().removeActor(this);
                //System.out.println("4 this "+this.takeactived()+ " actor take "+actor.takeactived() + " inf this " + this.takeinformation() + " inf actor " + actor.takeinformation() );
            })).scheduleFor(this);
    }
//    private void delete()
//    {
//        new When<>(
//            () ->
//            {
//                return this.getAnimation().getCurrentFrameIndex() >=7;
//            },
//            new Invoke<>(() ->
//            {
//                //System.out.println("2 this "+this.takeactived()+ " actor take "+actor.takeactived() + " inf this " + this.takeinformation() + " inf actor " + actor.takeinformation());
//                this.getScene().removeActor(this);
//                //System.out.println("4 this "+this.takeactived()+ " actor take "+actor.takeactived() + " inf this " + this.takeinformation() + " inf actor " + actor.takeinformation() );
//            })).scheduleFor(this);
//    }
    public boolean isActivated()
    {
        return activated;
    }

}
