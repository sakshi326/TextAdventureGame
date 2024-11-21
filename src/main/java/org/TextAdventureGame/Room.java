package org.TextAdventureGame;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private String description;
    private List<Item> items;
    private NPC npc;
    private Enemy enemy;
    private Room northExit, southExit, eastExit, westExit;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
    }

    // Set exits for the room
    public void setExits(Room north, Room south, Room east, Room west) {
        this.northExit = north;
        this.southExit = south;
        this.eastExit = east;
        this.westExit = west;
    }

    // Get specific exit
    public Room getExit(String direction) {
        switch (direction.toLowerCase()) {
            case "north": return northExit;
            case "south": return southExit;
            case "east": return eastExit;
            case "west": return westExit;
            default: return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public NPC getNPC() {
        return npc;
    }

    public void addNPC(NPC npc) {
        this.npc = npc;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
