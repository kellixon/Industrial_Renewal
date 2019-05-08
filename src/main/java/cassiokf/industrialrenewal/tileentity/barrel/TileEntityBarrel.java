package cassiokf.industrialrenewal.tileentity.barrel;

import cassiokf.industrialrenewal.network.NetworkHandler;
import cassiokf.industrialrenewal.network.PacketBarrel;
import cassiokf.industrialrenewal.network.PacketReturnBarrel;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class TileEntityBarrel extends TileFluidHandler
{

    public FluidTank tank = new FluidTank(32000)
    {
        @Override
        protected void onContentsChanged()
        {
            if (!world.isRemote)
            {
                NetworkHandler.INSTANCE.sendToAllAround(new PacketBarrel(TileEntityBarrel.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 16));
            }
            System.out.println("Changed");
            markDirty();
        }
    };

    @Override
    public void onLoad()
    {
        if (world.isRemote)
        {
            NetworkHandler.INSTANCE.sendToServer(new PacketReturnBarrel(this));
        }
    }

    public String GetChatQuantity()
    {
        if (this.tank.getFluid() != null)
        {
            return this.tank.getFluid().getLocalizedName() + ": " + this.tank.getFluidAmount() + " mB";
        } else
        {
            return "Empty";
        }
    }

    public void readTankFromNBT(NBTTagCompound tag)
    {
        if (tag.hasKey("Empty"))
        {
            tag.removeTag("Empty");
        }
        tank.readFromNBT(tag);
    }

    private void writeEntityTankToNBT(NBTTagCompound tag)
    {
        tank.writeToNBT(tag);
    }

    public NBTTagCompound GetTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeEntityTankToNBT(tag);
        return tag;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound tag = new NBTTagCompound();
        tank.writeToNBT(tag);
        compound.setTag("fluid", tag);

        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagCompound tag = compound.getCompoundTag("fluid");
        tank.readFromNBT(tag);

        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, @Nullable final EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }
}