package vic.chemicalcraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import vic.chemicalcraft.blocks.BlockHeatAcceptor;
import vic.chemicalcraft.blocks.BlockHeatProducer;
import vic.chemicalcraft.blocks.Gas;
import vic.chemicalcraft.blocks.GasBore;
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
    
    // Items
    public static Item substance;
    public static Item gloves;
    public static Item tongs;
    public static Item periodicTable;
	
	public static void registerBlocksAndItems()
	{
		gasBore = new GasBore(ChemicalCraft.gasBoreID, 2, Material.iron).setBlockName("gasBore").setCreativeTab(ChemicalCraft.tabSubstances);
		genericGas = new Gas(ChemicalCraft.genericGasID, 0, Material.air).setBlockName("genericGas");
		heatProducer = new BlockHeatProducer(ChemicalCraft.heatProducerID, 1, Material.iron).setBlockName("heatProducer").setCreativeTab(ChemicalCraft.tabSubstances);
		heatAcceptor = new BlockHeatAcceptor(ChemicalCraft.heatAcceptorID, 1, Material.iron).setBlockName("heatAcceptor").setCreativeTab(ChemicalCraft.tabSubstances);
		
		substance = new GenericSubstance(ChemicalCraft.substanceID).setItemName("substance").setCreativeTab(ChemicalCraft.tabSubstances);
		tongs = new HeatAbsorber(ChemicalCraft.tongsID, 80).setIconCoord(7, 1).setItemName("tongs").setCreativeTab(ChemicalCraft.tabSubstances); 
		gloves = new HeatAbsorber(ChemicalCraft.glovesID, 30).setIconCoord(6, 1).setItemName("gloves").setCreativeTab(ChemicalCraft.tabSubstances); 
		periodicTable = new PeriodicTable(ChemicalCraft.periodicTableID).setIconCoord(8, 1).setItemName("periodicTable").setCreativeTab(ChemicalCraft.tabSubstances); 
		
        GameRegistry.registerBlock(genericGas);
        GameRegistry.registerBlock(gasBore);
        GameRegistry.registerBlock(heatProducer);
        GameRegistry.registerBlock(heatAcceptor);
	}
	
	public static void addNames()
	{
        LanguageRegistry.addName(genericGas, "Generic Gas");
        LanguageRegistry.addName(gasBore, "Gas Bore");
        LanguageRegistry.addName(heatProducer, "Heat Producer");
        LanguageRegistry.addName(heatAcceptor, "Heat Acceptor");
        LanguageRegistry.addName(substance, "Substance");
        LanguageRegistry.addName(tongs, "Iron Tongs");
        LanguageRegistry.addName(gloves, "Lether Gloves");
        LanguageRegistry.addName(periodicTable, "Periodic Table");
        
	}
}
