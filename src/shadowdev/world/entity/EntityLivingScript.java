package shadowdev.world.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

public interface EntityLivingScript extends EntityScript {

	public void whenAlive(Creature c, LivingEntity e, Location l, World w);
	
}
