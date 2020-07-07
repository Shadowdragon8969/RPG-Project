package shadowdev.player.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InventoryListener {
	
	void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv);
	
}
