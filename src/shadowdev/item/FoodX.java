package shadowdev.item;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.shadow.api.IConsumable;
import dev.shadow.api.IInteractable;
import dev.shadow.api.ItemCustom;

public class FoodX extends ItemCustom implements IConsumable, IInteractable {

	int drumsticks;
	float sat;
	int cookingReq = 0;
	
	public FoodX(Material type, String id, int drumsticks, float saturation) {
		super(type, id);
		sat = saturation;
		this.drumsticks = drumsticks;
	}
	
	public int getDrumsticks() {
		return drumsticks;
	}
	
	public float getSaturation() {
		return sat;
	}
	
	protected void setCookingReq(int x) {
		cookingReq = x;
	}
	
	public int getCookingRequirment() {
		return cookingReq;
	}
	
	@Override
	public void onInteract(Player user) {
		user.playSound(user.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 1);
		user.setSaturation(sat);
		user.setFoodLevel(Math.min(user.getFoodLevel() + drumsticks, 20));
		if (Math.min(user.getFoodLevel() + drumsticks, 20) == 20) {
			user.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (user.getFoodLevel() + drumsticks - 20) * 20, 1));
		}
	}
	
}
