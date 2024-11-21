package org.TextAdventureGame;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int health;
    private List<Item> inventory;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.inventory = new ArrayList<>();
    }

    public int getHealth() {
        return health;
    }

    public void heal(int amount) {
        this.health += amount;
        if (this.health > 100) {
            this.health = 100; // Max health is 100
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            inventory.forEach(item -> System.out.println(item.getName() + ": " + item.getDescription()));
        }
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    // This method is used for fighting enemies
    public void fightEnemy(Player player, Enemy enemy) {
        System.out.println("Fighting " + enemy.getName() + "...");
        while (enemy.getHealth() > 0 && player.getHealth() > 0) {
            // Player attacks enemy
            enemy.takeDamage(20); // Assume player does 20 damage per attack
            System.out.println("You attacked " + enemy.getName() + " for 20 damage.");
            System.out.println(enemy.getName() + " Health: " + enemy.getHealth());

            // Check if enemy is defeated
            if (enemy.getHealth() <= 0) {
                System.out.println("You have defeated " + enemy.getName() + "!");
                break;
            }

            // Enemy attacks player
            player.takeDamage(15); // Assume enemy does 15 damage per attack
            System.out.println(enemy.getName() + " attacked you for 15 damage.");
            System.out.println("Your Health: " + player.getHealth());

            // Check if player is defeated
            if (player.getHealth() <= 0) {
                System.out.println("You have been defeated! Game over.");
                break;
            }
        }
    }

    // Method to apply damage to the player
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public String getName() {
        return name;
    }
}
