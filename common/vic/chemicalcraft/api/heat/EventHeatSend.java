package vic.chemicalcraft.api.heat;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.world.WorldEvent;
import vic.chemicalcraft.helper.Position;

public class EventHeatSend extends WorldEvent {

	public final Position position;
	public ArrayList<ForgeDirection> fdircetion = new ArrayList<ForgeDirection>();
	public final int heat;
	public final IHeatProducer sender;
	
	public EventHeatSend(IHeatProducer producer) {
		super(((TileEntity)producer).worldObj);
		
		this.position = new Position(((TileEntity)producer).xCoord, ((TileEntity)producer).yCoord, ((TileEntity)producer).zCoord, ForgeDirection.UNKNOWN);
		
		for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
		{
			if(producer.getActiveSides(d))
			{
				this.fdircetion.add(d);
			}
		}
		
		this.heat = producer.getHeatAmount();
		this.sender = producer;
	}

}
