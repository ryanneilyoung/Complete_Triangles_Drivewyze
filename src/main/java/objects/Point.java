package objects;

public abstract class Point
{
    public static final int NONE = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;
    public static final int YELLOW = 3;

    int colour;

    public int getColour()
    {
        return colour;
    }

    void isValidColor(int color) throws IllegalArgumentException
    {
        {
            switch (color)
            {
                case NONE:
                case RED:
                case BLUE:
                case YELLOW:
                    break;
                default:
                    throw new IllegalArgumentException("Invalid color");
            }
        }
    }
}
