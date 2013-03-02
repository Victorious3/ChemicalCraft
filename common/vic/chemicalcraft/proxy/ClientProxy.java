package vic.chemicalcraft.proxy;

import vic.chemicalcraft.handler.TickHandlerSP;
import vic.chemicalcraft.substance.SubstanceResearchClient;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {  
	
	public static SubstanceResearchClient substanceResearchClient = new SubstanceResearchClient(); 
	
	@Override
    public void registerRenderers() {
            MinecraftForgeClient.preloadTexture(ITEMS_PNG);
            MinecraftForgeClient.preloadTexture(BLOCK_PNG);
            MinecraftForgeClient.preloadTexture(GUI_PERIODIC);
    }
    
    public void registerHandlersSP() {
    	TickRegistry.registerTickHandler(new TickHandlerSP(), Side.CLIENT);
    }  
}