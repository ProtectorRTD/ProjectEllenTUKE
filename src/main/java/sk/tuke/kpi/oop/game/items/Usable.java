package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;

public interface Usable<A extends Actor>
{
    Class<A> getUsingActorClass();
    //нужно ли создавать какой-то класс с BreakableTool или нет не совсем понятно а также присваивание чему-тo
    void useWith(A actor);
}
