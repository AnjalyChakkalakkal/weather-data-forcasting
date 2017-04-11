# Weather Forcasting

Create a toy model of the environment (taking into account things like atmosphere, topography,
geography, oceanography, or similar) that evolves over time. Then take measurements at various
locations (ie weather stations), and then have your program emit that data.

---

Table of Contents

* <a href="#Weather-forecasting-and-Algorithm-used">Weather forecasting and Algorithm used.</a>
* <a href="#Configuring-Java-and-Maven">Configuring Java and Maven.</a>
* <a href="#Running-the-application">Running the application.</a>
* <a href="#Main-class-used-in-the-application">Main class used in the application.</a>

---

<a name="Weather-forecasting-and-Algorithm-used"></a>

## Weather forecasting and Algorithm used

Weather forecasting is mainly concerned with the prediction of weather condition in the given future time. Weather forecasts provide critical information about future weather. There are various approaches available in weather forecasting, from relatively simple observation of the sky to highly complex computerized mathematical models.

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

## Configuring Java and Maven.

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

```mvn
Download Apache Maven and install it.

unzip apache-maven-3.3.9-bin.zip

Add the bin directory of the created directory apache-maven-3.3.9 to the PATH environment variable

```
