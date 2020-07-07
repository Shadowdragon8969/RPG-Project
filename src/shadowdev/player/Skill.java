package shadowdev.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Skill {
	
	String[] pattern;
	boolean active = false;
	int staCost = 10;
	String id, name;
	Material icon;
	String[] desc;
	
	public Skill(boolean active, String name, Material icon, String id, String... pattern) {
		this.pattern = pattern;
		this.active = active;
		this.id = id;
		this.icon = icon;
		this.name = name;
	}
	
	public void setDescription(String... desc) {
		this.desc = desc;
	}
	
	public String[] getDescription() {
		return desc;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Material getIcon() {
		return icon;
	}
	
	public int getStaminaCost() {
		return staCost;
	}
	
	public abstract void useSkill(Player user);
	
}
