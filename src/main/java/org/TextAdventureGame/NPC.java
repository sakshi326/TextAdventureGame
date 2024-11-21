package org.TextAdventureGame;

public class NPC {

    private String name;
    private String dialogue;

    public NPC(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
    }

    public String getName() {
        return name;
    }

    public String talk() {
        return dialogue;
    }
}
