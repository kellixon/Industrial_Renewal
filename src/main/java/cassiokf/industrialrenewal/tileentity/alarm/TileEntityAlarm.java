package cassiokf.industrialrenewal.tileentity.alarm;

import cassiokf.industrialrenewal.IRSoundHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityAlarm extends TileEntity implements ITickable {

    private final long PERIOD = 1000L; // Adjust to suit sound timing
    private long lastTime = System.currentTimeMillis() - PERIOD;

    public TileEntityAlarm() {

    }

    private static boolean isPoweredWire(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.REDSTONE_WIRE && Blocks.REDSTONE_WIRE.getStrongPower(world.getBlockState(pos), world, pos, EnumFacing.DOWN) > 0;
    }

    @Override
    public void update() {
        long thisTime = System.currentTimeMillis();
        if ((thisTime - lastTime) >= PERIOD) {
            lastTime = thisTime;
            playThis();
        }
    }

    public boolean checkPowered() {
        boolean powered = world.isBlockPowered(this.getPos())
                || isPoweredWire(this.getWorld(), this.getPos().add(1, 0, 0))
                || isPoweredWire(this.getWorld(), this.getPos().add(-1, 0, 0))
                || isPoweredWire(this.getWorld(), this.getPos().add(0, 0, 1))
                || isPoweredWire(this.getWorld(), this.getPos().add(0, 0, -1));
        return powered;
    }

    public void playThis() {
        if (this.checkPowered()) {
            this.getWorld().playSound(null, this.getPos(), IRSoundHandler.TILEENTITY_ALARM, SoundCategory.BLOCKS, 4.0F, 1.0F);
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }
}
