package vic.chemicalcraft.blocks;

import vic.chemicalcraft.proxy.CommonProxy;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GenericChemBlock extends BlockContainer{

        public GenericChemBlock (int id, int texture, Material material) {
                super(id, texture, material);
        }
        
        @Override
        public String getTextureFile() {
                return CommonProxy.BLOCK_PNG;
        }

		@Override
		public TileEntity createNewTileEntity(World var1) {
			return null;
		}

		@Override
		public boolean hasTileEntity(int metadata) {
			return false;
		}
}
