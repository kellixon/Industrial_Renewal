package cassiokf.industrialrenewal.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class ValveUtils extends FluidTank {

    public ValveUtils(final TileEntity tileEntity, final int capacity) {
        super(capacity);
        tile = tileEntity;
    }

    public ValveUtils(final TileEntity tileEntity, final FluidStack stack, final int capacity) {
        super(stack, capacity);
        tile = tileEntity;
    }

    public ValveUtils(final TileEntity tileEntity, final Fluid fluid, final int amount, final int capacity) {
        super(fluid, amount, capacity);
        tile = tileEntity;
    }

}