package app;

import objects.EdgePoint;
import objects.InnerPoint;
import objects.Point;
import objects.Triangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppDriver
{
	//The two files that contain the data for the Polygon
	private final static String POINTS_FILE = "res/points.csv";
	private final static String TRIANGLES_FILE = "res/triangles.csv";
	//Counter to ensure every possible combination is tested
	private static int count;

    public static void main(String args[])
    {
		ArrayList<Point> points = new ArrayList<>();
		ArrayList<Triangle> triangles = new ArrayList<>();
		AppDriver driver = new AppDriver();

		//Reads the Point objects from the file containing data determining placement and colour of the points in the Polygon
		try(BufferedReader reader = new BufferedReader(new FileReader(POINTS_FILE)))
		{
			points = driver.parsePoints(points, reader);
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//Reads the Triangle objects from the file containing data determining which points are contained in the Triangle object
		try(BufferedReader reader = new BufferedReader(new FileReader(TRIANGLES_FILE)))
		{
			triangles = driver.parseTriangles(triangles, reader, points);
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		//Only InnerPoints are mutable, so rather than iterate through the entire list, we retrieve only the mutable Points
		ArrayList<InnerPoint> innerPoints = driver.getInnerPoints(points);

		//This sets all the Mutable dots to a default red colour
		driver.colourAllUncolouredTriangles(innerPoints, Point.RED);

		//If any solutions are found their current state is copied into this List so that we can display exactly what the solution looks like
		ArrayList<ArrayList<Triangle>> solution = new ArrayList<>();

		count = 0;

		//This is a recursive method that iterates through every combination of colours that the map could hold and returns a list of all of the solutions
		solution = driver.recursiveColourMethod((ArrayList<InnerPoint>) innerPoints.clone(), innerPoints, triangles, solution);

		//At the end of the program it prints out a message informing the user the results of the tests
		driver.printResults(solution, innerPoints, count);

	}

	//method that prints out the solutions if they exist, or how many tests were ran if there was no solution.
	public void printResults(ArrayList<ArrayList<Triangle>> solution, ArrayList<InnerPoint> innerPoints, int count)
	{
		if(solution == null || solution.size() == 0)
		{
			//due to the 3 possible colours the total combinations is equal to 3^Number of mutable nodes
			int totalCombinations = new Double(Math.pow(3,innerPoints.size())).intValue();

			System.out.println("After running " + count + " out of " + totalCombinations +
					" different combinations it has been determined there are no solutions to the given polygon");
		}
		else
		{
			//This iterates through all solutions and displays the "complete" Triangles found.
			for(int i = 0; i < solution.size(); i++)
			{
				int solutionNumber = i+1;
				System.out.println("Solution " + solutionNumber);
				for(int j = 0; j < solution.get(i).size(); j++)
				{
					Triangle triangle = solution.get(i).get(j);
					if(triangle.isComplete())
					{
						int triangleNumber = j + 1;
						System.out.println("Triangle " + triangleNumber + " is complete" +
								"\npoint 1: " + triangle.getSideOne().getColour() +
								"\npoint 2: " + triangle.getSideTwo().getColour() +
								"\npoint 3: " + triangle.getSideThree().getColour());
					}
				}
				System.out.println();
			}
		}
	}


	//Recursive method that tests every possible combination of colours
	public ArrayList<ArrayList<Triangle>> recursiveColourMethod(List<InnerPoint> subArrayOfPoints, ArrayList<InnerPoint> innerPoints,
																ArrayList<Triangle> triangles, ArrayList<ArrayList<Triangle>> solution)
	{
		//If this is the end of the list no longer continue with method calls, but test each combination of colours at the last node
		//If a match is found, copy it into a list for reporting later
		if(subArrayOfPoints.size() == 1)
		{
			for(int i = 1; i < 4; i++)
			{
				subArrayOfPoints.get(0).setColour(i);
				if(checkIfSolved(triangles))
				{
					ArrayList<Triangle> currentSolution = new ArrayList<>();
					for(Triangle triangle: triangles)
					{
						currentSolution.add(new Triangle(new EdgePoint(triangle.getSideOne().getColour()), new EdgePoint(triangle.getSideTwo().getColour()), new EdgePoint(triangle.getSideThree().getColour())));
					}
					solution.add(currentSolution);
				}
			}
		}
		//If its not the end of the list, continue to check further down the list for every combination of colours at this Point.
		else
		{
			List<InnerPoint> subList = subArrayOfPoints.subList(1, subArrayOfPoints.size());
			for(int i = 1; i < 4; i++)
			{
				subArrayOfPoints.get(0).setColour(i);
				recursiveColourMethod(subList, innerPoints, triangles,solution);
			}
		}
		return solution;
	}

	//Method that checks if there are exactly 2 "Complete" Triangles in the Polygon. Will exit the program and display a message that it has been solved,
	//and shows the colour of every point
	public boolean checkIfSolved(ArrayList<Triangle> triangles)
	{
		count++;
		if(triangles == null)
		{
			throw new IllegalArgumentException("List should not be null");
		}
		return completeTriangles(triangles).size() == 2;
	}

	//Method that returns a List of Mutable point objects from a list of all Points in the Polygon
	public ArrayList<InnerPoint> getInnerPoints(ArrayList<Point> points)
	{
		if(points == null)
		{
			throw new IllegalArgumentException("List should not be null");
		}
		ArrayList<InnerPoint> innerPoints = new ArrayList<>();

		for (Point point: points)
		{
			if(point instanceof InnerPoint)
			{
				innerPoints.add((InnerPoint) point);
			}
		}
		return innerPoints;
	}

	//Method that will iterate through a list of points and will Change all Mutable points to the colour supplied
	public void colourAllUncolouredTriangles(ArrayList<InnerPoint> innerPoints, int colour)
	{
		if(innerPoints == null)
		{
			throw new IllegalArgumentException("List should not be null");
		}
		for (InnerPoint point: innerPoints)
		{
			point.setColour(colour);
		}
	}

	//Method that checks the list of Triangles in the Polygon and returns a list of all "complete" Triangles.
	public ArrayList<Triangle> completeTriangles(ArrayList<Triangle> triangles)
	{
		if(triangles == null)
		{
			throw new IllegalArgumentException("List must be populated");
		}
		ArrayList<Triangle> completeTriangles = new ArrayList<>();
		for(Triangle triangle: triangles)
		{
			if(triangle.isComplete())
			{
				completeTriangles.add(triangle);
			}
		}
		return completeTriangles;
	}

	//Method that reads the Triangle objects from the csv file and uses the existing Point objects to build a dynamic reference.
	public ArrayList<Triangle> parseTriangles(ArrayList<Triangle> triangles, BufferedReader reader, ArrayList<Point> points) throws IOException
	{
		String line;
		String splitToken = ",";

		if(triangles == null)
		{
			triangles = new ArrayList<>();
		}
		if(points == null)
		{
			throw new IllegalArgumentException("Points must be generated before Triangles can be");
		}

		while((line = reader.readLine()) != null)
		{
			//takes the csv String and converts it to int array
			int[] pointsAsInts = Arrays.stream(line.split(splitToken)).map(String::trim).mapToInt(Integer::parseInt).toArray();
			//takes the int array and pulls the Point objects from the list provided
			if(pointsAsInts.length > 3 || pointsAsInts.length < 3)
			{
				throw new IllegalArgumentException("Triangles must have exactly 3 sides");
			}
			for(int index: pointsAsInts)
			{
				if(index > points.size() || index <= 0)
				{
					//This ensures that the application won't hit an IndexOutOfBoundsException
					throw new IllegalArgumentException("Reference to Point object must point to valid Point");
				}
			}
			Triangle triangle = new Triangle(points.get(pointsAsInts[0] -1), points.get(pointsAsInts[1] -1), points.get(pointsAsInts[2] -1));
			triangles.add(triangle);
		}

		return triangles;
	}

	//Method that reads the Points Object from the csv file
	public ArrayList<Point> parsePoints(ArrayList<Point> points, BufferedReader reader) throws IOException
	{
		if(points == null)
		{
			points = new ArrayList<>();
		}

		String line;

		while((line = reader.readLine()) != null)
		{
			int colour = Integer.parseInt(line);
			//Only edge Points that are immutable already have a colour
			if(colour > 0 && colour < 4)
			{
				EdgePoint point = new EdgePoint(colour);
				points.add(point);
			}
			//Inner points that are Mutable don't hold a colour prior to the program running
			else if(colour == 0)
			{
				InnerPoint point = new InnerPoint(colour);
				points.add(point);
			}
			//If the colour is not between 0-3 it is a mistake in the csv file
			else
			{
				throw new IllegalArgumentException("invalid edge type");
			}
		}

		return points;
	}

	//Method that retrieves the number of combinations were tested
	public int getCount()
	{
		return count;
	}
	//Method that clears the counter, this is used only for testing purposes
	public void resetCount()
	{
		count = 0;
	}
}
