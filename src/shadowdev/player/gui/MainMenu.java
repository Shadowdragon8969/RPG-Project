package shadowdev.player.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class MainMenu extends PlayerGui implements InventoryListener {

	public MainMenu(Player p) {
		super(p, ChatColor.GREEN + "Main Menu", 54);
		setItem(6, Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Swords", "Shows the swords that", "you own and can equip");
		setItem(15, Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", "Shows the armor that", "you own and can equip");
		setItem(24, Material.BLAZE_POWDER, 1, ChatColor.YELLOW + "Stats", "Shows your stats a levels");
		setItem(33, Material.BOOK, 1, ChatColor.YELLOW + "Skills", "Shows the skills that", "you have acquired and", "their attack patterns");
		setItem(42, Material.GOLD_NUGGET, 1, ChatColor.YELLOW + "Temporary! Sell Menu", "Sell your items for コル");
		setItem(42, Material.EMERALD, 1, ChatColor.YELLOW + "Potions and Crystals", "Shows your potions and crystals.");
		setItem(51, Material.CHEST, 1, ChatColor.YELLOW + "Inventory", "Shows the items that", "you have collected");
		
		
		
		for (int i = 5; i < 54; i += 9) {
			setItem(i, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
			setItem(i + 2, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		}
		
		setInventoryListener(this);
	}
	
	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		switch (slot) {
		case 6:
			SwordsMenu sm = new SwordsMenu(p, 0);
			sm.send();
			break;
		case 15:
			ArmorMenu am = new ArmorMenu(p, 0);
			am.send();
			break;
		case 24:
			StatsMenu s2 = new StatsMenu(p);
			s2.send();
			break;
		case 33:
			SkillMenu sk = new SkillMenu(p, 0);
			sk.send();
			break;
		case 42:
			PotionMenu smu = new PotionMenu(p, 0);
			smu.send();
			break;
		case 51:
			InventoryMenu im = new InventoryMenu(p, 0);
			im.send();
			break;
		}
	}
	
}
