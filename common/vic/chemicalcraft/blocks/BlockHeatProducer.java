package vic.chemicalcraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vic.chemicalcraft.blocks.tileentity.TileEntityHeatProducer;

public class BlockHeatProducer extends GenericChemBlock {

	public BlockHeatProducer(int id, int texture, Material material) 
	{
		super(id, texture, material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityHeatProducer();
	}

	@Override
	public boolean hasTileEntity(int metadata) 
	{
		return true;
	}	
}
