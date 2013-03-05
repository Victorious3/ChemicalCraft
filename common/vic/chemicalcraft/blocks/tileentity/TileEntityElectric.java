package vic.chemicalcraft.blocks.tileentity;

import ic2.api.Direction;
import ic2.api.IEnergyStorage;
import ic2.api.IWrenchable;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityElectric extends TileEntity implements IEnergySink, IEnergySource, IWrenchable, IEnergyStorage 
{
	public int powerLevel = 0;
	public int maxPowerLevel = 0;
	public int maxSafeInput = 0;
	public int maxEnergyOutput = 0;
	public int energyDemand = 0;
	public int energyOutput = 0;
		
	public short orientation = 2;
	
	boolean isAddedToEnergyNet = false;	
	
	@Override
	public void updateEntity() {
		if(!isAddedToEnergyNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			isAddedToEnergyNet = true;
		}
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) 
	{
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) 
	{
		super.readFromNBT(par1nbtTagCompound);
		powerLevel = par1nbtTagCompound.getInteger("powerLevel");
		this.orientation = (short)par1nbtTagCompound.getInteger("orientation");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) 
	{
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("powerLevel", powerLevel);
		par1nbtTagCompound.setInteger("orientation", this.orientation);
	}

	@Override
	public boolean isAddedToEnergyNet() 
	{
		return isAddedToEnergyNet;
	}

	@Override
	public void invalidate() 
	{
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		super.invalidate();
	}

	@Override
	public int demandsEnergy() 
	{
		return energyDemand;
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		
		if(this.powerLevel < this.maxPowerLevel)
		{
			if(this.powerLevel + amount > this.maxPowerLevel)
			{
				int back = (this.powerLevel + amount) - this.maxPowerLevel; 
				this.addEnergy(amount - back);
				this.energyDemand = 0;
				return back;
			}		
			this.addEnergy(amount);
		}
		else return amount;
		
		return 0;
	}

	@Override
	public int getMaxSafeInput()
	{
		return maxSafeInput;
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

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) 
	{
		return false;
	}

	@Override
	public int getStored() {		
		return powerLevel;
	}

	@Override
	public void setStored(int energy) 
	{
		if(energy <= this.maxPowerLevel)
		{
			powerLevel = energy;
		}		
	}

	@Override
	public int addEnergy(int amount) 
	{
		this.powerLevel += amount;
		return this.getStored();
	}

	@Override
	public int getCapacity() 
	{
		return maxPowerLevel;
	}

	@Override
	public int getOutput() 
	{
		return energyOutput;
	}

	@Override
	public boolean isTeleporterCompatible(Direction side) 
	{
		return false;
	}

	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) 
	{
		 return (side != this.orientation);
	}

	@Override
	public short getFacing() 
	{
		return orientation;
	}

	@Override
	public void setFacing(short facing) 
	{		
		this.orientation = facing;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer entityPlayer) 
	{
		return true;
	}

	@Override
	public float getWrenchDropRate() 
	{
		return 0.8F;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) 
	{
		return new ItemStack(this.blockType, 1);
	}

	@Override
	public int getMaxEnergyOutput() 
	{
		return maxEnergyOutput;
	}
}
