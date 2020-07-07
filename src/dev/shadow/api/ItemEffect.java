package dev.shadow.api;

public class ItemEffect {

	public enum ItemEffectType{
		ICE,
		FIRE,
		LIGHTNING,
		ATTACK
	}
	
	ItemEffectType type;
	double intervalDam;
	
	public ItemEffect(ItemEffectType t, double d) {
		type = t;
		intervalDam = d;
	}
	
	public double getCoefficient() {
		return intervalDam;
	}
	
	public ItemEffectType getEffect() {
		return type;
	}
	
}
