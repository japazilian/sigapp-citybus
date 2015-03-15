# Introduction #

DataManager class is the central point and the only point for bus information querying.

DataManager currently supports geo/time information query for loops.

The following list explains the different usage of querys:

## DataManager.getRoutineInfoById(Context ctx, int routineId, Time currentTime) ##
  * This method computes:
    * all bus stops for a specific bus routine (Currently support all loops),
    * and next arrival time for each bus stop after a specified time.
  * This method call returns an ArrayList< BusStopGeoTimeInfo >, which includes geological information of bus stops and bus arrival time for bus routine specified by user.
  * Parameter:
    * ctx: A context, required by Database operations. Any class extending Activity is considered a valid context
    * routineId: The bus routine's Id
      * routineId definition can be found in DataManager class
        *  GOLD\_LOOP = 0;
        *  SILVER\_LOOP = 1;
        *  BLACK\_LOOP = 2;
        *  TOWER\_ACRES = 3;
        *  BRONZE\_LOOP = 4;
        *  ROSS\_ADE = 5;
        *  NIGHT\_RIDER = 6;
        *  SOUTH\_CAMPUS\_AV\_TECH = 7;
      * currentTime: A time user specifies. All bus arrival will happen after this time point.
  * Example:
    * ArrayList< BusStopGeoTimeInfo > info = DataManager.getRoutineInfoById(                this, DataManager.TOWER\_ACRES, new Time(Calendar.FRIDAY, 0, 0));
> > > for (BusStopGeoTimeInfo i : info) {
> > > > Log.d("citybus", "time: " + i.timeInfo.toString() + ", name: "
> > > > > + i.geoInfo.name);

> > > }
    * All bus stop geo information and next arrival time after mid-night for Tower Acres will be stored in info ArrayList.

## DataManager.getNextComingBusInfoByLocation(Context ctx, int busStopId, Time currentTime, int nextComings) ##
  * This method is designed to answer the following question: What bus is passing this bus stop? When is it coming?
  * The function can query the above question and return N next coming buses ordered by arrival time. N can be specified in the parameter
  * This function returns an array of BusNextComingInfo structure, in which contains bus id, bus stop geological information and arrival time information.
  * Parameter Usage:
    * ctx: A context object. Required by Android system.
    * busStopId: Bus Stop ID: The id should come from DBConstants class.
    * currentTime: The inital desired arrival time. All incoming buses arrive after this time point.
    * nextComings: How many consecutive incoming buses is needed.
      * 1 will give you the most immediate incoming bus information for each routine.
      * 2 will give you the most immediate 2 incoming buses info for each routine.
      * etc.
  * Example:
    * Call:
> > > ArrayList< BusNextComingInfo > info2 = DataManager                .getNextComingBusInfoByLocation(this, DBConstants.THIRD\_UNIV,
> > > > new Time(Calendar.MONDAY, 8, 0), 4);

> > > for (BusNextComingInfo i : info2) {
> > > > Log.d("citybus", "bus ID=" + i.routeId + ",time="
> > > > > + i.geoTimeInfo.timeInfo.toString());

> > > }
    * Result:
      * D/citybus (  309): bus ID=0,time=day: MONDAY,8:10
      * D/citybus (  309): bus ID=0,time=day: MONDAY,8:15
      * D/citybus (  309): bus ID=0,time=day: MONDAY,8:20
      * D/citybus (  309): bus ID=0,time=day: MONDAY,8:40
      * D/citybus (  309): bus ID=3,time=day: MONDAY,8:0
      * D/citybus (  309): bus ID=3,time=day: MONDAY,8:15
      * D/citybus (  309): bus ID=3,time=day: MONDAY,8:20
      * D/citybus (  309): bus ID=3,time=day: MONDAY,8:30
      * D/citybus (  309): bus ID=6,time=day: MONDAY,18:50
      * D/citybus (  309): bus ID=6,time=day: MONDAY,19:30
      * D/citybus (  309): bus ID=6,time=day: MONDAY,20:10
      * D/citybus (  309): bus ID=6,time=day: MONDAY,20:50
      * D/citybus (  309): bus ID=7,time=day: MONDAY,8:4
      * D/citybus (  309): bus ID=7,time=day: MONDAY,8:24
      * D/citybus (  309): bus ID=7,time=day: MONDAY,8:44
      * D/citybus (  309): bus ID=7,time=day: MONDAY,9:4

## DataManager.getAllTimeForBus(Context ctx,int routineId, int stopId, int dayOfWeek) ##
  * This method makes query to get a full scheudle list for a bus at a specific bus stop on a specific day.
  * Returns ArrayList< BusNextComingInfo >
  * Usage Example:
    * ArrayList< BusNextComingInfo > info =
> > > DataManager.getAllTimeForBus(this,
> > > DataManager.GOLD\_LOOP, DBConstants.THIRD\_UNIV, Calendar.MONDAY);
    * This will return all times for gold loop on third & university stop on Monday.