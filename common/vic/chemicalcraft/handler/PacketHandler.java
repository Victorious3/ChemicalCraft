package vic.chemicalcraft.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

import vic.chemicalcraft.proxy.ClientProxy;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceResearch;
import vic.chemicalcraft.substance.SubstanceResearchClient;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{		
		if (packet.channel.equals("CC_researchData")) 
		{
			updateResearchData(packet);
		}
    }

	private void updateResearchData(Packet250CustomPayload packet)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try{
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			NBTTagCompound streamCompound = CompressedStreamTools.decompress(b);
			NBTTagList substanceList = streamCompound.getTagList("substanceList");
			
			SubstanceResearchClient research = new SubstanceResearchClient();
					
			for(int i = 0; i < substanceList.tagCount(); i++)
			{
				NBTTagCompound substanceCompound = (NBTTagCompound)substanceList.tagAt(i); 
				String substance = substanceCompound.getString("substanceName");
				boolean isResearched = substanceCompound.getBoolean("isResearched");
				
				research.changeResearched(SubstanceRegistry.getSubstanceByName(substance), isResearched);
			}
			
			ClientProxy.substanceResearchClient = research;
			
		} catch (Exception ex) {
			FMLLog.log(Level.WARNING, "[ChemicalCraft] Failed to read Research Data for player " + Minecraft.getMinecraft().thePlayer.username + "!", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void sendResearchData(EntityPlayer p)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();		
		DataOutputStream outputStream = new DataOutputStream(bos);
		SubstanceResearchClient research = SubstanceResearch.getResearchList(p.username);
		if(research == null)return;
    
		NBTTagCompound streamCompound = new NBTTagCompound();
		NBTTagList substanceList = new NBTTagList();
		
		try {
        	for(Object s : research.research.keySet().toArray())
        	{
        		NBTTagCompound substanceCompound = new NBTTagCompound();
        		String sname = ((Substance)s).Name;
        		boolean isResearched = (Boolean)research.research.get(s);
        		substanceCompound.setString("substanceName", sname);
        		substanceCompound.setBoolean("isResearched", isResearched);
        		substanceList.appendTag(substanceCompound);
        	}
        	streamCompound.setTag("substanceList", substanceList);
        	outputStream.write(CompressedStreamTools.compress(streamCompound));
        	
        	Packet250CustomPayload packet = new Packet250CustomPayload();
            packet.channel = "CC_researchData";
            packet.data = bos.toByteArray();
            packet.length = bos.size();
            
            PacketDispatcher.sendPacketToPlayer(packet, (Player)p);
        	
        } catch (Exception ex) {
        	FMLLog.log(Level.WARNING, "[ChemicalCraft] Failed to send Research Data to player " + p.username + "!", ex.getMessage());
        	ex.printStackTrace();
        	return;
        }        
	}	
}
