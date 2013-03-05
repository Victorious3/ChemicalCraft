package vic.chemicalcraft.blocks.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import vic.chemicalcraft.api.heat.EventHeatSend;
import vic.chemicalcraft.api.heat.IHeatProducer;

public class TileEntityHeatProducer extends TileEntity implements IHeatProducer {

	public int heat = 0;
	public int heatAmount = 5;
	
	@Override
	public int getHeatAmount() 
	{
		return heatAmount;
	}

	@Override
	public void onHeatSend(ForgeDirection direction, int amount) 
	{	
		heat += amount;
	}

	@Override
	public boolean getActiveSides(ForgeDirection direction) 
	{
		return true;
	}

	@Override
	public void updateEntity() 
	{
		if(getHeatAmount() != 0)MinecraftForge.EVENT_BUS.post(new EventHeatSend(this));
	}

	
}
