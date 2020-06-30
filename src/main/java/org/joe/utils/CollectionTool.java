package org.joe.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionTool {

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <T> Collection<T> mergeList(Collection<T> list1, Collection<T> list2) {
        if (isNullOrEmpty(list1) && isNullOrEmpty(list2)) {
            return new ArrayList<>();
        } else if (isNullOrEmpty(list1)) {
            return list2;
        } else if (isNullOrEmpty(list2)) {
            return list1;
        } else {
            return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
        }
    }
}
