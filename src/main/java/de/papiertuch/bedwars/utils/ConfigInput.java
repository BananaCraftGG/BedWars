package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import lombok.Getter;

@Getter
public class ConfigInput {

    private String path;
    private Object value;

    public ConfigInput(String path, Object value) {
        this.path = path;
        this.value = value;
        BedWars.getInstance().getBedWarsConfig().getSortedList().add(this);
    }
}

