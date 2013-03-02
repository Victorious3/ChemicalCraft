package vic.chemicalcraft.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class CC_DamageSource extends DamageSource{
	
	public String deathMessage = "died";
	
	public static CC_DamageSource scorch = (CC_DamageSource) new CC_DamageSource("scorch", " has burnt himself to death").setDamageBypassesArmor();
	public static CC_DamageSource freeze = (CC_DamageSource) new CC_DamageSource("scorch", " froze to death").setDamageBypassesArmor();
	
	protected CC_DamageSource(String par1Str, String deathMessage) 
	{
		super(par1Str);
		this.deathMessage = deathMessage;
	}

	@Override
	public String getDeathMessage(EntityPlayer par1EntityPlayer) 
	{
		return par1EntityPlayer.username + this.deathMessage;
	}

}
