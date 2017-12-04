/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.algoid.visualsource.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cyann
 */
public class AcceptationType {

    private static int counter = 0;
    private static Map<String, AcceptationType> flyWeight = new HashMap<>();

    public static final AcceptationType ALL = AcceptationType.register(new AcceptationType("ALL") {
        @Override
        public boolean match(AcceptationType other) {
            return true;
        }
    });

    private final int id;
    private final String name;

    private AcceptationType(String name) {
        this.name = name;
        this.id = counter++;
    }

    public static AcceptationType getInstance(String name) {
        if (!flyWeight.containsKey(name)) {
            flyWeight.put(name, new AcceptationType(name));
        }
        return flyWeight.get(name);
    }

    public static AcceptationType register(AcceptationType instance) {
        if (flyWeight.containsKey(instance.getName())) {
            throw new RuntimeException("Acceptation type cannot be defined twice !");
        }
        return flyWeight.put(instance.getName(), instance);
    }

    public String getName() {
        return name;
    }

    public boolean match(AcceptationType other) {
        if (other == null) {
            return false;
        }
        return id == other.id;
    }

}
