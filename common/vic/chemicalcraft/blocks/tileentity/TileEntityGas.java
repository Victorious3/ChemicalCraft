package vic.chemicalcraft.blocks.tileentity;

import vic.chemicalcraft.substance.EnumPhase;
import vic.chemicalcraft.substance.Substance;
import vic.chemicalcraft.substance.SubstanceRegistry;
import vic.chemicalcraft.substance.SubstanceStack;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGas extends TileEntity {

	public SubstanceStack substanceStack = new SubstanceStack(1, new Substance[]{SubstanceRegistry.Oxygen}, new int[]{1}, 1, EnumPhase.GASEOUS);
	public int heat = 0;
	public int strenght = 100;
	
	public TileEntityGas(){}

	public TileEntityGas(SubstanceStack stack, int strenght)
	{
		this.substanceStack = stack;
		this.strenght = strenght;
	}	

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) 
	{
		super.readFromNBT(par1nbtTagCompound);
		substanceStack = SubstanceStack.getFromNBT(par1nbtTagCompound);
		strenght = par1nbtTagCompound.getInteger("gasStrenght");
	}	

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) 
	{	
		super.writeToNBT(par1nbtTagCompound);
		substanceStack.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("gasStrenght", strenght);
	}
	
	@Override
	public Packet getDescriptionPacket() 
	{
		NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, var1);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
	{
		readFromNBT(pkt.customParam1);
	}
	
}
