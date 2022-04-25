package sk.tuke.kpi.oop.game;

public enum Direction
{
    NORTH (0, 1), EAST (1, 0), SOUTH (0, -1), WEST (-1, 0),


    //aditional Task
    NORTHEAST(1, 1), SOUTHEAST(1, -1), SOUTHWEST(-1, -1), NORTHWEST(-1, 1), NONE(0, 0);




    private final int dx, dy;
    Direction(int dx, int dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
    public float getAngle()
    {
        if(dx == 0)
        {
            if (dy == 1) return 0;
            else return 180;
        }
        if (dx == -1)
        {
            if(dy == 1) return 45;
            else if(dy == 0) return 90;
            else return 135;
        }
        if (dx == 1)
        {
            if (dy == -1) return 225;
            else if (dy == 0) return 270;
            else return 315;
        }
        return -1;
    }
    public int getDx()
    {
        return dx;
    }

    public int getDy()
    {
        return dy;
    }
    public Direction combine(Direction other)
    {
        if (this==other) return this;
        int new_y;
        int new_x;
        Direction direction = NONE;
        if (getDx()==other.getDx()) new_x=getDx();
        else new_x=getDx()+other.getDx();
        if (getDy()==other.getDy()) new_y=getDy();
        else new_y=getDy()+other.getDy();
        for (Direction value : Direction.values())
        {
            if (value.getDx() == new_x && value.getDy() == new_y)
            {
                direction=value;
            }
        }
        return direction;

    }
    public static Direction fromAngle(float angle)
    {

        if (angle == 0) return NORTH;
        if (angle == 45) return NORTHWEST;
        if (angle == 180) return SOUTH;
        if (angle == 90) return WEST;
        if (angle == 135) return SOUTHWEST;
        if (angle == 225) return SOUTHEAST;
        if (angle == 270) return EAST;
        return NORTHEAST;

    }

}
