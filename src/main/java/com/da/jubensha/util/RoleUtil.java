package com.da.jubensha.util;

import java.util.HashMap;
import java.util.Map;

public class RoleUtil {

    public static final Map<Integer, Integer> ignoreMap = new HashMap<>();

    public static final Map<Integer, Integer> sameMap = new HashMap<>();

    static {
        ignoreMap.put(5,11);
        ignoreMap.put(6,6);
        ignoreMap.put(7,8);
        ignoreMap.put(8,8);
        ignoreMap.put(9,10);
        ignoreMap.put(4,9);
        sameMap.put(6,15);
        sameMap.put(8,16);
        sameMap.put(9,17);
        sameMap.put(10,18);
        sameMap.put(11,19);
    }

    public static boolean ignore(int roleId, int placeId){
        if(roleId == 0){
            return false;
        }
        int ignoreId1 = ignoreMap.get(roleId);
        int ignoreId2 = sameMap.get(ignoreId1);
        return ignoreId1 == placeId || ignoreId2 == placeId;
    }
}
