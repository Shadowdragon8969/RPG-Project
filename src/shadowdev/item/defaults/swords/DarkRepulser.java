package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class DarkRepulser extends ToolX {

	public DarkRepulser() {
		super(ChatColor.GOLD + "[LGD] " + ChatColor.WHITE + "Dark Repulser", "darkRepulser", Material.DIAMOND_SWORD, 160.0f, true, "An absurdly heavy blade made from Crystallite. It can only be crafted by the best smiths");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 25;
	}
	
}
