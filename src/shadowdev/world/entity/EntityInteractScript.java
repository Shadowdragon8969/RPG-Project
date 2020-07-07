package shadowdev.world.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface EntityInteractScript extends EntityScript {

	public boolean onInteract(Player p, LivingEntity e);
	
}
