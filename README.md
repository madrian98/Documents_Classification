# About project
Java application to classify articles based on their content with usage of KNN-Algorithm & N-gram as text comparison method & Chebyshev,Manhattan,Euclides metrics.
GUI created with Java FX.

# Context
KNN(K-Nearest Neighbor) is an type of supervised machine-learning algorithm used for both regression&classification.<br>

It assumes the similarity between the new case/data and available cases and put the new case into the category that is most similar to the available categories.

The algorithm stores all the available data and classifies a new data point based on the similarity. This means when new data appears then it can be easily classified into a well suite category by using K- NN algorithm.

KNN algorithm helps us identify the nearest points or the groups for a query point. But to determine the closest groups or the nearest points for a query point we need some metric. For this purpose, following distance metrics were used:

### Chebyshev distance
The Chebyshev distance calculation, commonly known as the "maximum metric" in mathematics, measures distance between two points as the maximum difference over any of their axis values. In a 2D grid, for instance, if we have two points (x1, y1), and (x2, y2), the Chebyshev distance between is max(y2 - y1, x2 - x1).

### Manhattan distance
This distance metric is generally used when we are interested in the total distance traveled by the object instead of the displacement. This metric is calculated by summing the absolute difference between the coordinates of the points in n-dimensions.


### Euclides distance
This is nothing but the cartesian distance between the two points which are in the plane/hyperplane. Euclidean distance can also be visualized as the length of the straight line that joins the two points which are into consideration. This metric helps us calculate the net displacement done between the two states of an object.

### Metrics comparison
<p align="center">
  <img src="https://github.com/madrian98/Documents_Classification/blob/main/README_Images/Comparing%20Metrics.PNG" />
  Metrics comparison<br>
  [Source](https://iq.opengenus.org/euclidean-vs-manhattan-vs-chebyshev-distance/)
</p>


# Example

<p align="center">
  <img src="https://github.com/madrian98/Documents_Classification/blob/main/README_Images/GUI.PNG" />
  Application GUI
</p>


<p align="center">
  <img src="https://github.com/madrian98/Documents_Classification/blob/main/README_Images/LogicUML.png" />
  Application Logic UML
</p>


