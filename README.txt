1) Your name and your partner's name and Columbia UNI
Mengyu Wu: mw2907  Melanie Hsu: mlh2197 

2) A list of all files that you are submitting
build.xml
201501-citibike-tripdata.csv

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

b) We used the data for January 2015. 

c)

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
    3) ant AprioriLargeSets 201501-citibike-tripdata.csv

5) A clear description of the internal design of your project; in particular,
if you decided to implement variation(s) of the original a-priori algorithm
(see above), you must explain precisely what variation(s) you have implemented
and why

6) The command line specification of an interesting sample run (i.e., a
min_sup, min_conf combination that produces interesting results). Briefly
explain why the results are interesting.

7) Any additional information that you consider significant

