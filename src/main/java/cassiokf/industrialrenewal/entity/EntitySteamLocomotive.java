package cassiokf.industrialrenewal.entity;

import cassiokf.industrialrenewal.item.ModItems;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySteamLocomotive extends EntityMinecartFurnace {

    public EntitySteamLocomotive(World worldIn) {
        super(worldIn);
        this.setSize(1.4F, 1.2F);
    }

    public EntitySteamLocomotive(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void killMinecart(DamageSource source) {
        super.killMinecart(source);

        if (!source.isExplosion() && this.world.getGameRules().getBoolean("doEntityDrops")) {
            this.entityDropItem(new ItemStack(ModItems.steamLocomotive, 1), 0.0F);
        }
    }

    @Override
    public ItemStack getCartItem() {
        return new ItemStack(ModItems.steamLocomotive);
    }

}
