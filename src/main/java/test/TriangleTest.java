package test;

import objects.InnerPoint;
import objects.Point;
import objects.Triangle;
import org.junit.Test;

import static org.junit.Assert.*;

public class TriangleTest {
    private Triangle completeTriangle;
    private Triangle incompleteTriangle;
    private InnerPoint yellow;
    private InnerPoint additionalRed;

    @org.junit.Before
    public void setUp() throws Exception
    {
        InnerPoint red = new InnerPoint(Point.RED);
        InnerPoint blue = new InnerPoint(Point.BLUE);
        yellow = new InnerPoint(Point.YELLOW);
        additionalRed = new InnerPoint(Point.RED);

        completeTriangle = new Triangle(red, blue, yellow);
        incompleteTriangle = new Triangle(red, additionalRed, yellow);
    }

    @Test
    public void completeTriangleShowsComplete() throws Exception
    {
        assertTrue("This triangle should show as complete", completeTriangle.isComplete());
    }

    @Test
    public void incompleteTriangleShowsIncomplete() throws Exception
    {
        assertFalse("This triangle should show as incomplete", incompleteTriangle.isComplete());
    }

	@Test
	public void triangleWithoutColourShowsIncomplete() throws Exception
	{
		incompleteTriangle.setSideThree(new InnerPoint(Point.NONE));
		assertFalse("This triangle should show as incomplete", incompleteTriangle.isComplete());
	}

    @Test
    public void canChangeSide() throws Exception
    {
        completeTriangle.setSideOne(additionalRed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantChangeSideToDuplicateSide() throws Exception
    {
        completeTriangle.setSideOne(yellow);
    }

}