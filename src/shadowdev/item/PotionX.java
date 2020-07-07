package shadowdev.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import dev.shadow.api.IInteractable;
import dev.shadow.api.ItemCustom;
import shadowdev.player.Buff;
import shadowdev.player.GamePlayer;
import shadowdev.server.ServerManager;

public class PotionX extends ItemCustom implements IInteractable {

	Buff t;
	int strength;
	
	public PotionX(Buff t, String id) {
		super(Material.POTION, id);
		this.t = t;
		noDurability = true;
	}
	
	protected void setColor(Color c) {
		p = c;
	}
	
	@Override
	public void onInteract(Player user) {
		user.playSound(user.getLocation(), Sound.ENTITY_WITCH_DRINK, 1, 1);
		GamePlayer gp = ServerManager.getPlayer(user);
		gp.addBuff(t);
	}
	
}
