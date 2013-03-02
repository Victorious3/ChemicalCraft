package vic.chemicalcraft.substance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.common.primitives.Ints;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import vic.chemicalcraft.helper.TextFormat;
import vic.chemicalcraft.proxy.ClientProxy;

public class SubstanceStack {

	public Substance[] substance;
	public int[] amounts;
	public int amount;
	public int temperature;
	public EnumPhase phase;
	
	public SubstanceStack(int amount, Substance[] substance, int[] amounts, int temperature, EnumPhase phase)
	{
		if(amounts.length != substance.length) throw new RuntimeException("SubstanceStack amount and substance need to have the same lenght!");
		
		this.substance = substance;
		this.amounts = amounts;
		this.amount = amount;
		this.temperature = temperature;
		this.phase = phase;
	}
	
	/**
	 * Returns the current Substance out of Substance Stack
	 * @return substance
	 */
	public Substance getSubstance(int tag)
	{
		return substance[tag];
	}
	
	public int getAmount(int tag)
	{
		return amounts[tag];
	}
	
    /**
     * Writes current SubstanceStack to the given TagCompound.
     * @param par1NBTTagCompound
     */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	if(this == null || par1NBTTagCompound == null) return;
		
		NBTTagCompound stack = new NBTTagCompound();  	
    	
    	stack.setInteger("amount", this.amount);
    	stack.setInteger("temperature", this.temperature);
    	stack.setIntArray("amounts", this.amounts);
    	stack.setString("phase", this.phase.toString());

    	NBTTagCompound substances = new NBTTagCompound();
    	
    	for(int i = 0; i < this.substance.length; i++)
    	{
    		substances.setString(""+i, this.substance[i].Name);
    	}
    	
    	stack.setTag("substances", substances);
    	
    	par1NBTTagCompound.setTag("CC_substanceStack", stack);
    }

   
	/**
	 * Gets the Substance Stack out of a given TagCompound. Returns null if there is none.
	 * @param par1NBTTagCompound
	 * @return SubstanceStack
	 */
    public static SubstanceStack getFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if(par1NBTTagCompound != null)
        {
			if(par1NBTTagCompound.hasKey("CC_substanceStack"))
		    {
		    	NBTTagCompound stack = par1NBTTagCompound.getCompoundTag("CC_substanceStack");
		    	
		    	int amount = stack.getInteger("amount");
		    	int temperature = stack.getInteger("temperature");
		    	int[] amounts = stack.getIntArray("amounts");
		    	EnumPhase phase = EnumPhase.valueOf(stack.getString("phase"));
		    	ArrayList<Substance> substance1 = new ArrayList<Substance>();
		    	
		    	NBTTagCompound substancelist = stack.getCompoundTag("substances");
		    	
		    	for(int i = 0; substancelist.hasKey(""+i); i++)
		    	{
		    		substance1.add(SubstanceRegistry.getSubstanceByName(substancelist.getString(""+i)));
		    	}
		    	
		    	Substance[] substance = (Substance[])substance1.toArray(new Substance[substance1.size()]); 
		    	
		    	return new SubstanceStack(amount, substance, amounts, temperature, phase);
		    }
        }
    	
        return null;
    }
	
	public String toString()
	{
		String out = "";
		
		out += amount + " ";
		
		for(int i = 0; i < substance.length; i++)
		{
			String symbol = substance[i].Symbol;
			if(!ClientProxy.substanceResearchClient.isResearched(substance[i]) && !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) symbol = "?";
			String amount = TextFormat.getLowerValue(amounts[i]);
			out += symbol + amount;
		}
		return out;
	}
	
	public String toStringWithStorage(int storage)
	{
		String out = "";
		
		out += amount + "/" + storage + " ";
		
		for(int i = 0; i < substance.length; i++)
		{
			String symbol = substance[i].Symbol;
			if(!ClientProxy.substanceResearchClient.isResearched(substance[i]) && !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) symbol = "?";
			String amount = TextFormat.getLowerValue(amounts[i]);
			out += symbol + amount;
		}
		return out;
	}
	
	/**
	 * Returns all colors added together.
	 * @return int
	 */
	public int addColors()
	{
		int color = 0;
		
		for(int i = 0; i < substance.length; i++)
		{
			color += substance[i].Color;
		}
		
		return color;
	}
	
	/**
	 * Adds the content of a given Substance Stack to the Substance Stack
	 * @param stack
	 * @param multiplier (used for ItemStacks with more that 1 Item)
	 */
	public void addStack(SubstanceStack stack, int multiplier) 
	{
		if(stack != null)
		{
			this.amount += stack.amount * multiplier;
			this.temperature = this.temperature + stack.temperature / 2 - this.temperature / 2;
			
			HashMap h1 = new HashMap<Substance, Integer>();
			
			for(int i = 0; i < this.amounts.length; i++)
			{
				if(!h1.containsKey(this.substance[i])) h1.put(this.substance[i], this.amounts[i]);
				else
				{
					int var1 = (Integer)h1.get(this.substance[i]);
					var1 += this.amounts[i];
					h1.put(this.substance[i], var1);
				}
			}
			for(int i = 0; i < stack.amounts.length; i++)
			{
				if(!h1.containsKey(stack.substance[i])) h1.put(stack.substance[i], stack.amounts[i] * multiplier);
				else
				{
					int var1 = (Integer)h1.get(stack.substance[i]);
					var1 += stack.amounts[i] * multiplier;
					h1.put(stack.substance[i], var1);
				}
			}

			Collection<Integer> o1a = h1.values();
			this.amounts = Ints.toArray(o1a);
			
			this.substance = (Substance[]) h1.keySet().toArray(new Substance[h1.keySet().size()]);
		}		
	}
	
	public void addAllStacks(SubstanceStack[] stack, int multipier[])
	{
		for(int i = 0; i < stack.length; i++)
		{
			addStack(stack[i], multipier[i]);
		}
	}
	
	public void researchAllSubstances(EntityPlayer p)
	{
		for(int i = 0; i < this.substance.length; i++)
		{
			SubstanceResearch.changeResearch(this.substance[i], true, p);
		}
	}
	
	public boolean hasResearchedAllSubstances(EntityPlayer p)
	{
		boolean out = true;
		for(int i = 0; i < this.substance.length; i++)
		{
			if(!SubstanceResearch.hasResearched(p, this.substance[i])) out = false;
		}
		return out;
	}
	
	public EnumPhase phase()
	{
		int meltingPoint = substance[0].meltingPoint;
		int boilingPoint = substance[0].boilingPoint;		
		
		for(int i = 1; i < substance.length; i++)
		{
			meltingPoint = substance[i].meltingPoint / 2 + meltingPoint / 2;
			boilingPoint = substance[i].boilingPoint / 2 + boilingPoint / 2; 
		}
		
		if(this.temperature > meltingPoint && this.temperature < boilingPoint) return EnumPhase.LIQUID;
		if(this.temperature < meltingPoint) return EnumPhase.SOLID;
		
		return EnumPhase.GASEOUS;
	}
	
	public SubstanceStack copy()
	{
		return new SubstanceStack(amount, substance, amounts, temperature, phase);
	}
}
