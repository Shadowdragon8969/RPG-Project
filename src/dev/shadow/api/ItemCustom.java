package dev.shadow.api;

import org.bukkit.Color;
import org.bukkit.Material;

public abstract class ItemCustom {
	
	protected boolean noDurability = false;
	protected int durability = 10;
	OnBreakScript bsk;
	ExistScript esk;
	OnAttackScript ask;
	IInteractable interactScript;
	Material type;
	public String id;
	public int id2 = -1;
	String[] lore;
	protected Color m = null, p = null;
	private String displayName;
	boolean hideFlags = true;
	
	public ItemCustom(Material type, String id) {
		this.type = type;
		this.id = id;
	}
	
	public void applyFullScript(Object o) {
		if (o instanceof OnAttackScript && o instanceof OnBreakScript && o instanceof ExistScript) {
			bsk = (OnBreakScript) o;
			ask = (OnAttackScript) o;
			esk = (ExistScript) o;
		}
	}
	
	public void addInteractScript(IInteractable i) {
		interactScript = i;
	}
	
	public IInteractable getInteractScript() {
		return interactScript;
	}
	
	public void addBreakScript(OnBreakScript i) {
		bsk = i;
	}
	
	public final int getRegistryID() {
		return id2;
	}
	
	public final boolean hasBreakScript() {
		return bsk != null;
	}
	
	public final OnBreakScript getBreakScript() {
		return bsk;
	}
	
	public void addAttackScript(OnAttackScript i) {
		ask = i;
	}
	
	public final boolean hasAttackScript() {
		return ask != null;
	}
	
	public final OnAttackScript getAttackScript() {
		return ask;
	}
	
	public void addBreakScript(ExistScript i) {
		esk = i;
	}
	
	public final boolean hasExistScript() {
		return esk != null;
	}
	
	public final ExistScript getExistScript() {
		return esk;
	}
	
	protected final void setLore(String... s) {
		lore = s;
	}
	
	public String[] getLore() {
		return lore;
	}
	
	protected final void setDisplayName(String s) {
		displayName = s;
	}
	
	protected final int getMaxDurability() {
		return durability;
	}
	
	public final boolean hasNoDurability() {
		return noDurability;
	}
	
	public final String getId() {
		return id;
	}
	
	public final String getDisplayName() {
		return displayName;
	}
	
	public final Material getType() {
		return type;
	}
	
}