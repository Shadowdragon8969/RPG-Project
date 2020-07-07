package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class BriskLeaf extends MaterialX {

	public BriskLeaf() {
		super(Material.WHEAT_SEEDS, "bleaf", 10);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Brisk Leaf");
		setLore("A smooth leaf with speed", "altering properties.");
		noDurability = true;
	}

	
	
}
