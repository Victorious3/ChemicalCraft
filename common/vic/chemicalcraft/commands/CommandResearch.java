package vic.chemicalcraft.commands;

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
				PacketHandler.sendResearchData(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player));
			}
			else 
			{
				var1.sendChatToPlayer(TextFormat.RED + "The Player " + var2[0] + " doesn't exist.");
				return;
			}
		}
		else
		{
			var1.sendChatToPlayer(this.getCommandUsage(var1));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender par1iCommandSender) 
	{
		return TextFormat.RED + "Usage: /changeresearch [PlayerName] [SubstanceName] [boolean]";
	}

}
