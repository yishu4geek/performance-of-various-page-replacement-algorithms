-code folder contains the following files and folders.

input.txt - Samle input file which has default algorithm as FIFO

source Folder
-------------

The source folder contains all the source code files
1. PageReplacementAlgorithms.java
2. PageReplacementTest.java
3. PageStatistics.java
4. PageTable.java
5. PageTableEntry.java

The run.sh file compiles all the java source files, takes the file input.txt as sample file and produces an output.txt
To compile the source files, go to the source folder and run the following command from the command line

./run.sh

The above command generates the output for FIFO algorithm. If you want to run a different algorithm, change the algorithm name in input.txt (present in this folder) and run the above command again. The allowed values of algorithm name are FIFO, LRU or CLOCK

bin Folder
----------

The bin folder contains the compiled class files of the above 5 java files and sample input.txt file.
If you want to run the program from this folder, run the following command from the command line

java PageReplacementTest input.txt

The above command generates the output for FIFO algorithm. If you want to run a different algorithm, change the algorithm name in input.txt (present in this folder) and run the above command again. The allowed values of algorithm name are FIFO, LRU or CLOCK

output Folder
-------------

This folder contains the output from all the three page replacement algorithms.

1. clock-output.txt - contains the output of clock page replacement algorithm
2. fifo-output.txt - contains the output of fifo page replacement algorithm
3. lru-output.txt - contains the output of lru page replacement algorithm
