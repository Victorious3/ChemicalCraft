package vic.chemicalcraft;

import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.Configuration;
import universalelectricity.core.UniversalElectricity;
import vic.chemicalcraft.blocks.tileentity.TileEntityGas;
import vic.chemicalcraft.blocks.tileentity.TileEntityGasBore;
import vic.chemicalcraft.blocks.tileentity.TileEntityHeatAcceptor;
import vic.chemicalcraft.blocks.tileentity.TileEntityHeatProducer;
import vic.chemicalcraft.commands.CommandResearch;
import vic.chemicalcraft.handler.GuiHandler;
import vic.chemicalcraft.handler.PacketHandler;
import vic.chemicalcraft.proxy.CommonProxy;
import vic.chemicalcraft.substance.SubstanceRecipes;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceTab;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="ChemicalCraft", name="Chemical Craft", version="0.0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"CC_researchData"}, packetHandler = PacketHandler.class)
public class ChemicalCraft {

        @Instance("ChemicalCraft")
        public static ChemicalCraft instance;
        
        @SidedProxy(clientSide="vic.chemicalcraft.proxy.ClientProxy", serverSide="vic.chemicalcraft.proxy.CommonProxy")
        public static CommonProxy proxy;
        
        public static CreativeTabs tabSubstances = new SubstanceTab("Chemicals");
        
        // Config IDs       
        public static int genericGasID;
        public static int gasBoreID;
        public static int heatProducerID;
        public static int heatAcceptorID;
        
        public static int substanceID;
        public static int glovesID;
        public static int tongsID;
        public static int periodicTableID;
        
        public static boolean isThaumcraftLoaded = false;
        public static boolean isNEILoaded = false;
        
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {       	
        	
        	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        	config.load();
        	
        	genericGasID = config.get(Configuration.CATEGORY_BLOCK, "genericGas", 2500).getInt();
        	gasBoreID = config.get(Configuration.CATEGORY_BLOCK, "gasBore", 2501).getInt();
        	heatProducerID = config.get(Configuration.CATEGORY_BLOCK, "heatProducer", 2502).getInt();
        	heatAcceptorID = config.get(Configuration.CATEGORY_BLOCK, "heatAcceptor", 2503).getInt();
        	
        	substanceID = config.get(Configuration.CATEGORY_ITEM, "genericSubstance", 10000).getInt();
        	glovesID = config.get(Configuration.CATEGORY_ITEM, "gloves", 10001).getInt();
        	tongsID = config.get(Configuration.CATEGORY_ITEM, "tongs", 10002).getInt();
        	periodicTableID = config.get(Configuration.CATEGORY_ITEM, "periodicTable", 10003).getInt(); 
        	
        	config.save();
        	
        	SubstanceRegistry.getInstance();
        	
        	UniversalElectricity.register(this, 1, 2, 2, false);
        }
        
        //Render IDs
        public static int renderID;
        
        @Init
        public void load(FMLInitializationEvent event) 
        {
        	
        	CC_Registry.registerBlocksAndItems();    	
        	
        	proxy.registerHandlersMP();
        	proxy.registerHandlersSP();
        	proxy.registerRenderers();
        	
        	CC_Registry.addNames();
            
            GameRegistry.registerTileEntity(TileEntityGas.class, "CC_TileEntityGas");
            GameRegistry.registerTileEntity(TileEntityGasBore.class, "CC_TileEntityGasBore");
            GameRegistry.registerTileEntity(TileEntityHeatProducer.class, "CC_TileEntityHeatProducer");
            GameRegistry.registerTileEntity(TileEntityHeatAcceptor.class, "CC_TileEntityHeatAcceptor");
                
            // Crafting                
            CraftingManager.getInstance().getRecipeList().add(new SubstanceRecipes());
            NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) 
        {
        	SubstanceRegistry.getInstance().addManifests();
        	
        	if(Loader.isModLoaded("Thaumcraft")) isThaumcraftLoaded = true;
        	if(Loader.isModLoaded("NotEnoughItems")) isNEILoaded = true;
        }
        
        @ServerStarting
        public void serverStarting(FMLServerStartingEvent event)
        {
           CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
           commandManager.registerCommand(new CommandResearch());
        }
}