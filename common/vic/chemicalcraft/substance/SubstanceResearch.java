package vic.chemicalcraft.substance;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import vic.chemicalcraft.handler.PacketHandler;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class SubstanceResearch {
	
	public static HashMap players = new HashMap<String, SubstanceResearchClient>();

	public static void changeResearch(Substance s, boolean b, EntityPlayer p)
	{
		if(players.containsKey(p.username))
		{
			SubstanceResearchClient res = (SubstanceResearchClient) players.get(p.username);
			res.changeResearched(s, b);
			PacketHandler.sendResearchData(p);
		}
		else
		{		
			if(createPlayer(p)) changeResearch(s, b, p);			
		}
	}
	
	public static boolean createPlayer(EntityPlayer p) 
	{		
		try{
			players.put(p.username, new SubstanceResearchClient());
		} catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static SubstanceResearchClient getResearchList(String p)
	{
		return (SubstanceResearchClient) players.get(p);
	}

	public static boolean hasResearched(EntityPlayer p, Substance sub)
	{
		return getResearchList(p.username).isResearched(sub);
	}
	
	public static void saveResearch(World world)
	{
		if(world.provider.dimensionId == 0) 
		{
			WorldServer worldServer = (WorldServer)world;
	        File researchFolder = worldServer.getChunkSaveLocation();
	        
	        NBTTagCompound worldData = new NBTTagCompound();
	        NBTTagList players = new NBTTagList();
	      
	        int count = 0;
	      
	        for(Object player : SubstanceResearch.players.keySet().toArray())
	        {
	    	    String p = (String)player;		    	  
	    	    SubstanceResearchClient res = SubstanceResearch.getResearchList(p);
	    	    
	    	    NBTTagList substances = new NBTTagList(); 
	    	    NBTTagCompound playerCompound = new NBTTagCompound();	  	    
	    	    playerCompound.setString("playerName", p);
	    	    
	    		for(Object substance : res.research.keySet().toArray())
	    	    {
	    			Substance s = (Substance)substance;
	    		    String sname = s.Name;
	    		    boolean bool = res.isResearched(s);
	    		  
	    		    NBTTagCompound playerSubstance = new NBTTagCompound();
	    		    playerSubstance.setString("substanceName", sname);
	    		    playerSubstance.setBoolean("isResearched", bool);
	    		  	    		  
	    		    substances.appendTag(playerSubstance);
	    	    }
	    	    
	    		playerCompound.setTag("substances", substances);
	    	    players.appendTag(playerCompound);
	    	  
	    	    count++;
	        }
	      
	        worldData.setTag("players", players);

	        if (count > 0) {
	        	File researchData = new File(researchFolder, "CC_research.dat");
	        	try {
	        		CompressedStreamTools.write(worldData, researchData);
	        	} catch (Exception e) {
	        		FMLLog.log(Level.WARNING, e, "[ChemicalCraft] Failed to write Substance Research file to %s for world " + world.provider.dimensionId, new Object[]{researchData.getAbsolutePath()});
	        	}
	        }
	    }
	}
	
	public static void loadResearch(World world)
	{
		if(world.provider.dimensionId == 0) 
		{
			WorldServer worldServer = (WorldServer)world;
	        File researchFolder = worldServer.getChunkSaveLocation();
	        
	        File researchData = new File(researchFolder, "CC_research.dat");
	        
	        if(researchData.exists() && researchData.isFile())
	        {
	        	NBTTagCompound worldData;
	        	
	        	try {
					worldData = CompressedStreamTools.read(researchData);
				} catch (IOException e) {					
					FMLLog.log(Level.WARNING, e, "[ChemicalCraft] Failed to get Substance Research file from %s for world " + world.provider.dimensionId + " creating a blank one. If this happens on every load, this is an error.", new Object[]{researchData.getAbsolutePath()});					
					return;
				}
	        	
	        	NBTTagList players = worldData.getTagList("players");
	        	
	        	for(int i = 0; i < players.tagCount(); i++)
	        	{
	        		NBTTagCompound playerCompound = (NBTTagCompound)players.tagAt(i);
	        		String playerName = playerCompound.getString("playerName");
	        		        		
	        		
	        		NBTTagList substanceList = playerCompound.getTagList("substances");
	        		SubstanceResearchClient researchClient = new SubstanceResearchClient();
	        		
	        		for(int j = 0; j < substanceList.tagCount(); j++)
	        		{
	        			NBTTagCompound substanceCompound = (NBTTagCompound)substanceList.tagAt(j);
	        			
	        			String sname = substanceCompound.getString("substanceName");        			
		    		    boolean bool = substanceCompound.getBoolean("isResearched");
		    		    Substance s = SubstanceRegistry.getSubstanceByName(sname);
		    		    
		    		    researchClient.changeResearched(s, bool);
	        		}
	        		
	        		SubstanceResearch.players.put(playerName, researchClient);
	        	}
	        }
	        else
	        {
	        	FMLLog.log(Level.INFO, "[ChemicalCraft] No Substance Research file existing for world " + world.provider.dimensionId + ". Skipping.", new Object[]{});
	        }
		}
	}
	
	public static void unloadResearch()
	{
		SubstanceResearch.players = new HashMap<String, SubstanceResearchClient>();		
	}
	
	
}
