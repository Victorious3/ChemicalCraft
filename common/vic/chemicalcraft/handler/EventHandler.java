package vic.chemicalcraft.handler;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import vic.chemicalcraft.api.heat.EventHeatSend;
import vic.chemicalcraft.api.heat.IHeatAcceptor;
import vic.chemicalcraft.helper.Position;
import vic.chemicalcraft.substance.SubstanceResearch;

public class EventHandler {

	@ForgeSubscribe
	public void worldLoad(WorldEvent.Load event)
	{
		if(!event.world.isRemote)SubstanceResearch.loadResearch(event.world);
	}

	
	@ForgeSubscribe
	public void worldSave(WorldEvent.Save event)
	{
		if(!event.world.isRemote)SubstanceResearch.saveResearch(event.world);
	}
	
	@ForgeSubscribe
	public void worldUnload(WorldEvent.Save event){}
	
	@ForgeSubscribe
	public void heatSend(EventHeatSend event) 
	{				
		for (ForgeDirection o : event.fdircetion)
		{
			Position p = event.position;
			p.direction = o;
			p.plus(1);
			
//			System.out.println(p.x + " " + p.y + " " + p.z);
			
			TileEntity TE = event.world.getBlockTileEntity((int)p.x, (int)p.y, (int)p.z);
			
			if(TE != null)
			{
				if(TE instanceof IHeatAcceptor)
				{
					IHeatAcceptor ha = (IHeatAcceptor)TE;
					
					if(ha.getActiveSides(o.getOpposite()))
					{
						int heatBack = ha.getHeat(o.getOpposite(), event.heat);
						event.sender.onHeatSend(o, heatBack);
					}
				}
			}
		}
	}	
}
