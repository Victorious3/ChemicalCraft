package vic.chemicalcraft.items;

import vic.chemicalcraft.ChemicalCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PeriodicTable extends GenericChemItem {

	public PeriodicTable(int par1) 
	{
		super(par1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

		par3EntityPlayer.openGui(ChemicalCraft.instance, 1, par2World, par3EntityPlayer.chunkCoordX, par3EntityPlayer.chunkCoordY, par3EntityPlayer.chunkCoordZ);
		
		return par1ItemStack;
	}

	
}
