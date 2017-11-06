package test;

import app.PolygonHelper;
import objects.EdgePoint;
import objects.InnerPoint;
import objects.Point;
import objects.Triangle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AppDriverTest
{
	//Polygon shape that has a solution, so that we can determine if the recursive colour method works.
	private static final ArrayList<Point> exampleWorkingPoints = new ArrayList<>(Arrays.asList(
			new EdgePoint(Point.RED),
			new EdgePoint(Point.BLUE),
			new EdgePoint(Point.YELLOW),
			new EdgePoint(Point.BLUE),
			new InnerPoint(Point.NONE)));

	//private static final ArrayList<InnerPoint> exampleInnerPoints = new ArrayList<>(Arrays.asList((InnerPoint) exampleWorkingPoints.get(4)));

	//Polygon shape that has no solution, so that we can determine if the recursive colour method works.
	private static final ArrayList<Point> exampleBrokenPoints = new ArrayList<>(Arrays.asList(
			new EdgePoint(Point.RED),
			new EdgePoint(Point.RED),
			new EdgePoint(Point.RED),
			new EdgePoint(Point.RED),
			new EdgePoint(Point.RED),
			new EdgePoint(Point.RED),
			new EdgePoint(Point.RED),
			new InnerPoint(Point.NONE)));

	private static final ArrayList<Point> exampleCompletePoints = new ArrayList(Arrays.asList(
			new EdgePoint(Point.RED),
			new EdgePoint(Point.BLUE),
			new EdgePoint(Point.YELLOW),
			new EdgePoint(Point.BLUE),
			new InnerPoint(Point.YELLOW)));

	private static final ArrayList<Point> exampleInnerPoints = new ArrayList(Arrays.asList(new InnerPoint(Point.NONE),
			new InnerPoint(Point.NONE),
			new InnerPoint(Point.NONE),
			new InnerPoint(Point.NONE),
			new InnerPoint(Point.NONE)));

	private static final ArrayList<Triangle> exampleWorkingTriangles = new ArrayList(Arrays.asList(
			new Triangle(exampleWorkingPoints.get(0), exampleWorkingPoints.get(1), exampleWorkingPoints.get(4)),
			new Triangle(exampleWorkingPoints.get(1), exampleWorkingPoints.get(2), exampleWorkingPoints.get(4)),
			new Triangle(exampleWorkingPoints.get(2), exampleWorkingPoints.get(3), exampleWorkingPoints.get(4)),
			new Triangle(exampleWorkingPoints.get(3), exampleWorkingPoints.get(0), exampleWorkingPoints.get(4))
	));

	private static final ArrayList<Triangle> exampleBrokenTriangles = new ArrayList(Arrays.asList(
			new Triangle(exampleBrokenPoints.get(0), exampleBrokenPoints.get(1), exampleBrokenPoints.get(6)),
			new Triangle(exampleBrokenPoints.get(6), exampleBrokenPoints.get(1), exampleBrokenPoints.get(7)),
			new Triangle(exampleBrokenPoints.get(1), exampleBrokenPoints.get(2), exampleBrokenPoints.get(7)),
			new Triangle(exampleBrokenPoints.get(7), exampleBrokenPoints.get(2), exampleBrokenPoints.get(3)),
			new Triangle(exampleBrokenPoints.get(7), exampleBrokenPoints.get(3), exampleBrokenPoints.get(4)),
			new Triangle(exampleBrokenPoints.get(6), exampleBrokenPoints.get(7), exampleBrokenPoints.get(4)),
			new Triangle(exampleBrokenPoints.get(5), exampleBrokenPoints.get(6), exampleBrokenPoints.get(4)),
			new Triangle(exampleBrokenPoints.get(0), exampleBrokenPoints.get(6), exampleBrokenPoints.get(5))
	));

	private static final ArrayList<Triangle> exampleCompleteTriangles = new ArrayList(Arrays.asList(
			new Triangle(exampleCompletePoints.get(0), exampleCompletePoints.get(1), exampleCompletePoints.get(4)),
			new Triangle(exampleCompletePoints.get(1), exampleCompletePoints.get(2), exampleCompletePoints.get(4)),
			new Triangle(exampleCompletePoints.get(2), exampleCompletePoints.get(3), exampleCompletePoints.get(4)),
			new Triangle(exampleCompletePoints.get(3), exampleCompletePoints.get(0), exampleCompletePoints.get(4))
	));

	private PolygonHelper driver;

	@Before
	public void setUp() throws Exception
	{
		driver = new PolygonHelper();
	}

	@Test
	public void printResultsWithSuccess() throws Exception
	{
		ArrayList<InnerPoint> innerPoints = driver.getInnerPoints(exampleWorkingPoints);
		List<InnerPoint> innerPointsClone = (List<InnerPoint>) innerPoints.clone();
		ArrayList<ArrayList<Triangle>> solution  = new ArrayList<>();

		solution = driver.recursiveColourMethod(innerPoints, innerPoints, exampleWorkingTriangles, solution);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		System.setOut(new PrintStream(outputStream));

		driver.printResults(solution, innerPoints, 3);

		String expectedOutput = "Solution 1\n" +
				"Triangle 2 is complete\n" +
				"point 1: 2\n" +
				"point 2: 3\n" +
				"point 3: 1\n" +
				"Triangle 3 is complete\n" +
				"point 1: 3\n" +
				"point 2: 2\n" +
				"point 3: 1\n" +
				"\n" +
				"Solution 2\n" +
				"Triangle 1 is complete\n" +
				"point 1: 1\n" +
				"point 2: 2\n" +
				"point 3: 3\n" +
				"Triangle 4 is complete\n" +
				"point 1: 2\n" +
				"point 2: 1\n" +
				"point 3: 3";


		assertEquals("Wrong output", expectedOutput.trim().replace("\n","").replace("\r",""),
				outputStream.toString().trim().replace("\n","").replace("\r",""));

	}

	@Test
	public void printResultsWithFailure() throws Exception
	{
		ArrayList<InnerPoint> innerPoints = driver.getInnerPoints(exampleBrokenPoints);
		List<InnerPoint> innerPointsClone = (List<InnerPoint>) innerPoints.clone();
		ArrayList<ArrayList<Triangle>> solution  = new ArrayList<>();

		solution = driver.recursiveColourMethod(innerPoints, innerPoints, exampleBrokenTriangles, solution);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		System.setOut(new PrintStream(outputStream));

		driver.printResults(solution, innerPoints, 3);

		String expectedOutput = "After running 3 out of 3 different combinations it has been determined there are no solutions to the given polygon";

		assertEquals("The print method is not printing the correct format", expectedOutput.trim(), outputStream.toString().trim());

	}

	@Test
	public void recursiveColourMethod() throws Exception
	{
		ArrayList<InnerPoint> innerPoints = driver.getInnerPoints(exampleWorkingPoints);
		List<InnerPoint> innerPointsClone = (ArrayList<InnerPoint>) innerPoints.clone();

		driver.resetCount();

		driver.recursiveColourMethod(innerPoints, innerPoints, exampleWorkingTriangles, new ArrayList<>());
		//assertTrue("Given an example that has a solution, this should return true",

		assertEquals("This method should have run 3 times" ,3, driver.getCount());

	}

	@Test
	public void checkIfSolvedIsTrue() throws Exception
	{
		assertTrue("Given exactly 2 complete Triangles, this should pass", driver.checkIfSolved(exampleCompleteTriangles));
	}

	@Test
	public void checkIfSolvedIsFalse() throws Exception
	{
		assertFalse("Given no complete Triangles, this should fail", driver.checkIfSolved(exampleBrokenTriangles));
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkIfSolvedIsGivenEmptyList() throws Exception
	{
		assertFalse("Given no complete Triangles, this should fail", driver.checkIfSolved(null));
	}

	@Test
	public void getInnerPointsReturnsValidInnerPoints() throws Exception
	{
		List<InnerPoint> innerPoints = driver.getInnerPoints(exampleCompletePoints);

		assertEquals("Incorrect size returned", 1, innerPoints.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getInnerPointsCantTakeEmptyList() throws Exception
	{
		List<InnerPoint> innerPoints = driver.getInnerPoints(null);
	}

	@Test
	public void colourAllUncolouredTrianglesDoesColour() throws Exception
	{
		ArrayList<InnerPoint> innerPoints = driver.getInnerPoints(exampleBrokenPoints);

		driver.colourAllUncolouredTriangles(innerPoints, Point.RED);

		assertEquals("Colour is not given colour", Point.RED, innerPoints.get(innerPoints.size()-1).getColour());
	}

	@Test(expected = IllegalArgumentException.class)
	public void colourAllUncolouredTrianglesWontTakeInvalidColour() throws Exception
	{
		ArrayList<InnerPoint> innerPoints = driver.getInnerPoints(exampleBrokenPoints);

		driver.colourAllUncolouredTriangles(innerPoints, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void colourAllUncolouredTrianglesWontTakeEmptyList() throws Exception
	{
		driver.colourAllUncolouredTriangles(null, Point.RED);
	}

	@Test
	public void completeTrianglesShouldReturnListOfCompleteTriangles() throws Exception
	{
		ArrayList<Triangle> completeTriangles = driver.completeTriangles(exampleCompleteTriangles);

		assertEquals("Wrong number of complete Triangles", 2, completeTriangles.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void completeTrianglesShouldNotTakeNullList() throws Exception
	{
		ArrayList<Triangle> completeTriangles = driver.completeTriangles(null);
	}

	@Test
	public void parseTrianglesShouldRetrieveTriangleList() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1,2,5","2,3,5","3,4,5","4,1,5", null);

		driver.parseTriangles(triangles, stubReader, exampleWorkingPoints);

		assertEquals("Not all Triangles were correctly parsed", 4, triangles.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTrianglesShouldNotCreateTrianglesWithMatchingPoints() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1,2,5","2,2,5","3,4,5","4,1,5", null);

		driver.parseTriangles(triangles, stubReader, exampleWorkingPoints);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTrianglesShouldNotCreateTrianglesWithLessThanThreeSides() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1,2,5","2,2","3,4,5","4,1,5", null);

		driver.parseTriangles(triangles, stubReader, exampleWorkingPoints);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTrianglesMustHaveReferenceToPointerInList() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("0,2,5","2,3,5","3,4,5","4,1,5", null);

		driver.parseTriangles(triangles, stubReader, exampleWorkingPoints);
	}

	@Test
	public void parseTrianglesWillReturnListEvenIfProvidedNullList() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1,2,5","2,3,5","3,4,5","4,1,5", null);

		driver.parseTriangles(null, stubReader, exampleWorkingPoints);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTrianglesMustNotBeSuppliedNullPointerList() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("0,2,5","2,3,5","3,4,5","4,1,5", null);

		driver.parseTriangles(triangles, stubReader, null);
	}

	@Test(expected = NumberFormatException.class)
	public void parseTrianglesShouldNotAcceptCharacters() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1,2,5","2,A,5","3,4,5","4,1,5", null);

		driver.parseTriangles(triangles, stubReader, exampleWorkingPoints);
	}

	@Test
	public void parseTrianglesShouldReadFromFile() throws Exception
	{
		ArrayList<Triangle> triangles = new ArrayList<>();

		BufferedReader bufferedReader = new BufferedReader(new FileReader("res/exampleTriangles.csv"));

		triangles = driver.parseTriangles(triangles, bufferedReader, exampleWorkingPoints);

		assertEquals("Lists aren't the same", exampleWorkingTriangles, triangles);
	}

	@Test
	public void parsePointsShouldRetrievePointList() throws Exception
	{
		ArrayList<Point> points = new ArrayList();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1","2","3","2","0", null);

		driver.parsePoints(points, stubReader);

		assertEquals("Not all points were correctly parsed", 5,points.size());
	}

	@Test
	public void parsePointsShouldRetrievePointEvenIfListIsNotInitialized() throws Exception
	{
		ArrayList<Point> points;

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1","2","3","2","0", null);

		points = driver.parsePoints(null, stubReader);

		assertTrue("List was returned as Null", points != null);
	}

	@Test
	public void parsePointsShouldAssignClassType() throws Exception
	{
		ArrayList<Point> points = new ArrayList();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1","2","3","2","0", null);

		driver.parsePoints(points, stubReader);

		assertEquals("The last Point should be an inner point", true, points.get(points.size()-1) instanceof InnerPoint);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parsePointsShouldNotAcceptInvalidColours() throws Exception
	{
		ArrayList<Point> points = new ArrayList();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("1","4","3","2","0", null);

		driver.parsePoints(points, stubReader);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parsePointsShouldNotAcceptCharacters() throws Exception
	{
		ArrayList<Point> points = new ArrayList();

		BufferedReader stubReader = Mockito.mock(BufferedReader.class);
		Mockito.when(stubReader.readLine()).thenReturn("A","4","3","2","0", null);

		driver.parsePoints(points, stubReader);
	}

	@Test
	public void parsePointsShouldRetrievePointListFromFile() throws Exception
	{
		ArrayList<Point> points = new ArrayList();

		BufferedReader bufferedReader = new BufferedReader(new FileReader("res/examplePoints.csv"));

		driver.parsePoints(points, bufferedReader);

		assertEquals("Not all points were correctly parsed", 5, points.size());
	}

}