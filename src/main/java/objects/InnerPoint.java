package objects;

public class InnerPoint extends Point
{
	public InnerPoint(int colour)
	{
		isValidColor(colour);

		super.colour = colour;
	}

	public void setColour(int colour)
	{
		isValidColor(colour);

		super.colour = colour;
	}
}
