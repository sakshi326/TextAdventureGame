package org.TextAdventureGame;

import java.util.Scanner;

public class Game {

    private Room currentRoom;
    private Player player;

    public Game() {
        // Initialize the player with name and health
        this.player = new Player("Hero", 100);
    }

    // Setup game by creating rooms, NPCs, items, and enemies
    public void setupGame() {
        // Create Rooms
        Room entrance = new Room("Entrance", "You are at the entrance of a mysterious place.");
        Room forest = new Room("Forest", "A dense forest with rustling leaves.");
        Room dungeon = new Room("Dungeon", "A dark and damp dungeon.");
        Room treasureRoom = new Room("Treasure Room", "The room glitters with treasure!");

        // Set Exits (Connections between rooms)
        entrance.setExits(null, forest, null, null);
        forest.setExits(entrance, dungeon, null, null);
        dungeon.setExits(forest, treasureRoom, null, null);
        treasureRoom.setExits(dungeon, null, null, null);

        // Add Items
        forest.addItem(new Item("Potion", "A healing potion."));
        dungeon.addItem(new Item("Sword", "A sharp blade for combat."));
        treasureRoom.addItem(new Item("Key", "A golden key to unlock the treasure chest."));

        // Add NPCs
        forest.addNPC(new NPC("Old Man", "Beware of the dangers in the dungeon!"));

        // Add Enemy
        dungeon.setEnemy(new Enemy("Goblin", 30, 10)); // An enemy in the dungeon

        // Set the initial room
        this.currentRoom = entrance;
    }

    // Display available commands to the player
    private void showCommands() {
        System.out.println("Commands: ");
        System.out.println("- go [direction] : Move in a direction (north, south, east, west).");
        System.out.println("- check inventory : View your collected items.");
        System.out.println("- look around : Check for items and NPCs in the current room.");
        System.out.println("- talk to [NPC name] : Talk to an NPC in the room.");
        System.out.println("- take [item name] : Pick up an item.");
        System.out.println("- use [item name] : Use an item in your inventory.");
        System.out.println("- quit : Exit the game.");
    }

    // Handle player commands during the game loop
    private void handlePlayerCommand(String command) {
        if (command.startsWith("go ")) {
            String direction = command.substring(3);
            Room nextRoom = currentRoom.getExit(direction);
            if (nextRoom != null) {
                currentRoom = nextRoom;
                System.out.println("\nYou move to the " + currentRoom.getName());
                // Check for enemies in the new room
                if (currentRoom.getEnemy() != null) {
                    System.out.println("A wild " + currentRoom.getEnemy().getName() + " appears!");
                    player.fightEnemy(player, currentRoom.getEnemy());
                }
            } else {
                System.out.println("You can't go that way!");
            }
        } else if (command.equals("check inventory")) {
            player.displayInventory();
        } else if (command.equals("look around")) {
            currentRoom.getItems().forEach(item -> System.out.println("You see a " + item.getName()));
            if (currentRoom.getNPC() != null) {
                System.out.println("You see " + currentRoom.getNPC().getName());
            }
        } else if (command.startsWith("talk to ")) {
            String npcName = command.substring(8);
            NPC npc = currentRoom.getNPC();
            if (npc != null && npc.getName().equalsIgnoreCase(npcName)) {
                System.out.println(npc.talk());
            } else {
                System.out.println("There's no one by that name here.");
            }
        } else if (command.startsWith("take ")) {
            String itemName = command.substring(5);
            Item itemToTake = currentRoom.getItems().stream()
                    .filter(item -> item.getName().equalsIgnoreCase(itemName))
                    .findFirst()
                    .orElse(null);
            if (itemToTake != null) {
                player.addItem(itemToTake);
                currentRoom.getItems().remove(itemToTake);
                System.out.println("You picked up the " + itemToTake.getName());
            } else {
                System.out.println("There's no such item here.");
            }
        } else if (command.startsWith("use ")) {
            String itemName = command.substring(4);
            if (player.hasItem(itemName)) {
                if (itemName.equalsIgnoreCase("Potion")) {
                    player.heal(20);
                    System.out.println("You used a Potion and healed 20 health points!");
                } else {
                    System.out.println("You can't use that item here.");
                }
            } else {
                System.out.println("You don't have that item.");
            }
        } else if (command.equals("quit")) {
            System.out.println("Thanks for playing!");
        } else {
            System.out.println("Invalid command!");
        }

        // Check win condition: If player has the key and is in the treasure room
        if (player.hasItem("Key") && currentRoom.getName().equals("Treasure Room")) {
            System.out.println("You have unlocked the treasure chest and collected the treasure!");
            System.out.println("You Win!");
            System.exit(0); // End the game
        }
    }

    // Main game loop
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Adventure Game!");
        showCommands(); // Display available commands at the start

        while (true) {
            // Show current room description
            System.out.println("\n" + currentRoom.getName());
            System.out.println(currentRoom.getDescription());
            System.out.println("Your health: " + player.getHealth());

            // Display available exits
            System.out.println("Exits: ");
            if (currentRoom.getExit("north") != null) System.out.println("- North");
            if (currentRoom.getExit("south") != null) System.out.println("- South");
            if (currentRoom.getExit("east") != null) System.out.println("- East");
            if (currentRoom.getExit("west") != null) System.out.println("- West");

            // Get player command
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            // Handle player command
            if (command.equals("quit")) {
                break; // End the game
            } else {
                handlePlayerCommand(command);
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setupGame();
        game.play();
    }
}
