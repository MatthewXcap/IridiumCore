package com.iridium.iridiumcore.gui;

import com.iridium.iridiumcore.Background;
import com.iridium.iridiumcore.Item;
import com.iridium.iridiumcore.utils.InventoryUtils;
import com.iridium.iridiumcore.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public abstract class BackGUI implements GUI {
    private final Background background;
    private final String previousCommand;
    private final Inventory previousInventory;
    private final Item backButton;

    public BackGUI(Background background, String previousCommand, Inventory previousInventory, Item backButton) {
        this.background = background;
        this.previousCommand = previousCommand;
        this.previousInventory = previousInventory;
        this.backButton = backButton;
    }

    @Override
    public void addContent(Inventory inventory) {
        InventoryUtils.fillInventory(inventory, background);
        inventory.setItem(inventory.getSize() + backButton.slot, ItemStackUtils.makeItem(backButton));
    }
    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlot() == (event.getInventory().getSize() + backButton.slot)) {
            Player player = (Player) event.getWhoClicked();
            if (previousCommand != null && !previousCommand.isEmpty()) {
                executeCommand(player, previousCommand);
            } else if (previousInventory != null) {
                player.openInventory(previousInventory);
            }
        }
    }

    private void executeCommand(Player player, String command) {
        Bukkit.dispatchCommand(player, command);
    }
}
