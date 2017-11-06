This is an application designed as an exercise for a coding challenge at Drivewyze.

Given a polygon, that was subdivided into smaller triangles, and had coloured points (Red, Blue, or Yellow) at each intersection,
could exactly 2 "complete" triangles be formed.

A complete triangle is a triangle that has all three colours at one of its vertices. 

This challenge is made more difficult with all points along the border of the Polygon having set colours that cannot change.

My solution involved creating 2 "maps" for this triangle, so I could systematically test the shape.
Both maps are included in this GitHub, so you can see what number I have assigned each Triangle and Point.

My solution read all this data from 2 .csv files that are also attached in the res folder. After reading through this data it creates objects for each Point and Triangle. These objects contain rules and logic that allow me to ensure data validity and test the completeness of each triangle.
To save computation time, I sort out all the mutable points (the ones not on the edge), and then colour them all a default red colour. This application treats colours as numbers according to the following values:
Red = 1
Blue = 2
Yellow = 3
After giving them a default colour, the application will recursively iterate through every possible combination of colours. To give an example it would look like the following:

111
112
113
121
122
123
Etc.…
After going through every combination, it will either print out all the solutions it found, or it will print out a count of the iterations it went through. 

Most of the inspiration for this solution came from my research on combinations and permutations. Specifically this article: https://stackoverflow.com/questions/1734035/recursive-function-to-generate-all-combinations-of-capitals-in-a-word gave me the idea to do this recursively. 

All in all, this took me I’d say 3 hours for the code and then 3 hours of testing and cleanup.

If I could do this project again I would love to do it with an interactive display. My solution should work with any Polygon, so it would be a lot cooler if you could customize the polygon however you wanted.
Given the same criteria, of just needing to solve this specific polygon, but with more of an emphasis on efficiency, this solution is a lot more involved than is entirely necessary. This could be done without recursion, and without custom objects. That being said, objects allow for safer data, and they make the code much easier to read.

The code is run through the AppDriver class in the app package. I’d recommend importing this application into IntelleJ, and then running the AppDriver class through there as it will compile all of the necessary java files and dependencies. 
This is an application designed as an exercise for a coding challenge at Drivewyze.

Given a polygon, that was subdivided into smaller triangles, and had coloured points (Red, Blue, or Yellow) at each intersection,
could exactly 2 "complete" triangles be formed.

A complete triangle is a triangle that has all three colours at one of its vertices. 

This challenge is made more difficult with all points along the border of the Polygon having set colours that cannot change.

My solution involved creating 2 "maps" for this triangle, so I could systematically test the shape.
Both maps are included in this GitHub, so you can see what number I have assigned each Triangle and Point.

My solution read all this data from 2 .csv files that are also attached in the res folder. After reading through this data it creates objects for each Point and Triangle. These objects contain rules and logic that allow me to ensure data validity and test the completeness of each triangle.
To save computation time, I sort out all the mutable points (the ones not on the edge), and then colour them all a default red colour. This application treats colours as numbers according to the following values:
Red = 1
Blue = 2
Yellow = 3
After giving them a default colour, the application will recursively iterate through every possible combination of colours. To give an example it would look like the following:

111
112
113
121
122
123
Etc.…
After going through every combination, it will either print out all the solutions it found, or it will print out a count of the iterations it went through. 

Most of the inspiration for this solution came from my research on combinations and permutations. Specifically this article: https://stackoverflow.com/questions/1734035/recursive-function-to-generate-all-combinations-of-capitals-in-a-word gave me the idea to do this recursively. 

All in all, this took me I’d say 3 hours for the code and then 3 hours of testing and cleanup.

If I could do this project again I would love to do it with an interactive display. My solution should work with any Polygon, so it would be a lot cooler if you could customize the polygon however you wanted.
Given the same criteria, of just needing to solve this specific polygon, but with more of an emphasis on efficiency, this solution is a lot more involved than is entirely necessary. This could be done without recursion, and without custom objects. That being said, objects allow for safer data, and they make the code much easier to read.

Instalation Instructions:
The code is run through the AppDriver class in the app package. I’d recommend importing this application into IntelleJ, and then running the AppDriver class through there as it will compile all of the necessary java files and dependencies. This application has a few dependencies for testing, so ensure that the maven build script is ran prior to running any of the tests.
