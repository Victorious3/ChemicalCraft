package vic.chemicalcraft.handler;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;


public class PlayerTracker implements IPlayerTracker{

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		player.sendChatToPlayer("Welcome " + player.username + " to the ForgeCraft server! You are currently in World " + player.worldObj.provider.getDimensionName() + " and the current time is: " + player.worldObj.provider.getWorldTime());
		PacketHandler.sendResearchData(player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}


}
