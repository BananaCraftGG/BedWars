package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;

public class ConfigInput {

    private String path;
    private Object value;

    public ConfigInput(String path, Object value) {
        this.path = path;
        this.value = value;
        BedWars.getInstance().getBedWarsConfig().getSortedList().add(this);
    }

    public Object getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }
}

