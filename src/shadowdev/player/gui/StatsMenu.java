package shadowdev.player.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import shadowdev.player.GamePlayer;
import shadowdev.server.ServerManager;

public class StatsMenu extends PlayerGui implements InventoryListener {

	public StatsMenu(Player p) {
		super(p, ChatColor.GREEN + "Stats", 54);
		setItem(6, Material.DIAMOND_SWORD, 1, ChatColor.YELLOW + "Swords", "Shows the swords that", "you own and can equip");
		setItem(15, Material.IRON_CHESTPLATE, 1, ChatColor.YELLOW + "Armor", "Shows the armor that", "you own and can equip");
		setItem(24, Material.BLAZE_POWDER, 1, ChatColor.YELLOW + "Stats", "Shows your stats a levels");
		setItem(33, Material.BOOK, 1, ChatColor.YELLOW + "Skills", "Shows the skills that", "you have acquired and", "their attack patterns");
		setItem(42, Material.EMERALD, 1, ChatColor.YELLOW + "Potions and Crystals", "Shows your potions and crystals.");
		setItem(51, Material.CHEST, 1, ChatColor.YELLOW + "Inventory", "Shows the items that", "you have collected");
		
		GamePlayer gp = ServerManager.getPlayer(p);
		setItem(10, Material.ANVIL, 1, ChatColor.AQUA + "Smithing Level", "Level: " + gp.getStat("smithing").getLevel(), "Exp: " + (int) gp.getStat("smithing").getExp() + "/" + (int) Math.pow(gp.getStat("smithing").getLevel(), 2) * 15);
		setItem(11, Material.IRON_SWORD, 1, ChatColor.AQUA + "Combat Level", "Level: " + gp.getStat("cmbt_level").getLevel(), "Exp: " + (int) gp.getStat("cmbt_level").getExp() + "/" + (int) Math.pow(gp.getStat("cmbt_level").getLevel(), 2) * 15);
		setItem(12, Material.JUNGLE_SAPLING, 1, ChatColor.AQUA + "Foraging Level", "Level: " + gp.getStat("forage").getLevel(), "Exp: " + (int) gp.getStat("forage").getExp() + "/" + (int) Math.pow(gp.getStat("forage").getLevel(), 2) * 15);
		setItem(19, Material.POTION, 1, ChatColor.AQUA + "Alchemy Level", "Level: " + gp.getStat("alchemy").getLevel(), "Exp: " + (int) gp.getStat("alchemy").getExp() + "/" + (int) Math.pow(gp.getStat("alchemy").getLevel(), 2) * 15);
		setItem(21, Material.COOKED_PORKCHOP, 1, ChatColor.AQUA + "Cooking Level", "Level: " + gp.getStat("cooking").getLevel(), "Exp: " + (int) gp.getStat("cooking").getExp() + "/" + (int) Math.pow(gp.getStat("cooking").getLevel(), 2) * 15);
		
		setItem(20, Material.RED_DYE, 1, ChatColor.RED + "Strength: " + gp.getAttribute("strength").getLevel());
		setItem(28, Material.YELLOW_DYE, 1, ChatColor.RED + "Max Stamina: " + gp.getAttribute("stamina").getLevel());
		setItem(29, Material.LIGHT_BLUE_DYE, 1, ChatColor.RED + "Defense: " + gp.getAttribute("defense").getLevel());
		setItem(30, Material.MAGENTA_DYE, 1, ChatColor.RED + "Max Health: " + gp.getAttribute("hitpoint").getLevel());
		setItem(37, Material.PINK_DYE, 1, ChatColor.RED + "Health Regeneration: " + gp.getAttribute("regen").getLevel());
		setItem(38, Material.BLUE_DYE, 1, ChatColor.RED + "Critical Damage: " + gp.getAttribute("crit_damage").getLevel() + "%");
		setItem(39, Material.CYAN_DYE, 1, ChatColor.RED + "Critical Chance: " + gp.getAttribute("crit_chance").getLevel() + "%");
		
		setItem(47, Material.GOLD_BLOCK, 1, ChatColor.YELLOW + "Attribute Points: " + gp.getAttribute("atbpts").getLevel(), "Click on a stat to raise its level");
		
		for (int i = 5; i < 54; i += 9) {
			setItem(i, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
			setItem(i + 2, Material.GRAY_STAINED_GLASS_PANE, 1, " ", "");
		}
		setItem(23, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		setItem(25, Material.GREEN_STAINED_GLASS_PANE, 1, " ", "");
		
		setInventoryListener(this);
	}
	
	@Override
	public void onClick(Player p, ItemStack i, int slot, ClickType click, Inventory inv) {
		new MainMenu(p).onClick(p, i, slot, click, inv);
		GamePlayer gp = ServerManager.getPlayer(p);
		boolean update = false;
		if (gp.getAttribute("atbpts").getLevel() < 1) return;
		switch (slot) {
		case 20:
			gp.getAttribute("strength").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		case 28:
			gp.getAttribute("stamina").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		case 29:
			gp.getAttribute("defense").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		case 30:
			gp.getAttribute("hitpoint").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		case 37:
			gp.getAttribute("regen").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		case 38:
			gp.getAttribute("crit_damage").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		case 39:
			gp.getAttribute("crit_chance").addLevel();
			gp.getAttribute("atbpts").removeLevel();
			update = true;
			break;
		}
		if (update) {
			StatsMenu s = new StatsMenu(p);
			s.send();
		}
	}
	
}
