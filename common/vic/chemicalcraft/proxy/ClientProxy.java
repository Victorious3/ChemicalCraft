package vic.chemicalcraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import vic.chemicalcraft.CC_Registry;
import vic.chemicalcraft.ChemicalCraft;
import vic.chemicalcraft.blocks.render.RenderGasBore;
import vic.chemicalcraft.blocks.tileentity.TileEntityGasBore;
import vic.chemicalcraft.handler.TickHandlerSP;
import vic.chemicalcraft.helper.BlockRenderHandler;
import vic.chemicalcraft.substance.SubstanceResearchClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {  
	
	public static SubstanceResearchClient substanceResearchClient = new SubstanceResearchClient(); 
	
	@Override
    public void registerRenderers() {
	    
		ChemicalCraft.renderID = RenderingRegistry.getNextAvailableRenderId();
		
		MinecraftForgeClient.preloadTexture(ITEMS_PNG);
	    MinecraftForgeClient.preloadTexture(BLOCK_PNG);
	    MinecraftForgeClient.preloadTexture(GUI_PERIODIC);
	    MinecraftForgeClient.preloadTexture(TEX_GASBORE);
	    
	    
	    RenderingRegistry.registerBlockHandler(new BlockRenderHandler());
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasBore.class, new RenderGasBore());
	    
	    BlockRenderHandler.renderList.put(new BlockRenderHandler.Index(CC_Registry.gasBore, 0), new RenderGasBore());
    }
    
    public void registerHandlersSP() {
    	TickRegistry.registerTickHandler(new TickHandlerSP(), Side.CLIENT);
    }  
}