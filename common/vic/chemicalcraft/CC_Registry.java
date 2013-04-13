package vic.chemicalcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import vic.chemicalcraft.blocks.BlockHeatAcceptor;
import vic.chemicalcraft.blocks.BlockHeatProducer;
import vic.chemicalcraft.blocks.Gas;
import vic.chemicalcraft.blocks.GasBore;
import vic.chemicalcraft.blocks.TemperedFabricator;
import vic.chemicalcraft.items.GenericSubstance;
import vic.chemicalcraft.items.HeatAbsorber;
import vic.chemicalcraft.items.PeriodicTable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CC_Registry {

    // Blocks
    public static Block genericGas;
    public static Block gasBore;
    public static Block heatProducer;
    public static Block heatAcceptor;
    public static Block temperedFabricator;
    
    // Items
    public static Item substance;
    public static Item gloves;
    public static Item tongs;
    public static Item periodicTable;
	
	public static void registerBlocksAndItems()
	{
		gasBore = new GasBore(ChemicalCraft.gasBoreID, Material.iron, "default").setCreativeTab(ChemicalCraft.tabSubstances).setUnlocalizedName("Gas Bore");
		genericGas = new Gas(ChemicalCraft.genericGasID, Material.air, "gas");
		heatProducer = new BlockHeatProducer(ChemicalCraft.heatProducerID, Material.iron, "default").setCreativeTab(ChemicalCraft.tabSubstances).setUnlocalizedName("Heat Producer");
		heatAcceptor = new BlockHeatAcceptor(ChemicalCraft.heatAcceptorID, Material.iron, "default").setCreativeTab(ChemicalCraft.tabSubstances).setUnlocalizedName("Heat Acceptor");
		temperedFabricator = new TemperedFabricator(ChemicalCraft.temperedFabricatorID, Material.iron, "default").setCreativeTab(ChemicalCraft.tabSubstances).setUnlocalizedName("Tempered Furnace");
		
		substance = new GenericSubstance(ChemicalCraft.substanceID).setCreativeTab(ChemicalCraft.tabSubstances);
		tongs = new HeatAbsorber(ChemicalCraft.tongsID, 80, "tongs").setCreativeTab(ChemicalCraft.tabSubstances); 
		gloves = new HeatAbsorber(ChemicalCraft.glovesID, 30, "gloves").setCreativeTab(ChemicalCraft.tabSubstances); 
		periodicTable = new PeriodicTable(ChemicalCraft.periodicTableID).setCreativeTab(ChemicalCraft.tabSubstances); 
		
        GameRegistry.registerBlock(genericGas, "genericGas");
        GameRegistry.registerBlock(gasBore, "gasBore");
        GameRegistry.registerBlock(heatProducer, "heatProducer");
        GameRegistry.registerBlock(heatAcceptor, "heatAcceptor");
        GameRegistry.registerBlock(temperedFabricator, "temperedFabricator");
        
        GameRegistry.registerItem(gloves, "gloves");
        GameRegistry.registerItem(tongs, "tongs");
        GameRegistry.registerItem(substance, "substance");
        GameRegistry.registerItem(periodicTable, "periodicTable");
	}
	
	public static void addNames()
	{
        LanguageRegistry.addName(genericGas, "Generic Gas");
        LanguageRegistry.addName(gasBore, "Gas Bore");
        LanguageRegistry.addName(heatProducer, "Heat Producer");
        LanguageRegistry.addName(heatAcceptor, "Heat Acceptor");
        LanguageRegistry.addName(temperedFabricator, "Tempered Fabricator");
        LanguageRegistry.addName(substance, "Substance");
        LanguageRegistry.addName(tongs, "Iron Tongs");
        LanguageRegistry.addName(gloves, "Lether Gloves");
        LanguageRegistry.addName(periodicTable, "Periodic Table");
        
	}
}
