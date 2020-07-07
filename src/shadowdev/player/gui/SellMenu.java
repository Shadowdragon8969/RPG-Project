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
import shadowdev.server.EconomyManager;
import shadowdev.server.ServerManager;

public class SellMenu extends PlayerGui implements InventoryListener {

	List<ItemCustom> icons = new ArrayList<ItemCustom>();
	List<Integer> amts = new ArrayList<Integer>();
	int start = 0;
	
	public SellMenu(Player p, int start) {
		super (p, ChatColor.GREEN + "Sell Menu", 54);
		setItem(6, Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Swords", "Shows the swords that", "you own and can equip");
		setItem(15, Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", "Shows the armor that", "you own and can equip");
		setItem(24, Material.BLAZE_POWDER, 1, ChatColor.YELLOW + "Stats", "Shows your stats a levels");
		setItem(33, Material.BOOK, 1, ChatColor.YELLOW + "Skills", "Shows the skills that", "you have acquired and", "their attack patterns");
		setItem(51, Material.CHEST, 1, ChatColor.YELLOW + "Inventory", "Shows the items that", "you have collected");
		this.start = start;
		for (int i = 0; i < ServerManager.getPlayer(p).getInventory().size(); i++) {
			ItemCustom item = ServerManager.getPlayer(p).getInventory().get(i);
			if (p.getInventory().contains(CraftingTableManager.getItemFromCustom(item))) continue;
			boolean a = false;
			for (int x = 0; x < p.getInventory().getArmorContents().length; x++) {
				if (CraftingTableManager.isCustomItem(p.getInventory().getArmorContents()[x]) == item) {
					a = true;
					break;
				}
			}
			if (a) continue;
			int amt = 0;
			boolean present = false;
			for (ItemCustom ic : icons) {
				if (ic.getId().equalsIgnoreCase(item.getId())) {
					present = true;
					break;
				}
			}
			if (present) continue;
			for (int x = 0; x < ServerManager.getPlayer(p).getInventory().size(); x++) {
				if (item.getDisplayName().equalsIgnoreCase(ServerManager.getPlayer(p).getInventory().get(x).getDisplayName())) {
					amt++;
				}
			}
			amts.add(amt);
			icons.add(item);
		}
		if (icons.size() < 1) {
			setItem(10, Material.OAK_SIGN, 1, ChatColor.RED + "Couldn't Find any items :/", "");
		}
		setInventoryListener(this);
		for (int i = 5; i < 54; i += 9) {
			setItem(i, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
			setItem(i + 2, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		}
		setItem(50, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		setItem(52, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		if (start > 0) {
			setItem(2, Material.ARROW, 1, ChatColor.GREEN + "Page Up", "");
		}
		if (icons.size() - 12 - start > 0)
		setItem(47, Material.ARROW, 1, ChatColor.GREEN + "Page Down", "");
		///
		int x = 0;
		if (start + x < icons.size() && icons.get(start + x) != null) {
			System.out.println(icons.get(start + x).getDisplayName() + "|||" + amts.get(start + x));
			setItem(10, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(11, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(12, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(19, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(20, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(21, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(28, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(29, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(30, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(37, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(38, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		if (start + x < icons.size() && icons.get(start + x) != null) {
			setItem(39, amts.get(start + x), icons.get(start + x), ChatColor.YELLOW + "Sell Price: " + ChatColor.GREEN.toString() + EconomyManager.getSellPrice(icons.get(start + x)) + ChatColor.GOLD + "コル");
			x++;
		}
		///
	}
	
	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		new MainMenu(p).onClick(p, i, slot, click, inv);
		if (i != null && slot == 47) {
			start += 12;
			SellMenu im = new SellMenu(p, start);
			im.send();
		}
		if (i != null && slot == 2) {
			start -= 12;
			SellMenu im = new SellMenu(p, start);
			im.send();
		}
		ItemCustom c = CraftingTableManager.isCustomItem(i);
		if (c != null) {
			ServerManager.getPlayer(p).getAttribute("cor").addLevels(EconomyManager.getSellPrice(c));
			ServerManager.getPlayer(p).removeItem(c);
			SellMenu m = new SellMenu(p, start);
			m.send();
		}
	}
	
}
