package shadowdev.player;

import org.bukkit.entity.Player;

public abstract class Buff {
	
	String label;
	int strength, dur;
	
	public Buff(String label, int strength, int duration) {
		this.label = label;
		this.strength = strength;
		this.dur = duration;
	}
	
	public abstract void onApply(Player p);
	
	public abstract void onLoss(Player p);
	
}
