package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class ToughBoarSword extends ToolX {

	public ToughBoarSword() {
		super(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Tough Boar Sword", "boarSword", Material.IRON_SWORD, 4.3f, true, "A handcrafted sword made with the tusk of a wild boar.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 5;
	}
	
}
