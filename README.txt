1) Your name and your partner's name and Columbia UNI
Mengyu Wu: mw2907  Melanie Hsu: mlh2197 

2) A list of all files that you are submitting
build.xml
citibike.csv

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

b) We used a year's worth of citibike data, ranging from November 2014 to October 2015
(because the November and December 2015 datasets are not yet published, we used the 
Nov/Dec ones from the year before). 

Go to http://www.citibikenyc.com/system-data and download the following files:
201411-citibike-tripdata.csv
201412-citibike-tripdata.csv
201501-citibike-tripdata.csv
201502-citibike-tripdata.csv
201503-citibike-tripdata.csv
201504-citibike-tripdata.csv
201505-citibike-tripdata.csv
201506-citibike-tripdata.csv
201507-citibike-tripdata.csv
201508-citibike-tripdata.csv
201509-citibike-tripdata.csv
201510-citibike-tripdata.csv

Place the above .csv files into the same directory, then ran the following command:  
    cat *.csv > citibike.csv

This resulted in all of the data being consolidated into the citibike.csv file, 
which is our INTEGRATED-DATASET file.

These files are way too large to store on GitHub, so we included a link to the 
citibike.csv file via Google Drive:
https://drive.google.com/a/columbia.edu/file/d/0B-F5_I2j_YyeamtPZ3ZtcTZmWjQ/view?usp=sharing

We will also include this file in our submission, in case the Google Drive link does not work.

c) Our dataset is interesting, because it allows us to get a sense of the exercise habits
of a large group of New Yorkers and obtain information about how people's exercise
patterns (duration, frequency) differ across several key attributes: time of year 
(particular month, season), gender, and age. The results of this investigation could 
be used to determine how to increase participation in the citibike program for target
audiences, such as college students or women, and allow us to understand which demographics
may be in danger of underexercising (provided that they do not participate in additional 
exercise programs such as gyms, or partake in any other form of exercise outside of biking).

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
	ex. ant AprioriLargeSets -Dargs='citibike.csv 0.1 0.6'

5) A clear description of the internal design of your project; in particular,
if you decided to implement variation(s) of the original a-priori algorithm
(see above), you must explain precisely what variation(s) you have implemented
and why

We did not implement any variations of the original a-priori algorithm.

6) The command line specification of an interesting sample run (i.e., a
min_sup, min_conf combination that produces interesting results). Briefly
explain why the results are interesting.

7) Any additional information that you consider significant

