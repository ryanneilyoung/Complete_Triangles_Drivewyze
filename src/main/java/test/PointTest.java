package test;

import objects.InnerPoint;
import objects.Point;
import org.junit.Before;
import org.junit.Test;

public class PointTest
{
	private InnerPoint point;
	@Before
	public void setUp()
	{
		 point = new InnerPoint(Point.RED);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cantSetInvalidColour() throws Exception
	{
		point.setColour(4);
	}

}