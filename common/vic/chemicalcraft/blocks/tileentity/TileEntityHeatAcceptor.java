package vic.chemicalcraft.blocks.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import vic.chemicalcraft.api.heat.IHeatAcceptor;

public class TileEntityHeatAcceptor extends TileEntity implements IHeatAcceptor {

	public int heat = 0;
	
	@Override
	public int getHeat(ForgeDirection direction, int amount)
	{
		heat += amount;
		return 0;
	}

	@Override
	public boolean getActiveSides(ForgeDirection direction) 
	{
		return true;
	}

}
