package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class SwordBreaker extends ToolX {

	public SwordBreaker() {
		super(ChatColor.LIGHT_PURPLE + "[EPC] " + ChatColor.WHITE + "Sword Breaker", "swordBreaker", Material.STONE_SWORD, 43.0f, true, "A shorter blade with a serrated backside that is used to catch incoming swords.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	
}
