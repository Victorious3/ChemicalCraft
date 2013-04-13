package vic.chemicalcraft.blocks.tileentity;

import vic.chemicalcraft.api.heat.IHeatAcceptor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityTemperedFabricator extends TileEntity implements IHeatAcceptor
{
    public int orientation = 2;
    
    @Override
    public int getHeat(ForgeDirection direction, int amount) 
    {
        return 0;
    }

    @Override
    public boolean getActiveSides(ForgeDirection direction) 
    {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) 
    {
        super.readFromNBT(par1nbtTagCompound);
        orientation = par1nbtTagCompound.getInteger("orientation");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) 
    {
        super.writeToNBT(par1nbtTagCompound);
        par1nbtTagCompound.setInteger("orientation", orientation);
    }

    @Override
    public void updateEntity() 
    {
        super.updateEntity();
    }

}
