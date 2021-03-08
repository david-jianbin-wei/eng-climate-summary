package com.david.engclimatesummary.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public final class ClimateComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Climate>> map = new HashMap<>();

    static {
        map.put(new Key("Station_Name", Direction.asc), Comparator.comparing(Climate::getStationName));
        map.put(new Key("Station_Name", Direction.desc), Comparator.comparing(Climate::getStationName)
                                                           .reversed());

        map.put(new Key("Date", Direction.asc), Comparator.comparing(Climate::getDate));
        map.put(new Key("Date", Direction.desc), Comparator.comparing(Climate::getDate)
                                                                 .reversed());

        map.put(new Key("Mean_Temp", Direction.asc), Comparator.comparing(Climate::getMeanTemp));
        map.put(new Key("Mean_Temp", Direction.desc), Comparator.comparing(Climate::getMeanTemp)
                                                               .reversed());
    }

    public static Comparator<Climate> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private ClimateComparators() {
    }
}
