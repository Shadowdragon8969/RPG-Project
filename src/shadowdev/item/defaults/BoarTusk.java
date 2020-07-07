package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class BoarTusk extends MaterialX {

	public BoarTusk() {
		super(Material.BONE, "boarTusk", 0);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Boar Tusk");
		setLore("A sturdy tusk used for the", "creation of weapons.");
		noDurability = true;
	}

	
	
}
