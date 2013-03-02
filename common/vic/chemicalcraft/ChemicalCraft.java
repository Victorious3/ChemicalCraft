package vic.chemicalcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;

import net.minecraftforge.common.Configuration;
import universalelectricity.core.UniversalElectricity;
import vic.chemicalcraft.blocks.GasBore;
import vic.chemicalcraft.blocks.GenericChemBlock;
import vic.chemicalcraft.blocks.tileentity.TileEntityGas;
import vic.chemicalcraft.blocks.tileentity.TileEntityGasBore;
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
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="ChemicalCraft", name="Chemical Craft", version="0.0pre1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"CC_researchData"}, packetHandler = PacketHandler.class)
public class ChemicalCraft {

        // The instance of your mod that Forge uses.
        @Instance("ChemicalCraft")
        public static ChemicalCraft instance;
        
        // Says where the client and server 'proxy' code is loaded.
        @SidedProxy(clientSide="vic.chemicalcraft.proxy.ClientProxy", serverSide="vic.chemicalcraft.proxy.CommonProxy")
        public static CommonProxy proxy;
        
        public static CreativeTabs tabSubstances = new SubstanceTab("Chemicals");
        
        // Config IDs       
        public static int genericGasID;
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
        	
        	genericGasID = config.get(Configuration.CATEGORY_BLOCK, "genericGas", 2501).getInt();
        	substanceID = config.get(Configuration.CATEGORY_ITEM, "genericSubstance", 10000).getInt();
        	glovesID = config.get(Configuration.CATEGORY_ITEM, "gloves", 10001).getInt();
        	tongsID = config.get(Configuration.CATEGORY_ITEM, "tongs", 10002).getInt();
        	periodicTableID = config.get(Configuration.CATEGORY_ITEM, "periodicTable", 10003).getInt(); 
        	
        	config.save();
        	
        	SubstanceRegistry.getInstance();
        	UniversalElectricity.register(this, 1, 2, 2, false);
        }
        
        // Blocks
        public static Block genericDirt = new GenericChemBlock(2500, 1, Material.ground).setHardness(0.5F).setStepSound(Block.soundGravelFootstep).setBlockName("genericDirt").setCreativeTab(CreativeTabs.tabDecorations);
        public static Block genericGas;
        public static Block gasBore = new GasBore(2502, 3, Material.iron).setBlockName("gasBore").setCreativeTab(tabSubstances);
        // Items
        public static Item substance;
        public static Item gloves;
        public static Item tongs;
        public static Item periodicTable;
        
        
        @Init
        public void load(FMLInitializationEvent event) {
        	
        	proxy.registerBlocksAndItems();
        	
        	proxy.registerHandlersMP();
        	proxy.registerHandlersSP();
        	proxy.registerRenderers();
     	
        	GameRegistry.registerBlock(genericDirt);
            GameRegistry.registerBlock(genericGas);
            GameRegistry.registerBlock(gasBore);
        	
        	LanguageRegistry.addName(genericDirt, "Generic Dirt");
            LanguageRegistry.addName(genericGas, "Generic Gas");
            LanguageRegistry.addName(gasBore, "Gas Bore");
            LanguageRegistry.addName(substance, "Substance");
            LanguageRegistry.addName(tongs, "Iron Tongs");
            LanguageRegistry.addName(gloves, "Lether Gloves");
            LanguageRegistry.addName(periodicTable, "Periodic Table");
            
            GameRegistry.registerTileEntity(TileEntityGas.class, "TileEntityGas");
            GameRegistry.registerTileEntity(TileEntityGasBore.class, "TileEntityGasBore");
                
            // Crafting
                
            CraftingManager.getInstance().getRecipeList().add(new SubstanceRecipes());
            NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
        }
        
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
        	SubstanceRegistry.getInstance().addManifests();
        	
        	if(Loader.isModLoaded("Thaumcraft")) isThaumcraftLoaded = true;
        	if(Loader.isModLoaded("NotEnoughItems")) isNEILoaded = true;
        }
}