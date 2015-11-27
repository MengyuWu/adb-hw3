1) Your name and your partner's name and Columbia UNI
Mengyu Wu: mw2907  Melanie Hsu: mlh2197 

2) A list of all files that you are submitting
build.xml
example-run.txt
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

Here is an example run with min confidence = 50%, and min support = 10%. 
We picked a min support, or cover of 10%, which represents the number of
transactions/instances that satisfy the rule. 

From these results, we can glean the following:
2/3 of the citibikers in June were male ([gender1], 66%), 20% were female 
([gender2], 20%), and the rest are unknown. The most frequent trip duration 
([tripMin0], 45%) is a trip lasting between 0-9 minutes, followed by trip 
durations lasting between 10-19 minutes ([tripMin10], 34%), suggesting at
a very short exercise time. It seems that users tend to use citibike more
for transportation purposes, than for purely exercise purposes, since a
standard workout is more likely to be in the 20-40 minute range.

The most frequent age brackets are people in their 20s and 30s,
[age30], 29% and [age20], 20%, followed closely by people in their 40s
[age40], 18%. There seem to be associations between men taking 
trips of 0-9 minutes in length most frequently, followed by men taking
trips of 10-19 minutes in length. However, there aren't any frequent
itemsets associating age or trip length with women, probably because
there aren't many women represented in this dataset.

We chose a minimum confidence of 50%, where confidence indicates the 
ratio of how many instances satisfy the rule, to the number of 
instances that satisfy the LHS. Among the high-confidence association
rules, there are five rules with a high confidence (let's say that
high confidence = 72% or higher):
     [age40] => [gender1](Conf: 80%, Supp: 15%)
     [age30,tripMin0] => [gender1](Conf: 79%, Supp: 11%)
     [tripMin0] => [gender1](Conf: 76%, Supp: 35%)
     [age30] => [gender1](Conf: 76%, Supp: 22%)
     [age20] => [gender1](Conf: 72%, Supp: 14%)
     
These rules say:
80% of the time, if the citibike user is in their 40s, they are male
79% of the time, if the citibike user is in their 30s, and the trip duration
is between 0-9 minutes, the user is male
76% of the time, if the trip duration is between 0-9 minutes, then the user is male
76% of the time, if the citibike user is in their 30s, they are male
72% of the time, if the citibike user is in their 20s, they are male

These rules are unsurprising, since the majority of citibike users are male, 
and the proportion of female and unknown gender users are quite low, so no 
frequent itemsets could be generated involving female users. Even if the dataset
were expanded to accommodate more months, the proportion of female users would
probably be similar. Thus, we can say that the most frequent users of the 
citibike, at least in the month of June, are men in their 20s-40s. 

These results are consistent with previous studies and articles noting a huge
gender gap in citibike users: 
http://nymag.com/scienceofus/2015/07/why-arent-more-women-riding-citi-bikes.html
http://nypost.com/2014/05/09/majority-of-citi-bike-users-are-men-study/

These articles cite safety and lack of family-friendliness (no seats for children)
as major reasons why women are less inclined to bike in NYC. If the datasets
were expanded to take time of day into account, the gender disparity would probably
be even more marked during certain times of the day. This suggests that citibike
is not a popular exercise and/or transportation option among people of all age ranges
and demographics, and that in order to cater to women and families, they would
have to make some significant changes, such as adding additional seats for children
and taking more measures to increase satefy for cyclists in the city.

mlh2197@athens:~/adb-hw3$ ant AprioriLargeSets -Dargs='201506-citibike-tripdata.csv 0.1 0.50'
Buildfile: /home/mlh2197/adb-hw3/build.xml

AprioriLargeSets:
     [java] age50  count:117335
     [java] age40  count:177630
     [java] tripMin0  count:432141
     [java] tripMin20  count:122137
     [java] age20  count:193734
     [java] tripMin10  count:320718
     [java] gender2  count:188655
     [java] gender1  count:621591
     [java] age30  count:275306
     [java] In level:2
     [java] age20 gender1 count140117
     [java] age30 tripMin10 count94174
     [java] age30 tripMin0 count140358
     [java] gender1 tripMin0 count329498
     [java] age20 tripMin0 count99242
     [java] age40 gender1 count143326
     [java] gender1 tripMin10 count205935
     [java] age30 gender1 count209793
     [java] In level:3
     [java] age30 gender1 tripMin0 count111932
     [java] In level:4
     [java] ==Frequent Itemsets (min_sup=10%)
     [java] [gender1], 66%
     [java] [tripMin0], 45%
     [java] [gender1,tripMin0], 35%
     [java] [tripMin10], 34%
     [java] [age30], 29%
     [java] [age30,gender1], 22%
     [java] [gender1,tripMin10], 21%
     [java] [age20], 20%
     [java] [gender2], 20%
     [java] [age40], 18%
     [java] [age40,gender1], 15%
     [java] [age30,tripMin0], 14%
     [java] [age20,gender1], 14%
     [java] [tripMin20], 12%
     [java] [age50], 12%
     [java] [age30,gender1,tripMin0], 11%
     [java] [age20,tripMin0], 10%
     [java] [age30,tripMin10], 10%
     [java] ==High-confidence association rules (min_conf=50%)
     [java] [age40] => [gender1](Conf: 80%, Supp: 15%)
     [java] [age30,tripMin0] => [gender1](Conf: 79%, Supp: 11%)
     [java] [tripMin0] => [gender1](Conf: 76%, Supp: 35%)
     [java] [age30] => [gender1](Conf: 76%, Supp: 22%)
     [java] [age20] => [gender1](Conf: 72%, Supp: 14%)
     [java] [tripMin10] => [gender1](Conf: 64%, Supp: 21%)
     [java] [age30,gender1] => [tripMin0](Conf: 53%, Supp: 11%)
     [java] [gender1] => [tripMin0](Conf: 53%, Supp: 35%)
     [java] [age20] => [tripMin0](Conf: 51%, Supp: 10%)
     [java] [age30] => [tripMin0](Conf: 50%, Supp: 14%)

7) Any additional information that you consider significant
