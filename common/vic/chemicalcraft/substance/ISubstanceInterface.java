package vic.chemicalcraft.substance;

import net.minecraft.item.ItemStack;

public interface ISubstanceInterface {

	/**
	 * Returns the SubstanceStack for an Item or Block.
	 * @param stack
	 * @return SubstanceStack
	 */
	public SubstanceStack getSubstanceStack(ItemStack stack);
	
	/**
	 * Return the maximum size of the SubstanceStack allocated for an Item or Block.
	 * @return int
	 */
	public int maxSubstanceStackSize();
	
	/**
	 * Returns true if the SubstanceStack ingredients can be changed.
	 * @param stack
	 * @return boolean
	 */
	public boolean hasStorage(ItemStack stack);
	
	/**
	 * Provides a new ItemStack when the Phase of the Substance changed.
	 * @param phase
	 * @return ItemStack
	 */
	public ItemStack getItemStackBasedOnPhase(EnumPhase phase);
}
