package vic.chemicalcraft.substance;

import java.util.Collection;
import java.util.LinkedHashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


public class SubstanceRegistry {
	
	private static LinkedHashMap substances = new LinkedHashMap<String, Substance>();
	private static LinkedHashMap blocksList = new LinkedHashMap<Object, SubstanceStack>();
	
	public static SubstanceRegistry instance = new SubstanceRegistry();
	
	// real Elements
	public static Substance Iron = new Substance("Iron", 0xBC9980, "Fe", 1538, 2862);
	public static Substance Gold = new Substance("Gold", 0xFCEE4B, "Ag", 1064, 2856);
	public static Substance Aluminium = new Substance("Aluminium", 0x999999, "Al", 660, 2519);
	public static Substance Silicium = new Substance("Silicium", 0x3F3F3F, "Si", 1414, 2357);
	public static Substance Phosphor = new Substance("Phosphor", 0xFFEE8E, "P", 44, 280);
	public static Substance Sulphur = new Substance("Sulphur", 0xFFDE23, "S", 115, 444);
	public static Substance Natrium = new Substance("Natrium", 0xD8D7D2, "Na", 98, 890);
	public static Substance Carbon = new Substance("Carbon", 0x373737, "C", 3674, 4827);
	public static Substance Oxygen = new Substance("Oxygen", 0xA8A8FF, "O", -219, -183);
	public static Substance Nitrogen = new Substance("Nitrogen", 0xC1FFCD, "N", -210, -196);
	public static Substance Hydrogen = new Substance("Hydrogen", 0xFFD1D1, "H", -247, -253);	
	
	// Minecraft ones
	public static Substance Electrolyt = new Substance("Electrolyt", 0xFFD1D1, "El", 978, 1345);
	public static Substance Quarz = new Substance("Quarz", 0xFFFFFF, "Qz", 1266, 2680);
	
	private static int pointer = 1;
	
	public void addManifests()
	{
		bindSubstanceStack(Item.redstone.getItemName(), new SubstanceStack(1, new Substance[]{Electrolyt}, new int[]{4}, 1, EnumPhase.SOLID));
		bindSubstanceStack(Block.bedrock.getBlockName(), new SubstanceStack(1, new Substance[]{Carbon}, new int[]{10}, 1, EnumPhase.SOLID));
	}
    
	/**
     * Returns the static instance of this class
     */
    public static final SubstanceRegistry getInstance()
    {
        return instance;
    }
	
	/**
	 * returns a the Substance of an Block / Item / Gas / Liquid
	 * @return
	 */
	public static SubstanceStack getSubstanceStack(String string)
	{
		if(blocksList.containsKey(string))
		{
			return (SubstanceStack)blocksList.get(string);
		}
		else
		{
			return null;
		}
	}
	
	public static Substance[] getAllSubstances()
	{
		
		Collection object = substances.values();
		Substance[] sublist = (Substance[]) object.toArray(new Substance[object.size()]);
		
		return sublist;
		
	}
	
	/**
	 * returns a Substance by its name
	 * @return Substance
	 */
	public static Substance getSubstanceByName(String name)
	{
		if(substances.containsKey(name))
		{
			return (Substance)substances.get(name);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Creates and adds a new Substance to the Substance List
	 */
	public static void createSubstance(String name, String symbol, int color, int meltingPoint, int boilingPoint)
	{
		new Substance(name, color, symbol, meltingPoint, boilingPoint);
	}
	
	/**
	 * Adds a existing Substance to the Substance List.
	 */
	public static void addSubstance(String name, Substance substance)
	{
		pointer++;
		substance.id = pointer;
		substances.put(name, substance);
	}
	
	/**
	 * Used to bind a SubstanceStack to the name of a Block, Item, ...
	 */
	public static void bindSubstanceStack(String string, SubstanceStack substanceStack)
	{
		blocksList.put(string, substanceStack);
	}
	
	/**
	 * Tries every way to get a SubstanceStack out of a given ItemStack
	 * @param ItemStack
	 * @return SubstanceStack
	 */
	public static SubstanceStack provideSubstanceStack(ItemStack stack)
	{
		Item item = stack.getItem();
		SubstanceStack out = null;
		
		if(SubstanceRegistry.getSubstanceStack(item.getItemName()) != null)
        {
        	out = SubstanceRegistry.getSubstanceStack(item.getItemName());
        }   
        if(item instanceof ItemBlock)
        {
        	Block block = Block.blocksList[((ItemBlock)item).getBlockID()];
        	if(block instanceof ISubstanceInterface)
            {
            	ISubstanceInterface IS = (ISubstanceInterface) block;
            	out = IS.getSubstanceStack(stack);       	       	          	
            }
        }
        if(item instanceof ISubstanceInterface)
        {
        	ISubstanceInterface IS = (ISubstanceInterface) item;
        	if(IS.getSubstanceStack(stack) != null)
        	{
        		out = IS.getSubstanceStack(stack); 
        	}      	
        }      
        if(SubstanceStack.getFromNBT(stack.getTagCompound()) != null)
        {
        	out = SubstanceStack.getFromNBT(stack.getTagCompound()); 
        }
        
        return out;
	}
			
	public static ISubstanceInterface getSubstanceInterface(ItemStack itemStack)
	{
		Item item = itemStack.getItem();
		ISubstanceInterface IS = null;
		
		if(item instanceof ItemBlock)
        {
        	Block block = Block.blocksList[((ItemBlock)item).getBlockID()];
        	if(block instanceof ISubstanceInterface)
            {
            	IS = (ISubstanceInterface) block; 	       	          	
            }
        }
        if(item instanceof ISubstanceInterface)
        {
        	IS = (ISubstanceInterface) item;     	
        }
        
        return IS;
	}
}
