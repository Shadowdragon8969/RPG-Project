package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class HealthShroom extends MaterialX {

	public HealthShroom() {
		super(Material.RED_MUSHROOM, "hpshroom", 20);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Red Mushroom");
		setLore("A tiny red mushroom.");
		noDurability = true;
	}

	
	
}
