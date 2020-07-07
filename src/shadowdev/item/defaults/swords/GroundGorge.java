package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class GroundGorge extends ToolX {

	public GroundGorge() {
		super(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Ground Gorge", "groundGorge", Material.WOODEN_AXE, 110.0f, true, "A tough double bladed axe. It is as sharp as it is heavy.");
		noDurability = true;
		attackSpeed = -3.2;
	}
	
	
}
