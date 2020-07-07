package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class IronBroadsword extends ToolX {

	public IronBroadsword() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Iron Broadsword", "ironSword", Material.IRON_SWORD, 8.0f, true, "A sluggish broadsword. The craftsmanship looks moderate.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 6;
	}
	
}
