package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.mixin.TitleAccessor;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.galgan.skyupplus.util.Sound;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;

import java.util.Set;

public class Warnings {

    private static boolean playPlugWarning = false;

    public static void handleWarnings() {

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (!Config.get().togglePlugWarning || !ServerRestrictor.isAllowed() || Cooldown.isActivePlug) {
                return ActionResult.PASS;
            }

            Block block = world.getBlockState(pos).getBlock();

            playPlugWarning = crops.contains(block);

            return ActionResult.PASS;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!ServerRestrictor.isAllowed()) return;

            boolean plugWarning = Config.get().togglePlugWarning;
            boolean toolWarning = Config.get().toggleToolDurabilityWarning;
            boolean armorWarning = Config.get().toggleArmorDurabilityWarning;

            if (!toolWarning && !armorWarning && !plugWarning) return;

            if (client.player == null || client.inGameHud == null) return;

            TitleAccessor title = (TitleAccessor) client.inGameHud;
            boolean titleActive = title.getTitle() != null || title.getSubtitle() != null;

            if (!titleActive && plugWarning && playPlugWarning) {
                client.inGameHud.setTitle(Text.literal("UWAGA!").formatted(Formatting.RED, Formatting.BOLD));
                client.inGameHud.setSubtitle(Text.literal("Pług nie jest aktywny!"));
                client.inGameHud.setTitleTicks(5, 10, 5);
                Sound.playSound(client, warningSound(), SoundCategory.MASTER, 1.0f);
                playPlugWarning = false;
                titleActive = true;
            }

            if (!titleActive && toolWarning) {
                int toolThreshold = Config.get().toolDurabilityThreshold;

                ItemStack mainHand = client.player.getEquippedStack(EquipmentSlot.MAINHAND);
                ItemStack offHand = client.player.getEquippedStack(EquipmentSlot.OFFHAND);

                boolean toolLow = compareDurability(mainHand, toolThreshold) || compareDurability(offHand, toolThreshold);

                if(toolLow) {
                    client.inGameHud.setTitle(Text.literal("UWAGA!").formatted(Formatting.RED, Formatting.BOLD));
                    client.inGameHud.setSubtitle(Text.literal("Niskie użycia narzędzia!"));
                    client.inGameHud.setTitleTicks(5, 10, 5);
                    Sound.playSound(client, warningSound(), SoundCategory.MASTER, 1.0f);
                    titleActive = true;
                }
            }

            if (!titleActive && armorWarning) {
                int armorThreshold = Config.get().armorDurabilityThreshold;

                ItemStack head = client.player.getEquippedStack(EquipmentSlot.HEAD);
                ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
                ItemStack legs = client.player.getEquippedStack(EquipmentSlot.LEGS);
                ItemStack feet = client.player.getEquippedStack(EquipmentSlot.FEET);

                boolean armorLow = compareDurability(head, armorThreshold) || compareDurability(chest, armorThreshold) || compareDurability(legs, armorThreshold) || compareDurability(feet, armorThreshold);

                if(armorLow) {
                    client.inGameHud.setTitle(Text.literal("UWAGA!").formatted(Formatting.RED, Formatting.BOLD));
                    client.inGameHud.setSubtitle(Text.literal("Niskie użycia zbroi!"));
                    client.inGameHud.setTitleTicks(5, 10, 5);
                    Sound.playSound(client, warningSound(), SoundCategory.MASTER, 1.0f);
                }
            }
        });
    }

    private static final Set<Block> crops = Set.of(
            net.minecraft.block.Blocks.WHEAT,
            net.minecraft.block.Blocks.BEETROOTS,
            net.minecraft.block.Blocks.CARROTS,
            net.minecraft.block.Blocks.POTATOES,
            net.minecraft.block.Blocks.NETHER_WART
    );

    private static boolean compareDurability(ItemStack stack, int durabilityThreshold) {
        if (stack.isEmpty() || !stack.isDamageable()) return false;

        int percent = ((stack.getMaxDamage() - stack.getDamage()) * 100) / stack.getMaxDamage();

        return percent <= durabilityThreshold;
    }

    private static SoundEvent warningSound() {
        return switch (Config.get().warningSound) {
            case PLING -> SoundEvents.BLOCK_NOTE_BLOCK_PLING.value();
            case TUBE -> SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO.value();
            case BASS -> SoundEvents.BLOCK_NOTE_BLOCK_BASS.value();
            case ANVIL -> SoundEvents.BLOCK_ANVIL_LAND;
        };
    }
}
