package org.joe.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DaoFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Integer, Object> dataMap = new HashMap<>();

    public void put(int value) {
        int maxIndex = getMaxIndex();
        dataMap.put(maxIndex + 1, value);
    }

    public void put(String value) {
        int maxIndex = getMaxIndex();
        dataMap.put(maxIndex + 1, value);
    }

    public void put(Date value) {
        int maxIndex = getMaxIndex();
        dataMap.put(maxIndex + 1, value);
    }

    public void put(double value) {
        int maxIndex = getMaxIndex();
        dataMap.put(maxIndex + 1, value);
    }

    private int getMaxIndex() {
        Optional<Integer> optional = dataMap.keySet().stream().sorted(Comparator.reverseOrder()).findFirst();
        if (!optional.isPresent()) {
            return -1;
        }
        return optional.get();
    }

    public Map<Integer, Object> getDataMap() {
        return dataMap;
    }

    @Override
    public String toString() {
        return "DaoFile" + dataMap;
    }

}
