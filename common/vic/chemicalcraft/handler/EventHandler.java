package vic.chemicalcraft.handler;

import vic.chemicalcraft.substance.SubstanceResearch;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

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
	public void worldUnload(WorldEvent.Save event)
	{

	}
}
