package vic.chemicalcraft.api.heat;

import net.minecraftforge.common.ForgeDirection;

/**
 * This interface is for accepting heat from heat emitting blocks.
 * You don't have to post any Events in order to use it.
 * @author Vic
 */
public interface IHeatAcceptor {
	
	/**
	 * @param direction
	 * @param sender
	 * @param amount
	 * @return heat that is not accepted
	 */
	public int getHeat(ForgeDirection direction, int amount);
	
	public boolean getActiveSides(ForgeDirection direction);

}
