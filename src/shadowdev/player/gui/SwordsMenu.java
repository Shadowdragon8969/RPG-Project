package shadowdev.player.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;
import shadowdev.server.ServerManager;

public class SwordsMenu extends PlayerGui implements InventoryListener {

	List<ItemCustom> icons = new ArrayList<ItemCustom>();
	int start = 0;
	
	public SwordsMenu(Player p, int start) {
		super (p, ChatColor.GREEN + "Swords", 54);
		setItem(6, Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Swords", "Shows the swords that", "you own and can equip");
		setItem(15, Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", "Shows the armor that", "you own and can equip");
		setItem(24, Material.BLAZE_POWDER, 1, ChatColor.YELLOW + "Stats", "Shows your stats and levels");
		setItem(33, Material.BOOK, 1, ChatColor.YELLOW + "Skills", "Shows the skills that", "you have acquired and", "their attack patterns");
		setItem(42, Material.EMERALD, 1, ChatColor.YELLOW + "Potions and Crystals", "Shows your potions and crystals.");
		setItem(51, Material.CHEST, 1, ChatColor.YELLOW + "Inventory", "Shows the items that", "you have collected");
		this.start = start;
		for (int i = 0; i < ServerManager.getPlayer(p).getInventory().size(); i++) {
			ItemCustom item = ServerManager.getPlayer(p).getInventory().get(i);
			if (!(item instanceof ToolX)) continue;
			ItemCustom hand = CraftingTableManager.isCustomItem(p.getInventory().getItem(2));
			if (hand != null && item.getId().equalsIgnoreCase(hand.getId())) continue;
			icons.add(item);
		}
		for (int i = 5; i < 54; i += 9) {
			setItem(i, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
			setItem(i + 2, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		}
		setItem(5, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		setItem(7, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		setInventoryListener(this);
		setItem(45, Material.BARRIER, 1, ChatColor.RED + "Unequip Sword", "Click to put away your sword");
		if (icons.size() < 1) {
			setItem(10, Material.OAK_SIGN, 1, ChatColor.RED + "Couldn't Find any swords to equip :/", "");
		}
		if (start > 0) {
			setItem(2, Material.ARROW, 1, ChatColor.GREEN + "Page Up", "");
		}
		if (icons.size() - 12 - start > 0)
		setItem(47, Material.ARROW, 1, ChatColor.GREEN + "Page Down", "");
		///
		int x = 0;
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(10, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(11, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(12, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(19, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(20, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(21, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(28, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(29, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(30, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(37, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(38, icons.get(start + x));
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(39, icons.get(start + x));
			x++;
		}
		///
	}
	
	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		new MainMenu(p).onClick(p, i, slot, click, inv);
		if (i != null && slot == 47) {
			start += 12;
			SwordsMenu im = new SwordsMenu(p, start);
			im.send();
		}
		if (i != null && slot == 2) {
			start -= 12;
			SwordsMenu im = new SwordsMenu(p, start);
			im.send();
		}
		if (slot == 45) {
			p.getInventory().setItem(2, null);
			p.sendMessage(ChatColor.GREEN + "Unequipped Sword");
			SwordsMenu im = new SwordsMenu(p, start);
			im.send();
		}
		ItemCustom c = CraftingTableManager.isCustomItem(i);
		if (c != null) {
			p.getInventory().setItem(2, i);
			p.sendMessage(ChatColor.GREEN + "You Equipped " + c.getDisplayName());
			SwordsMenu im = new SwordsMenu(p, start);
			im.send();
		}
	}
	
}
