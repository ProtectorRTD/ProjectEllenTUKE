package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class AlienMother extends Alien
{
    public AlienMother(int healthvalue, Behaviour<? super Alien> behaviour)
    {
        super(healthvalue,behaviour);
        //создать метод для деланье хп
        setAnimation(new Animation("sprites/mother.png", 112, 162, 0.2f));
    }


}