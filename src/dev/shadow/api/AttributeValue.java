package dev.shadow.api;

import org.bukkit.inventory.EquipmentSlot;

public class AttributeValue {
		
	float value;
	EquipmentSlot eq = EquipmentSlot.HAND;
		
	public AttributeValue(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}
	
	public void setSlot(EquipmentSlot e) {
		eq = e;
	}
		
	
}
