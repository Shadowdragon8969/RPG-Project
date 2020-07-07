package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class FrostSteelBlade extends ToolX {

	public FrostSteelBlade() {
		super(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Blade of Frost Steel", "frostSteelSword", Material.IRON_SWORD, 48.0f, true, "A heavy sharpened blade. The steel is icy cold to the touch.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	
}
