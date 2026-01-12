package net.galgan.skyupplus.mixin;

import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HandledScreen.class)
public abstract class HandledScreenDraw {

    @Inject(
            method = "drawSlot(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/screen/slot/Slot;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItem(Lnet/minecraft/item/ItemStack;III)V"
            )
    )

    private void drawStorageBackground(DrawContext ctx, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        if(!Config.get().showFullStorages || !ServerRestrictor.isAllowed()) return;

        String title = ((Screen)(Object)this).getTitle().getString();
        if (!title.equals("\uE001\uE041\uE002Kontroler magazynów")) return;

        ItemStack stack = slot.getStack();
        if (stack.isEmpty() || !slot.isEnabled()) return;

        Text name = stack.getName();
        List<Text> siblings = name.getSiblings();

        if(siblings.size() < 4) return;
        if(!siblings.getFirst().getString().equals("» ")) return;

        TextColor color = siblings.size() > 1 ? siblings.get(1).getStyle().getColor() : null;
        if(color == null) return;

        int x = slot.x;
        int y = slot.y;

        if(color.getName().equals("red")) {
            ctx.fill(x, y, x + 16, y + 16, 0xFFFF5555);

        } else if(color.getName().equals("gold")) {
            ctx.fill(x, y, x + 16, y + 16, 0xFFFFAA00);
        }
    }
}