package objects;

public class Triangle
{
    //Variables
    private Point sideOne;
    private Point sideTwo;
    private Point sideThree;


    public Triangle(Point sideOne, Point sideTwo, Point sideThree)
    {
        this.sideOne = sideOne;
        this.sideTwo = sideTwo;
        this.sideThree = sideThree;

        isValidTriangle();
    }

    public Point getSideOne() {
        return sideOne;
    }

    public void setSideOne(Point sideOne) {
        this.sideOne = sideOne;

        isValidTriangle();
    }

    public Point getSideTwo() {
        return sideTwo;
    }

    public void setSideTwo(Point sideTwo) {
        this.sideTwo = sideTwo;

        isValidTriangle();
	}

    public Point getSideThree() {
        return sideThree;
    }

    public void setSideThree(Point sideThree) {
        this.sideThree = sideThree;
		isValidTriangle();
	}

    public boolean isComplete()
    {
        return (sideOne.getColour() != sideTwo.getColour() && sideOne.getColour() != sideThree.getColour() && sideTwo.getColour() != sideThree.getColour() && sideOne.getColour() != Point.NONE
				&& sideTwo.getColour() != Point.NONE && sideThree.getColour() != Point.NONE);
    }

    private void isValidTriangle() throws IllegalArgumentException
    {
        if(sideOne == sideTwo || sideOne == sideThree || sideTwo == sideThree)
        {
            throw new IllegalArgumentException("sides cannot be the same point object");
        }

    }

    @Override
    public boolean equals(Object obj)
    {
        Triangle other = (Triangle) obj;

        return this.sideOne == other.getSideOne()
                && this.sideTwo == other.getSideTwo()
                && this.sideThree == other.getSideThree();
    }
}
