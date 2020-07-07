package shadowdev.world.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;

import dev.shadow.api.ItemCustom;
import dev.shadow.api.ObjectSet;
import net.minecraft.server.v1_15_R1.EntityCreature;
import net.minecraft.server.v1_15_R1.EntityTypes;

public class BossCreature extends Creature {

	List<ObjectSet<ItemCustom, Double>> bossLoot = new ArrayList<ObjectSet<ItemCustom, Double>>();
	
	public BossCreature(String name, String id, int hp, double atk, double spd, EntityTypes<? extends EntityCreature> et) {
		super(name, id, null, new int[] {0, 10}, true, hp, atk, spd, et);
	}
	
	public void addBossLoot(ItemCustom drop, double damageP) {
		bossLoot.add(new ObjectSet<ItemCustom, Double>(drop, damageP));
	}
	
	public ItemCustom getDropFor(double dam) {
		List<ObjectSet<ItemCustom, Double>> sorted = sortIntArray(bossLoot);
		for (ObjectSet<ItemCustom, Double> drop : sorted) {
			if (dam / hp * 100 >= drop.value) {
				return drop.key;
			}
		}
		return null;
	}
	
	private List<ObjectSet<ItemCustom, Double>> sortIntArray(List<ObjectSet<ItemCustom, Double>> ir) {
		List<ObjectSet<ItemCustom, Double>> newI = new ArrayList<ObjectSet<ItemCustom, Double>>();
		for (int i = 0; i < ir.size(); i++) {
			newI.add(null);
		}
		for (int i = 0; i < ir.size(); i++) {
			int placement = 0;
			for (int i2 = 0; i2 < ir.size(); i2++) {
				if (i == i2) continue;
				if (ir.get(i).value < ir.get(i2).value) placement++;
			}
			newI.set(placement, ir.get(i));
		}
		return newI;
	}
	
}
