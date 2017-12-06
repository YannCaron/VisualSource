/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.corenew;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 *
 * @author cyann
 */
public class HoldType {

    // class attribute
    public static final HoldType ALL = new HoldType("ALL", (me, other) -> true);

    private static Map<String, HoldType> flyweight;
    private static int nextOrdinal = 0;

    // attribute
    private final int ordinal;
    private final String name;
    private final BiPredicate<HoldType, HoldType> matcher;

    // constructor
    public HoldType(String name, BiPredicate<HoldType, HoldType> matcher) {
        if (flyweight == null) {
            flyweight = new HashMap<>();
        }

        this.name = name;
        this.ordinal = nextOrdinal++;
        this.matcher = matcher;

        if (flyweight.containsKey(name)) {
            throw new RuntimeException(String.format("Acceptation type [%s] cannot be defined twice !", name));
        }
        flyweight.put(name, this);
    }

    public HoldType(String name) {
        this(name, (me, other) -> me.ordinal == other.ordinal);
    }

    // accessor
    public String getName() {
        return name;
    }

    // method
    public boolean accept(HoldType other) {
        if (other == null) {
            return false;
        }
        return matcher.test(this, other);
    }

    // equals and hash
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.ordinal;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HoldType other = (HoldType) obj;
        if (this.ordinal != other.ordinal) {
            return false;
        }
        return true;
    }

}
