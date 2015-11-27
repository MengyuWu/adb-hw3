1) Your name and your partner's name and Columbia UNI
Mengyu Wu: mw2907  Melanie Hsu: mlh2197 

2) A list of all files that you are submitting
build.xml
201506-citibike-tripdata.csv

src:
AprioriLargeSets.java
FileProcess.java
Transaction.java

src/items:
AgeItem.java
GenderItem.java
Item.java
ItemSet.java
Rule.java
TripDurationItem.java
UserTypeItem.java

3) A detailed description explaining: (a) which NYC Open Data data set(s) you
used to generate the INTEGRATED-DATASET file; (b) what (high-level) procedure
you used to map the original NYC Open Data data set(s) into your
INTEGRATED-DATASET file; (c) what makes your choice of INTEGRATED-DATASET file
interesting (in other words, justify your choice of NYC Open Data data
set(s)). The explanation should be detailed enough to allow us to recreate
your INTEGRATED-DATASET file exactly from scratch from the NYC Open Data site.

a) We chose the citibike system data, found here:
https://data.cityofnewyork.us/NYC-BigApps/Citi-Bike-System-Data/vsnr-94wk
http://www.citibikenyc.com/system-data

The data includes the following:
    Trip Duration (seconds)
    Start Time and Date
    Stop Time and Date
    Start Station Name
    End Station Name
    Station ID
    Station Lat/Long
    Bike ID
    User Type (Customer = 24-hour pass or 7-day pass user; Subscriber = Annual
Member)
    Gender (Zero=unknown; 1=male; 2=female)
    Year of Birth

b) We ended up using a single .csv file, as attempting to upload or unzip more
than one of these files to .csv has consistently resulted in connection and
write failures. We chose June as it is one of the hottest months in the year,
where people are more likely to exercise. This is supported by the fact that
the June dataset in size than all of the winter and spring datasets, with the
exception of May. We did not use July, August, or September because the data
sizes were so large, they produced an overhead limit exceeded error.

Go to http://www.citibikenyc.com/system-data and download the following file:
201506-citibike-tripdata.csv


c) Our dataset is interesting, because it allows us to get a sense of the exercise
habits of a large group of New Yorkers and obtain information about how people's
exercise patterns (duration, frequency) differ across gender and age. The results
of this investigation could be used to determine how to increase participation
in the citibike program for target audiences, such as college students or women,
and allow us to understand which demographics may be in danger of underexercising
(provided that they do not participate in additional exercise programs such as gyms,
or partake in any other form of exercise outside of biking).

4) A clear description of how to run your program (note that your project must
compile/run under Linux in your CS account) 

    1) ant clean 
    2) ant
    If you receive something similar to the following error:
        [javac] javac: invalid target release: 1.7
        Please do the following:
        1) In Build.xml, update the following two lines to reflect the
        version of Java that you are using:
            <property name="target" value="1.6"/>
                <property name="source" value="1.6"/>
         2) In Build.xml, if fork="yes" in the line below, change fork="no"
            <java classname="main.bingRun" failonerror="true" fork="no">
    3) ant AprioriLargeSets -Dargs='<file_name.csv> <min_sup> <min_conf>'
	ex. ant AprioriLargeSets -Dargs='201509-citibike-tripdata.csv 0.1 0.6'

5) A clear description of the internal design of your project; in particular,
if you decided to implement variation(s) of the original a-priori algorithm
(see above), you must explain precisely what variation(s) you have implemented
and why

AprioriLargeSets class's main method does command-line argument checking, 
prepares the .csv input file for the Apriori Algorithm by having the 
FileProcess class parse the file. 

The FileProcess readFile method parses the .csv file according to the
columns in the .csv file's schema, creates a new Transaction for each 
record in the file, and adds that transaction to the TransactionsSet
HashSet. When the new Transaction is created, it's given a unique 
transaction ID (tid) and is added to the itemSetMap, which stores the 
set of single items found in the .csv file, along with information on
their frequencies (the number of times that item occurs in the dataset).
These frequencies are later used in the Apriori Algorithm to determine
the frequent itemsets in the file.

Item.java and ItemSet.java represent the concept of an item and itemset
in our project, and AgeItem.java, GenderItem.java, TripDurationItem.java, 
and UserTypeItem.java represent the concepts of the important aspects in 
the dataset's schema.

After the file is parsed, the AprioriLargeSets class creates a set of 
large 1-itemsets (firstItemSet), which contains all of the items that 
exceed the minimum support threshold specified by the user. The count of
each item was pre-computed during the file parsing process earlier.
In preparation for the next iteration of the algorithm, the items that 
pass the support threshold are added to largeSets.

The createLargeItemSetK runs the rest of the Apriori Algorithm (from k=2
onwards), terminating when L_k is the empty set (we cannot generate any
item sets of size k). First, the algorithm starts with an empty L_k, aka.
Lnext, and retrieves the set of all large k-1 itemsets. It then calls the
aprioriGen method, which generates all sets of large k-itemsets by adding
one item to the frequent subsets at a time. 

The algorithm then takes a count of the frequency of each item set of size
k, adding it to the set of k-itemsets if the item set passes the minimum
support threshold. If there is at least one item set in the set of k-itemsets,
then the algorithm progresses to the next iteration, incrementing k by 1.

Using the results of this algorithm, the AprioriLargeSets class generates all
possible rules that contain one item on the RHS, and at least one item on the
LHS, outputting only the rules that are greater than or equal to the minimum
confidence - this is the set of high-confidence rules. 

Finally, the FileProcess class outputs these rules to an output.txt file, 
which includes a list of frequent itemsets (one itemset plus its support)
and a list of high-confidence association rules, inculding its support and
confidence.   

We did not implement any variations of the original a-priori algorithm.

6) The command line specification of an interesting sample run (i.e., a
min_sup, min_conf combination that produces interesting results). Briefly
explain why the results are interesting.

7) Any additional information that you consider significant
