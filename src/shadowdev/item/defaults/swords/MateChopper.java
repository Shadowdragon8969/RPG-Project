package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class MateChopper extends ToolX {

	public MateChopper() {
		super(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Mate Chopper", "mateChopper", Material.IRON_INGOT, 100.0f, true, "A sharp cleaver wielded by certain monsters.");
		noDurability = true;
		attackSpeed = 0;
	}
	
	
}
