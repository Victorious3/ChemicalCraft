package vic.chemicalcraft.helper;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.IBlockAccess;
import vic.chemicalcraft.ChemicalCraft;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRenderHandler implements ISimpleBlockRenderingHandler {

	public static class Index {

		Block block;
		int meta;
		
		public Index(Block block, int meta) 
		{
			this.block = block;
			this.meta = meta;
		}

		@Override
		public int hashCode() {
			return block.hashCode() + meta;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (!(obj instanceof Index))return false;
			Index index = (Index) obj;
			return index.block == block && index.meta == meta;
		}
	}
	
	public static HashMap<Index, TileEntitySpecialRenderer> renderList = new HashMap<Index, TileEntitySpecialRenderer>();
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) 
	{		
		if(renderList.containsKey(new Index(block, metadata)))
		{
			TileEntitySpecialRenderer TErenderer = renderList.get(new Index(block, metadata));
			TErenderer.renderTileEntityAt(null, -0.5D, -0.5D, -0.5D, 0.0F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) 
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() 
	{
		return true;
	}

	@Override
	public int getRenderId() 
	{
		return ChemicalCraft.renderID;
	}

}
