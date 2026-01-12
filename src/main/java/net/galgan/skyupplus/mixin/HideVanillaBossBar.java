package net.galgan.skyupplus.mixin;

import net.galgan.skyupplus.config.Config;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.entity.boss.BossBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Mixin(BossBarHud.class)
public abstract class HideVanillaBossBar {

    @Unique
    private static final String[] ABILITY_NAMES = {
            "Pług",
            "Wiertło",
            "Rozbiórka",
            "Piła",
            "Sieci",
            "Nawałnica",
            "Eskalacja"
    };

    @Unique
    private static boolean shouldHide(BossBar bar) {
        if (!Config.get().abilityCooldown) return false;

        String name = bar.getName().getString();
        for (String prefix : ABILITY_NAMES) {
            if (name.startsWith(prefix)) return true;
        }
        return false;
    }

    @Redirect(
            method = "render(Lnet/minecraft/client/gui/DrawContext;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;values()Ljava/util/Collection;"),
            require = 1
    )

    private Collection<?> filterValues(Map<?, ?> map) {
        var list = new ArrayList<>(map.values());
        list.removeIf(v -> v instanceof BossBar bar && shouldHide(bar));
        return list;
    }
}

