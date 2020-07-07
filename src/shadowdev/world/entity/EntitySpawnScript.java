package shadowdev.world.entity;

import org.bukkit.entity.LivingEntity;

public interface EntitySpawnScript extends EntityScript {

	public void onSpawn(LivingEntity e);
	
}
