package de.papiertuch.bedwars.utils;

import java.util.List;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class TabListGroup {

    private String prefix;
    private String suffix;
    private String name;
    private String display;
    private String permission;
    private int tagId;

    public TabListGroup(String name, String prefix, String suffix, String display, int tagId, String permission) {
        this.name = name;
        this.suffix = suffix;
        this.display = display;
        this.prefix = prefix;
        this.tagId = tagId;
        this.permission = permission;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPermission() {
        return permission;
    }

    public String getSuffix() {
        return suffix;
    }

    public int getTagId() {
        return tagId;
    }

    public String getDisplay() {
        return display;
    }

    public String getName() {
        return name;
    }
}
