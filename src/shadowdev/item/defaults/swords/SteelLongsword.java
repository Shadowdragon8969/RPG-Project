package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class SteelLongsword extends ToolX {

	public SteelLongsword() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Steel Longsword", "steelSword", Material.STONE_SWORD, 7.0f, true, "A durable steel sword. The weight feels balanced.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 12;
	}
	
}
