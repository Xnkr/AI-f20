package ai.hw1.usa;

import aima.core.environment.map.ExtendableMap;

public class RoadMapOfUSA extends ExtendableMap {

    public RoadMapOfUSA() {
        initMap(this);
    }

    /**
     * Constant city names
     */
    public static final String AUSTIN = "Austin";
    public static final String CHARLOTTE = "Charlotte";
    public static final String SAN_FRANCISCO = "San Francisco";
    public static final String LOS_ANGELES = "Los Angeles";
    public static final String NEW_YORK = "New York";
    public static final String CHICAGO = "Chicago";
    public static final String SEATTLE = "Seattle";
    public static final String SANTA_FE = "Santa Fe";
    public static final String BAKERSVILLE = "Bakersville";
    public static final String BOSTON = "Boston";
    public static final String DALLAS = "Dallas";

    /**
     * Initializes the map with distances with cities and positions
     * Created with reference from SimplifiedRoadMapOfRomania
     * @param map
     */
    public static void initMap(ExtendableMap map) {
        map.clear();

        // Creating the graph
        map.addBidirectionalLink(LOS_ANGELES, SAN_FRANCISCO, 383.0);
        map.addBidirectionalLink(LOS_ANGELES, AUSTIN, 1377.0);
        map.addBidirectionalLink(LOS_ANGELES, BAKERSVILLE, 153.0);
        map.addBidirectionalLink(SAN_FRANCISCO, BAKERSVILLE, 283.0);
        map.addBidirectionalLink(SAN_FRANCISCO, SEATTLE, 807.0);
        map.addBidirectionalLink(SEATTLE, SANTA_FE, 1463.0);
        map.addBidirectionalLink(SEATTLE, CHICAGO, 2064.0);
        map.addBidirectionalLink(BAKERSVILLE, SANTA_FE, 864.0);
        map.addBidirectionalLink(AUSTIN, DALLAS, 195.0);
        map.addBidirectionalLink(SANTA_FE, DALLAS, 640.0);
        map.addBidirectionalLink(BOSTON, AUSTIN, 1963.0);
        map.addBidirectionalLink(DALLAS, NEW_YORK, 1548.0);
        map.addBidirectionalLink(AUSTIN, CHARLOTTE, 1200.0);
        map.addBidirectionalLink(CHARLOTTE, NEW_YORK, 634.0);
        map.addBidirectionalLink(NEW_YORK, BOSTON, 225.0);
        map.addBidirectionalLink(BOSTON, CHICAGO, 983.0);
        map.addBidirectionalLink(CHICAGO, SANTA_FE, 1272.0);
        map.addBidirectionalLink(BOSTON, SAN_FRANCISCO, 3095.0);

        map.setPosition(DALLAS, 0, 0);

        // SLD Heuristic with reference to Dallas as origin
        map.setPosition(AUSTIN, 182.0, 0);
        map.setPosition(CHARLOTTE, 929.0, 0);
        map.setPosition(SAN_FRANCISCO, 1230.0, 0);
        map.setPosition(LOS_ANGELES, 1100.0, 0);
        map.setPosition(NEW_YORK, 1368.0, 0);
        map.setPosition(CHICAGO, 800.0, 0);
        map.setPosition(SEATTLE, 1670.0, 0);
        map.setPosition(SANTA_FE, 560.0, 0);
        map.setPosition(BAKERSVILLE, 1282.0, 0);
        map.setPosition(BOSTON, 1551.0, 0);
    }
}
