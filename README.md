# Weather Forcasting

```
Create a toy model of the environment (taking into account things like atmosphere, topography,
geography, oceanography, or similar) that evolves over time. Then take measurements at various
locations (ie weather stations), and then have your program emit that data.
```
---

Table of Contents

* <a href="#Weather-forecasting-and-Algorithm-used">Weather forecasting and Algorithm used</a>
* <a href="#Configuring-Java-and-Maven">Configuring Java and Maven</a>
* <a href="#Running-the-application">Running the application</a>
* <a href="#Sample-Ouput">Sample Ouput</a>
* <a href="#Major-class-used-in-the-application">Major  class used in the application</a>

---

<a name="Weather-forecasting-and-Algorithm-used"></a>

## Weather forecasting and Algorithm used

```
Weather forecasting is mainly concerned with the prediction of weather condition in the given future time. 
Weather forecasts provide critical information about future weather. There are various approaches available 
in weather forecasting, from relatively simple observation of the sky to highly complex computerized 
mathematical models.
 
A new sliding window approach for the same is used in this project for weather prediction.
```

```algo

Step 1. Take matrix “CD” of last seven days for current year’s data of size 7 × 4.
Step 2. Take matrix “PD” of fourteen days for previous year’s data of size 14 × 4.
Step 3. Make 8 sliding windows of size 7 × 4 each from the matrix “PD” as 𝑊1,𝑊2,𝑊3,,.., 𝑊8
Step 4. Compute the Euclidean distance of each sliding window with the matrix “CD” as ED1, ED2, ED3, . . . , ED8
Step 5. Select matrix𝑊𝑖 as
		𝑊𝑖 = Correponding Matrix (Min.(ED𝑖))
		∀𝑖 ∈ [1, 8]
Step 6. For 𝑘 = 1 to 𝑛
		(i) For WC𝑘 compute the variation vector for the matrix “CD” of size 6 × 1 as “VC”.
		(ii) ForWC𝑘 compute the variation vector for the matrix “PD” of size 6 × 1 as “VP”.
		(iii) Mean1 =Mean (VC)
		(iv) Mean2 =Mean (VP)
		(v) Predicted Variation “𝑉” = (Mean1+ Mean2)/2
		(vi) Add “𝑉” to the previous day’s weather condition in consideration to get the predicted condition.
Step 7. End

```
<a name="Configuring-Java-and-Maven"></a>

## Configuring Java and Maven

### Installation procedure 

<h3>Install Java</h3>

```java
To run the application java 1.8 need to be installed in the machine.

Add JAVA_HOME environment variable and include in PATH variable

edit .bashrc to export JAVA_HOME.

export JAVA_HOME=/home/559162/jdk1.8.0_92

export PATH=$PATH:$JAVA_HOME/bin

check the java installation using command-line "java -version"

java version "1.8.0_92"
Java(TM) SE Runtime Environment (build 1.8.0_92-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.92-b14, mixed mode)

Now you have java installed in the machine.

```

<h3>Install Maven</h3>


Install [Maven](http://maven.apache.org/) (preferably version 3.x) by following
the [Maven installation instructions](http://maven.apache.org/download.cgi).

```mvn
Download Apache Maven and install it.

unzip apache-maven-3.3.9-bin.zip

Add the bin directory of the created directory apache-maven-3.3.9 to the PATH environment variable

```
<a name="Running-the-application"></a>

## Running the application

```running
Once Java and Maven is configured once can trigger the application by using main class "WeatherSimulator".
The application will log the resultant data in the console.
```

<a name="Sample-Ouput"></a>

## Sample Ouput

```output

MELBOURNE|12/04/2017|16.0|65.916664|1022.8333|CLOUD
SYDNEY|12/04/2017|17.833334|61.416668|1015.0|COLD
SYDNEY|13/04/2017|17.930555|61.04861|1015.4167|CLOUD
MELBOURNE|13/04/2017|15.916667|63.423607|1022.5972|CLOUD
MELBOURNE|14/04/2017|16.090279|60.72164|1022.5475|CLOUD
SYDNEY|14/04/2017|18.103008|60.79456|1015.88196|COLD
MELBOURNE|15/04/2017|16.749422|57.661507|1021.75183|CLOUD
SYDNEY|15/04/2017|18.177757|62.06168|1016.1418|COLD
MELBOURNE|16/04/2017|16.520304|60.52305|1020.2725|CLOUD
SYDNEY|16/04/2017|18.412945|61.723206|1015.54663|COLD
MELBOURNE|17/04/2017|16.060278|62.47946|1020.3331|CLOUD
SYDNEY|17/04/2017|18.128532|60.496273|1014.8344|CLOUD
SYDNEY|18/04/2017|18.034489|58.704918|1014.5982|CLOUD
MELBOURNE|18/04/2017|15.971922|63.939507|1021.05536|CLOUD
SYDNEY|19/04/2017|18.101059|57.930897|1014.79834|COLD
SYDNEY|20/04/2017|18.336851|58.690704|1014.34985|CLOUD
MELBOURNE|19/04/2017|15.890928|65.10427|1021.7035|CLOUD
SYDNEY|21/04/2017|18.484032|59.282692|1014.14417|COLD
MELBOURNE|20/04/2017|15.893073|64.13088|1021.27795|CLOUD
SYDNEY|22/04/2017|18.625175|59.930943|1013.9773|CLOUD
MELBOURNE|21/04/2017|15.909507|63.013447|1020.8837|CLOUD
MELBOURNE|22/04/2017|15.9795|61.73412|1020.45605|CLOUD
SYDNEY|23/04/2017|18.274157|61.41363|1014.4414|CLOUD
SYDNEY|24/04/2017|17.928688|60.753853|1015.4742|CLOUD
SYDNEY|25/04/2017|17.520838|61.249775|1016.9012|CLOUD
SYDNEY|26/04/2017|17.56919|62.88987|1017.97595|CLOUD

```

<a name="Major-class-used-in-the-application"></a>

## Major class used in the application

### WeatherSimulator

```
This is the entry point class of the application, this class act as thread also to handle multiple
weather environment.
This class read the configuration file and loads the history data that is used for weather forecasting.
```

### WeatherForcastingEngine

```
This class loads the history data and create a record list that can be used for creating sliding window for
calculation variation on weather.
```

### WeatherDataBuilder

```
WeatherDataBuilder is the class that find weather variation, calculate the distance between previous data
and current data. calculate mean and the over all data help ins calculating weather variation.
```

### SlidingWindow

```
SlidingWindow class take previous year data nearly fourteen day data and create slides so that each slide can 
compare with the current data to get the weather variation.
```

### WeatherSimulationHelper

```
This class provides methods that acts as major functionality in developing the application. it provide function
to calculate Euclidean Distance, Minimum Distance, Mean and Variation matrix. This class also provide some
generic validator and util function to parse date to string and vice versa
```
