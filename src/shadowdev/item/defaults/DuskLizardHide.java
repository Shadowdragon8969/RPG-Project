package shadowdev.item.defaults;

import org.bukkit.Material;

import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;

public class DuskLizardHide extends ItemCustom {

	public DuskLizardHide() {
		super(Material.BLACK_DYE, "dlh");
		setDisplayName(ChatColor.BLUE + "[RAR] " + ChatColor.WHITE + "Dusk Lizard Hide");
		setLore("A high quality material", "used to make armor.");
		noDurability = true;
	}

}
