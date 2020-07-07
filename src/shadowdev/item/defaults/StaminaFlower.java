package shadowdev.item.defaults;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import shadowdev.item.MaterialX;

public class StaminaFlower extends MaterialX {

	public StaminaFlower() {
		super(Material.DANDELION, "stashroom", 20);
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Stamina Flower");
		setLore("A yellow flower with stamina", "restoring properties.");
		noDurability = true;
	}

	
	
}
