package vic.chemicalcraft.blocks.tileentity;

import ic2.api.Direction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityGasBore extends TileEntityElectric {

	public TileEntityGasBore() 
	{
		super.maxPowerLevel = 10000;
		super.maxSafeInput = 32;
		super.energyDemand = 16;
	}
		
	@Override
	public boolean isOrientationValid(EntityPlayer entityPlayer, int side) 
	{
		return side != ForgeDirection.UP.ordinal() && side != ForgeDirection.DOWN.ordinal();
	}

	@Override
	public void updateEntity() 
	{
		super.updateEntity();
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return direction.toForgeDirection() == ForgeDirection.getOrientation(this.getFacing()).getOpposite();
	}
	
	
}
