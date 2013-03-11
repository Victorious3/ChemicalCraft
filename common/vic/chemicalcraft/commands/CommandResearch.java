package vic.chemicalcraft.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import vic.chemicalcraft.handler.PacketHandler;
import vic.chemicalcraft.helper.TextFormat;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceResearch;
import vic.chemicalcraft.substance.SubstanceResearchClient;

public class CommandResearch extends CommandBase{

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "changeresearch";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) 
	{
		if(var2.length == 3)
		{
			String player = var2[0];
			Substance substance = SubstanceRegistry.getSubstanceByName(var2[1]);
			boolean bool = Boolean.parseBoolean(var2[2]);
			
			if(substance == null) 
			{
				var1.sendChatToPlayer(TextFormat.RED + "The Substance " + var2[1] + " doesn't exist."); 
				return;
			}
			
			SubstanceResearchClient research = SubstanceResearch.getResearchList(player);
			
			if(research != null)
			{
				(SubstanceResearch.getResearchList(player)).changeResearched(substance, bool);
				if(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player) != null)PacketHandler.sendResearchData(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player));
				var1.sendChatToPlayer("Successfully changed the research for Substance " + var2[1] + " and player " + var2[0] + " to " + var2[2] + ".");
			}
			else 
			{
				var1.sendChatToPlayer(TextFormat.RED + "The Player " + var2[0] + " doesn't exist.");
				return;
			}
		}
		else
		{
			var1.sendChatToPlayer(TextFormat.RED + "Usage: " + this.getCommandUsage(var1));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender par1iCommandSender) 
	{
		return "/changeresearch [PlayerName] [SubstanceName] [boolean]";
	}

	@Override
	public int getRequiredPermissionLevel() 
	{
		return super.getRequiredPermissionLevel();
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender par1iCommandSender, String[] par2ArrayOfStr) 
	{
		if(par2ArrayOfStr.length == 1)
		{
			return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
		}
		if(par2ArrayOfStr.length == 2)
		{
			return getListOfStringsMatchingLastWord(par2ArrayOfStr, SubstanceRegistry.getAllSubstanceNames());
		}
		
		return null;
	}

}
