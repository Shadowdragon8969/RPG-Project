package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class IronOre extends MaterialX {

	public IronOre() {
		super(Material.IRON_ORE, "ironOre", 35);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Iron Ore");
		setLore("A piece of stone with Iron inside.", "It looks difficult to smelt.");
		noDurability = true;
	}

	
	
}
