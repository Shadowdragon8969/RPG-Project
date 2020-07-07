package shadowdev.player.gui;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.shadow.api.CraftingTableManager;
import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import net.md_5.bungee.api.ChatColor;
import shadowdev.player.GamePlayer;
import shadowdev.server.ServerManager;

public class ShopEditor extends PlayerGui implements InventoryListener {
	
	HashMap<Integer, Integer> buyPrices = new HashMap<Integer, Integer>();
	
	public ShopEditor(Player p, List<ObjectSet<ItemCustom, Integer>> content) {
		super(p, ChatColor.YELLOW + "Item Shop", 27);
		int b = content.size() - 1;
		setItem(10, 1, content.get(ServerManager.getSeed(b)).key, ChatColor.YELLOW + "Price: " + ChatColor.GREEN.toString() + content.get(ServerManager.getSeed(b)).value + ChatColor.GOLD + "コル");
		buyPrices.put(10, content.get(ServerManager.getSeed(b)).value);
		b--;
		setItem(13, 1, content.get(ServerManager.getSeed(b)).key, ChatColor.YELLOW + "Price: " + ChatColor.GREEN.toString() + content.get(ServerManager.getSeed(b)).value + ChatColor.GOLD + "コル");
		buyPrices.put(13, content.get(ServerManager.getSeed(b)).value);
		b--;
		setItem(16, 1, content.get(ServerManager.getSeed(b)).key, ChatColor.YELLOW + "Price: " + ChatColor.GREEN.toString() + content.get(ServerManager.getSeed(b)).value + ChatColor.GOLD + "コル");
		buyPrices.put(16, content.get(ServerManager.getSeed(b)).value);
		setItem(22, Material.GOLD_NUGGET, 1, ChatColor.YELLOW + "Sell Menu", ChatColor.GREEN + "Sell your unneeded items.");
		setInventoryListener(this);
	}

	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		GamePlayer gp = ServerManager.getPlayer(p);
		int cor = gp.getAttribute("cor").getLevel();
		Integer price = buyPrices.get(slot);
		ItemCustom c = CraftingTableManager.isCustomItem(i);
		switch(slot) {
		case 10:
			if (cor >= price) {
				gp.getAttribute("cor").addLevels(-price);
				p.sendMessage(ChatColor.GREEN + "Successfully purchased " + c.getDisplayName() + " for " + price + " " + ChatColor.GOLD + "コル");
				gp.addItem(c);
			}else {
				p.sendMessage(ChatColor.RED + "You do not have enough " + ChatColor.GOLD + "コル" + ChatColor.RED + " to purchase that item.");
			}
			break;
		case 13:
			if (cor >= price) {
				gp.getAttribute("cor").addLevels(-price);
				p.sendMessage(ChatColor.GREEN + "Successfully purchased " + c.getDisplayName() + " for " + price + " " + ChatColor.GOLD + "コル");
				gp.addItem(c);
			}else {
				p.sendMessage(ChatColor.RED + "You do not have enough " + ChatColor.GOLD + "コル" + ChatColor.RED + " to purchase that item.");
			}
			break;
		case 16:
			if (cor >= price) {
				gp.getAttribute("cor").addLevels(-price);
				p.sendMessage(ChatColor.GREEN + "Successfully purchased " + c.getDisplayName() + " for " + price + " " + ChatColor.GOLD + "コル");
				gp.addItem(c);
			}else {
				p.sendMessage(ChatColor.RED + "You do not have enough " + ChatColor.GOLD + "コル" + ChatColor.RED + " to purchase that item.");
			}
			break;
		case 22:
			SellMenu sm = new SellMenu(p, 0);
			sm.send();
			break;
		}
	}
	
}
