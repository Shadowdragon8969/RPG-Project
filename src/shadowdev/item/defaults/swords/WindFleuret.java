package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class WindFleuret extends ToolX {

	public WindFleuret() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Wind Fleuret", "windfleuret", Material.IRON_SWORD, 12.0f, true, "A quick rapier with a sharp edge. It is warm to the touch.");
		noDurability = true;
		attackSpeed = -0.8;
	}
	
	
}
