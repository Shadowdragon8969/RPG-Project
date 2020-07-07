package shadowdev.item.defaults.swords;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.ToolX;

public class GuiltyThorn extends ToolX {

	public GuiltyThorn() {
		super(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Guilty Thorn", "guiltyThorn", Material.WOODEN_SWORD, 43.0f, true, "A handcrafted longsword made from moderately durable metal. There isn't anything special about it.");
		noDurability = true;
		attackSpeed = -2.2;
	}
	
	@Override
	public int getEnhancements() {
		return 12;
	}
	
}
