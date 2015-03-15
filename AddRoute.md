## For drawing the route: ##
  1. go to google maps and create a map of the route, try to do as little overlap as possible, it's ok to make multiple sections
  1. make sure it's saved then get the link to the map (button on top right of the map view) and paste it in an internet browser and add "&output=kml" to the link. You will receive a kml (basically xml) file with gps coordinates that describe the route.
  1. All you want are the list of coordinates, I just copied them into a new file then do a "find and replace all" to add the "{" at the beginning of a pair and "}," to the end. Copy it into the busRouteOverlay.java and make a new 2d array similar to other routes
  1. make changes to the loops in the functions as necessary. Also add new cases to any switches for the new route.
In citybusMap.java, change the loops' conditions as needed, don't forget to add new cases to switches in other methods.

## Modifying data for the route: ##
  1. DBConstants.java
> > add stops (also add to the string at the same index, and also gps location). then routes (order matters DataManager has route id's so use those ids as the index in DBConstants 2d array).
  1. RuleUnits, create a new class for each loop(for each unique table) (extends RuleUnit) Last line should be initFields
> > stopGaps - is gap between columns (find a full row)
> > emptyBlocks - is inclusive
> > busgap - holds patterns for a column
> > busgapcutoffs - specifies the groups of patterns (after which time it starts)
> > !TIME is our own defined class, not java's time"
> > starttime - top left item
> > stoptime -BOTTOM LEFT item
> > speacial times - only for the first row (always), if not needed, don't initialize, leave null
> > if last row has some empty items, use "empptyBlocks"
> > in general, if we don't need anything don't initalize, let it be null, code takes care of it.
  1. RuleParser.java
> > add a switch case (potentially for each day)