package vic.chemicalcraft.blocks.tileentity;

import ic2.api.Direction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import vic.chemicalcraft.CC_Registry;

public class TileEntityGasBore extends TileEntityElectric {

	public TileEntityGasBore() 
	{
		super.maxPowerLevel = 10000;
		super.maxSafeInput = 32;
		super.energyDemand = 16;
	}
		
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
	
	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) 
	{
		return new ItemStack(CC_Registry.gasBore.blockID, 1, 0);
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) 
	{
		return direction.toForgeDirection() == ForgeDirection.getOrientation(this.getFacing()).getOpposite();
	}
	
	
}
