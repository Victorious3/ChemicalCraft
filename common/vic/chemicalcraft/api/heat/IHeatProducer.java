package vic.chemicalcraft.api.heat;

import net.minecraftforge.common.ForgeDirection;

/**
 * When you implement this interface, you have to remember to post an {@link vic.chemicalcraft.api.heat.EventHeatSend EventHeatSend}
 * every time you are sending an amount of heat to the ActiveSides.
 * @author Vic 
 */
public interface IHeatProducer {

	public int getHeatAmount();
	
	public void onHeatSend(ForgeDirection direction, int amount);
	
	public boolean getActiveSides(ForgeDirection direction);
	
}
