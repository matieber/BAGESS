# BAGESS (Battery Aware Green Edge Scenario Sequencer)
Powered by [<img src="https://cdn.clever-cloud.com/uploads/2023/06/java.svg" alt="Java" style="width:20%; height: 15%;" />](https://www.java.com)

## What is BAGESS?

BAGESS is a Java-based software to near-optimally sorting test scenarios when experimenting with in-lab smartphone-based edge computing environments. BAGESS models the problem using the ATSP (Asymmetric Traveling Salesman Problem) and solves it using genetic algorithms. The software is purely based on Java; no external dependencies are needed.

Please refer to BAGESS as: 

*BAGESS: A software module based on evolutionary algorithms to schedule load-balancing scenarios over smartphone-based clusters at the Edge, by Virginia Yannibelli, Matías Hirsch, Juan Toloza, Tim A. Majchrzak, Tor-Morten Grønli, Alejandro Zunino and Cristian Mateos. IEEE Access. 2024.*

## Running instructions

Please make sure you have installed a modern version of Java on your machine (+17), and have set the JAVA_HOME environment variable properly.

The BAGESS software receives two text filenames as arguments: 

1. First argument: Refers to the scenario inherent to each of the events considered in the instance, indicating the smartphone's models, the smartphone's current battery levels (-1 represents unknown battery level), and the smartphone's target battery levels.
2. Second argument: Refers to the load-balancing strategy inherent to each of the events considered in the instance, indicating the estimated battery level variations that smartphones suffer once the strategy is evaluated on them.

## Invocation example

```bash
java -jar Bagess.jar ScePerEvent_G2_R10_M4_HD_I7.txt StrPerEvent_G2_R10_M4_HD_I7.txt
```

Format examples of each input file can be found in the project's *instances* folder.
