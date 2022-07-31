package de.papiertuch.bedwars.utils;

import lombok.Getter;

import java.util.List;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */
@Getter
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
    public TabListGroup(String name, String prefix, String suffix, String display, int tagId) {
        this.name = name;
        this.suffix = suffix;
        this.display = display;
        this.prefix = prefix;
        this.tagId = tagId;
    }
}
