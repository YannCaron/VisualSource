/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 *
 * @author cyann
 */
public class AcceptationType {

    // class attribute
    public static final AcceptationType ALL = new AcceptationType("ALL", (me, other) -> true);

    private static Map<String, AcceptationType> enumSet;
    private static int nextOrdinal = 0;

    // attribute
    private final int ordinal;
    private final String name;
    private final BiPredicate<AcceptationType, AcceptationType> matcher;

    // constructor
    public AcceptationType(String name, BiPredicate<AcceptationType, AcceptationType> matcher) {
        if (enumSet == null) {
            enumSet = new HashMap<>();
        }

        this.name = name;
        this.ordinal = nextOrdinal++;
        this.matcher = matcher;

        if (enumSet.containsKey(name)) {
            throw new RuntimeException(String.format("Acceptation type [%s] cannot be defined twice !", name));
        }
        enumSet.put(name, this);
    }

    public AcceptationType(String name) {
        this(name, (me, other) -> me.ordinal == other.ordinal);
    }

    // accessor
    public String getName() {
        return name;
    }

    // method
    public boolean match(AcceptationType other) {
        if (other == null) {
            return false;
        }
        return matcher.test(this, other);
    }

}
