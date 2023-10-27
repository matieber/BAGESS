# BAGESS
Battery Aware Green Edge Scenario Scheduler

Refer to this software as:

BAGESS: A software module based on evolutionary algorithms to schedule load-balancing scenarios over smartphone-based clusters at the Edge
Virginia Yannibelli, Matías Hirsch, Juan Toloza, Tim A. Majchrzak,*, Tor-Morten Grønli, Alejandro Zunino and Cristian Mateos


Running instructions:

Bagess module receives two text filenames as arguments: 

File1: refers to the scenario inherent to each of the events considered in the instance, indicating the smartphone's models, the smartphone's current battery levels (-1 represents unknown battery level), and the smartphone's target battery levels.

File2: refers to the load-balancing strategy inherent to each of the events considered in the instance, indicating the estimated battery level variations that smartphones suffer once the strategy is evaluated on them.


Invocation Example:

java -jar Bagess.jar ScePerEvent_G2_R10_M4_HD_I7.txt StrPerEvent_G2_R10_M4_HD_I7.txt

Format examples of each input file can be found in the project's "instances" folder.
