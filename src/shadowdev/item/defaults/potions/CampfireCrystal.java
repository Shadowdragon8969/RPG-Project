package shadowdev.item.defaults.potions;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import shadowdev.item.PotionX;
import shadowdev.player.Buff;
import shadowdev.server.ServerManager;

public class CampfireCrystal extends PotionX {

	public CampfireCrystal() {
		super(new Buff("Health Boost", 1, 1) {
			
			@Override
			public void onLoss(Player p) {
				
			}
			
			@Override
			public void onApply(Player p) {
				
			}
		}, "campfire");
		setDisplayName(ChatColor.GREEN + "[UNC] " + ChatColor.WHITE + "Campfire Crystal");
		setLore("A campfire stored in a crystal.", ChatColor.GREEN + "Effect: " + ChatColor.GOLD + "Spawns a campfire where you are looking.");
		setColor(Color.ORANGE);
	}

	@Override
	public void onInteract(Player user) {
		Block b = user.getTargetBlock(3);
		if (b != null) {
			Block t = b.getLocation().add(0, 1, 0).getBlock();
			if (b.isPassable()) {
				b.setType(Material.CAMPFIRE);
				user.sendMessage(ChatColor.GREEN + "Campfire Deployed!");
				return;
			}
			user.sendMessage(t.getType().toString());
			if (t == null || t.getType() == Material.AIR || t.getType() == Material.CAVE_AIR) {
				t.setType(Material.CAMPFIRE);
				user.sendMessage(ChatColor.GREEN + "Campfire Deployed!");
				return;
			}
		}
		ServerManager.getPlayer(user).addItem(this);
	}
	
}
