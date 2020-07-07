package shadowdev.item.defaults;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import dev.shadow.api.IInteractable;
import dev.shadow.api.ItemCustom;
import net.md_5.bungee.api.ChatColor;
import shadowdev.player.gui.MainMenu;

public class MenuItem extends ItemCustom implements IInteractable {

	public MenuItem() {
		super(Material.NETHER_STAR, "menu");
		setDisplayName(ChatColor.GRAY + "< " + ChatColor.GREEN + "Menu" + ChatColor.GRAY + " >");
		setLore("Right Click to open");
		noDurability = true;
	}

	@Override
	public void onInteract(Player user) {
		MainMenu m = new MainMenu(user);
		m.send();
	}

	
	
}
