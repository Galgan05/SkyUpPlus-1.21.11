package net.galgan.skyupplus.util;

import net.galgan.skyupplus.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.List;

public class DrawUtil {

    public static int getHeight(TextRenderer tr, List<Text> body) {

        return (tr.fontHeight + 2) * body.size();
    }

    public static int getWidth(TextRenderer tr, List<Text> body) {

        int width = 0;

        for(Text line : body) {
            if (tr.getWidth(line) > width) {
                width = tr.getWidth(line);
            }
        }
        return width;
    }

    public static void background(DrawContext ctx, int x, int y, int w, int h) {

        //Background
        ctx.fill(
                x + 1,
                y + 1,
                x + w + 4,
                y + h + 2,
                0x80000000
        );
    }

    public static void border(DrawContext ctx, int x, int y, int w, int h, int color) {

        //Shadow
        ctx.drawHorizontalLine(x + 1, x + w + 5, y + 1, (color & 0xFCFCFC) >> 2 | (color & 0xFF000000));
        ctx.drawHorizontalLine(x + 1, x + w + 5, y + h + 3, (color & 0xFCFCFC) >> 2 | (color & 0xFF000000));
        ctx.drawVerticalLine(x + 1, y + 1, y + h + 3, (color & 0xFCFCFC) >> 2 | (color & 0xFF000000));
        ctx.drawVerticalLine(x + w + 5, y + 1, y + h + 3, (color & 0xFCFCFC) >> 2 | (color & 0xFF000000));

        //Main
        ctx.drawHorizontalLine(x, x + w + 4, y, color);
        ctx.drawHorizontalLine(x, x + w + 4, y + h +2, color);
        ctx.drawVerticalLine(x, y, y + h + 2, color);
        ctx.drawVerticalLine(x + w + 4, y, y + h + 2, color);
    }

    public static void text(DrawContext ctx, TextRenderer tr, List<Text> body, int x, int y) {

        //Move the text to add space
        x+=3;
        y+=3;

        //Draw the text
        for(Text line : body) {
            ctx.drawTextWithShadow(tr, line, x, y,0xFFFFFFFF);
            y += tr.fontHeight + 2;
        }
    }

    public static void drawWidget(DrawContext ctx, int color, Config.DisplayLocation displayLocation, List<Text> body) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;

        //Get the dimensions of the text
        int w = getWidth(tr, body);
        int h = getHeight(tr, body);

        //Get the starting position
        int x = switch (displayLocation) {
            case TOP_LEFT, MIDDLE_LEFT, BOTTOM_LEFT -> 2;
            case TOP_RIGHT, BOTTOM_RIGHT, MIDDLE_RIGHT -> ctx.getScaledWindowWidth() - w - 8;
        };

        int y = switch (displayLocation) {
            case TOP_LEFT, TOP_RIGHT -> 2;
            case MIDDLE_LEFT, MIDDLE_RIGHT -> (ctx.getScaledWindowHeight() - h - 4) / 2;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> ctx.getScaledWindowHeight() - h - 6;
        };

        //Draw the border and text
        background(ctx, x, y, w, h);
        border(ctx, x, y, w, h, color);
        text(ctx, tr, body, x, y);
    }
}
