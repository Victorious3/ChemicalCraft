package vic.chemicalcraft.helper;

import vic.chemicalcraft.items.HeatAbsorber;
import vic.chemicalcraft.substance.ISubstanceInterface;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceStack;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class HeatTicker {

	public static void updateHeat(ItemStack itemstack, double posX, double posY, double posZ, World world, Entity par3Entity)
	{		
		int heat = BiomeTemperature.instance.currTemperature(posX, posY, posZ, world);
		
		if(itemstack.getTagCompound() != null)
		{
			SubstanceStack substanceStack = SubstanceStack.getFromNBT(itemstack.getTagCompound());
			if(substanceStack == null) return; 
			
			heat = substanceStack.temperature;
			int currHeat = BiomeTemperature.instance.currTemperature(posX, posY, posZ, world);
			
			if(heat > currHeat) heat--;
			if(heat < currHeat) heat++;
			
			substanceStack.temperature = heat;
			
			if((heat > 60 || heat < -25) ) hurtPlayer(heat, par3Entity);

			if(world.isRemote)
			{
				if(substanceStack.phase != substanceStack.phase())
				{				
					ISubstanceInterface IS = SubstanceRegistry.getSubstanceInterface(itemstack);
					if(IS != null)
					{
						if(IS.getItemStackBasedOnPhase(substanceStack.phase()) != null)
						{													
							itemstack.itemID = IS.getItemStackBasedOnPhase(substanceStack.phase()).itemID;
							itemstack.setItemDamage(IS.getItemStackBasedOnPhase(substanceStack.phase()).getItemDamage());
							itemstack.stackSize = IS.getItemStackBasedOnPhase(substanceStack.phase()).stackSize;					
							itemstack.setTagCompound(new NBTTagCompound());
							substanceStack.phase = substanceStack.phase();
						}
					}
				}
				substanceStack.writeToNBT(itemstack.getTagCompound());
			}
		}
		return;
	}

	private static void hurtPlayer(int heat, Entity ent) 
	{		
		EntityPlayerMP playerClient = null;
		EntityPlayer player = (EntityPlayer)ent;
		
		int percent = 0;
		int damage = 0;
		
		for(int i = 0; i < 9; i++)
		{
			if(player.inventory.getStackInSlot(i) != null)
			{
				if(player.inventory.getStackInSlot(i).getItem() instanceof HeatAbsorber)
				{
					int currPercent = ((HeatAbsorber)player.inventory.getStackInSlot(i).getItem()).absorbs(); 
					if(currPercent > percent) percent = currPercent;  
				}	
			}
		}
		
		damage = heat / 60; 
		if(heat < 0) damage *= -1;
		
		damage = (damage * (100 - percent)) / 100;
		
		if(!ent.worldObj.isRemote)
		{
			playerClient = (EntityPlayerMP)ent;
			if(heat > 0)playerClient.attackEntityFrom(CC_DamageSource.scorch, damage);
			if(heat < 0)playerClient.attackEntityFrom(CC_DamageSource.freeze, damage);	
		}		
	}	
}
