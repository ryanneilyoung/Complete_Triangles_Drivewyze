package app;

import objects.InnerPoint;
import objects.Point;
import objects.Triangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AppDriver
{
	//The two files that contain the data for the Polygon
	private final static String POINTS_FILE = "res/points.csv";
	private final static String TRIANGLES_FILE = "res/triangles.csv";

    public static void main(String args[])
    {
		ArrayList<Point> points = new ArrayList<>();
		ArrayList<Triangle> triangles = new ArrayList<>();
		PolygonHelper helper = new PolygonHelper();

		//Reads the Point objects from the file containing data determining placement and colour of the points in the Polygon
		try(BufferedReader reader = new BufferedReader(new FileReader(POINTS_FILE)))
		{
			points = helper.parsePoints(points, reader);
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//Reads the Triangle objects from the file containing data determining which points are contained in the Triangle object
		try(BufferedReader reader = new BufferedReader(new FileReader(TRIANGLES_FILE)))
		{
			triangles = helper.parseTriangles(triangles, reader, points);
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//Only InnerPoints are mutable, so rather than iterate through the entire list, we retrieve only the mutable Points
		ArrayList<InnerPoint> innerPoints = helper.getInnerPoints(points);

		//This sets all the Mutable dots to a default red colour
		helper.colourAllUncolouredTriangles(innerPoints, Point.RED);

		//If any solutions are found their current state is copied into this List so that we can display exactly what the solution looks like
		ArrayList<ArrayList<Triangle>> solution = new ArrayList<>();

		//This is a recursive method that iterates through every combination of colours that the map could hold and returns a list of all of the solutions
		solution = helper.recursiveColourMethod((ArrayList<InnerPoint>) innerPoints.clone(), innerPoints, triangles, solution);

		//At the end of the program it prints out a message informing the user the results of the tests
		helper.printResults(solution, innerPoints, helper.getCount());

	}
}
