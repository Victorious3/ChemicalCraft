package vic.chemicalcraft.proxy;

import net.minecraftforge.common.MinecraftForge;
import vic.chemicalcraft.handler.CraftingHandler;
import vic.chemicalcraft.handler.EventHandler;
import vic.chemicalcraft.handler.PlayerTracker;
import vic.chemicalcraft.handler.TickHandlerMP;
import vic.chemicalcraft.substance.SubstanceResearchClient;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
    
    public static String GUI_PERIODIC = "/vic/chemicalcraft/textures/Gui_PeriodicTable.png";
    public static String TEX_GASBORE = "/vic/chemicalcraft/textures/GasBore.png";
    public static String TEX_STOVE = "/vic/chemicalcraft/textures/Stove.png";

    public static SubstanceResearchClient substanceResearchClient; 
    
    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics!
    }
    
    public void registerHandlersMP() {
    	TickRegistry.registerTickHandler(new TickHandlerMP(), Side.SERVER);
    	
    	GameRegistry.registerCraftingHandler(new CraftingHandler());
    	GameRegistry.registerPlayerTracker(new PlayerTracker());
  	
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
    
    public void registerHandlersSP() {}

	public void registerBlocksAndItems() {}
}