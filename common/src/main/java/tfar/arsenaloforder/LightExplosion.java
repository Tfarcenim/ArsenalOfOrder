package tfar.arsenaloforder;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightExplosion extends Explosion {
    public LightExplosion(Level level, @Nullable Entity source, double toBlowX, double toBlowY, double toBlowZ, float radius, List<BlockPos> positions) {
        super(level, source, toBlowX, toBlowY, toBlowZ, radius, positions);
    }

    public LightExplosion(Level level, @Nullable Entity source, double toBlowX, double toBlowY, double toBlowZ, float radius, boolean fire, BlockInteraction blockInteraction, List<BlockPos> positions) {
        super(level, source, toBlowX, toBlowY, toBlowZ, radius, fire, blockInteraction, positions);
    }

    public LightExplosion(Level level, @Nullable Entity source, double toBlowX, double toBlowY, double toBlowZ, float radius, boolean fire, BlockInteraction blockInteraction) {
        super(level, source, toBlowX, toBlowY, toBlowZ, radius, fire, blockInteraction);
    }

    public LightExplosion(Level level, @Nullable Entity source, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator damageCalculator, double toBlowX, double toBlowY, double toBlowZ, float radius, boolean fire, BlockInteraction blockInteraction) {
        super(level, source, damageSource, damageCalculator, toBlowX, toBlowY, toBlowZ, radius, fire, blockInteraction);
    }


    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    @Override
    public void finalizeExplosion(boolean spawnParticles) {
        if (this.level.isClientSide) {
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean flag = this.interactsWithBlocks();
        if (spawnParticles) {
            if (!(this.radius < 2.0F) && flag) {
                this.level.addParticle(ParticleTypes.FIREWORK, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            } else {
                this.level.addParticle(ParticleTypes.FIREWORK, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            }
        }

        if (flag) {
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
            boolean flag1 = this.getIndirectSourceEntity() instanceof Player;
            Util.shuffle((ObjectArrayList<BlockPos>)this.getToBlow(), this.level.random);

            for(BlockPos blockpos : this.getToBlow()) {
                BlockState blockstate = this.level.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (!blockstate.isAir()) {
                    BlockPos blockpos1 = blockpos.immutable();
                    this.level.getProfiler().push("explosion_blocks");
                    if (block.dropFromExplosion(this)) {
                        Level $$9 = this.level;
                        if ($$9 instanceof ServerLevel) {
                            ServerLevel serverlevel = (ServerLevel)$$9;
                            BlockEntity blockentity = blockstate.hasBlockEntity() ? this.level.getBlockEntity(blockpos) : null;
                            LootParams.Builder lootparams$builder = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos))
                                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                    .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
                            if (this.blockInteraction == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
                                lootparams$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                            }

                            blockstate.spawnAfterBreak(serverlevel, blockpos, ItemStack.EMPTY, flag1);
                            blockstate.getDrops(lootparams$builder).forEach((p_46074_) -> {
                                addBlockDrops(objectarraylist, p_46074_, blockpos1);
                            });
                        }
                    }

                    this.level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 3);
                    block.wasExploded(this.level, blockpos, this);
                    this.level.getProfiler().pop();
                }
            }

            for(Pair<ItemStack, BlockPos> pair : objectarraylist) {
                Block.popResource(this.level, pair.getSecond(), pair.getFirst());
            }
        }

        if (this.fire) {
            for(BlockPos blockpos2 : this.getToBlow()) {
                if (this.random.nextInt(3) == 0 && this.level.getBlockState(blockpos2).isAir() && this.level.getBlockState(blockpos2.below())
                        .isSolidRender(this.level, blockpos2.below())) {
                    this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
                }
            }
        }

    }
}
