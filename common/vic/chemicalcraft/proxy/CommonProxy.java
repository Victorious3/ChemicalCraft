package vic.chemicalcraft.proxy;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.MinecraftForge;

import vic.chemicalcraft.ChemicalCraft;
import vic.chemicalcraft.gases.Gas;
import vic.chemicalcraft.handler.CraftingHandler;
import vic.chemicalcraft.handler.EventHandler;
import vic.chemicalcraft.handler.PlayerTracker;
import vic.chemicalcraft.handler.TickHandlerMP;
import vic.chemicalcraft.items.GenericSubstance;
import vic.chemicalcraft.items.HeatAbsorber;
import vic.chemicalcraft.items.PeriodicTable;
import vic.chemicalcraft.substance.SubstanceResearchClient;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
    public static String ITEMS_PNG = "/vic/chemicalcraft/resources/items.png";
    public static String BLOCK_PNG = "/vic/chemicalcraft/resources/terrain.png";
    
    public static String GUI_PERIODIC = "/vic/chemicalcraft/resources/Gui_PeriodicTable.png";

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

	public void registerBlocksAndItems() 
	{
		ChemicalCraft.genericGas = new Gas(2501, 0, Material.air).setBlockName("genericGas").setCreativeTab(CreativeTabs.tabDecorations);
		ChemicalCraft.substance = new GenericSubstance(ChemicalCraft.substanceID).setItemName("substance").setCreativeTab(ChemicalCraft.tabSubstances);
		ChemicalCraft.tongs = new HeatAbsorber(ChemicalCraft.tongsID, 80).setIconCoord(7, 1).setItemName("tongs").setCreativeTab(ChemicalCraft.tabSubstances); 
		ChemicalCraft.gloves = new HeatAbsorber(ChemicalCraft.glovesID, 30).setIconCoord(6, 1).setItemName("gloves").setCreativeTab(ChemicalCraft.tabSubstances); 
		ChemicalCraft.periodicTable = new PeriodicTable(ChemicalCraft.periodicTableID).setIconCoord(8, 1).setItemName("periodicTable").setCreativeTab(ChemicalCraft.tabSubstances); 
	}
}