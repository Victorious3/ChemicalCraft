package vic.chemicalcraft.blocks.tileentity;

import ic2.api.Direction;
import net.minecraft.tileentity.TileEntity;

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
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		// TODO Auto-generated method stub
		return true;
	}
}
