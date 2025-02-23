package me.bigteddy98.bannerboard.util;

import me.bigteddy98.bannerboard.Main;
import me.bigteddy98.bannerboard.SkinRequest;
import me.bigteddy98.bannerboard.api.SkinType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class SkinCache {

    // HEAD_3D("3DHEAD"), HEAD_ONLY("HEAD"), ENTIRE_SKIN("SKIN");

    private Map<SkinType, SkinRequest> typeLinks;

    public SkinCache(String server) {

        typeLinks = Map.of(SkinType.fromName("HEAD"), new SkinRequest(server + "fullskin-%NAME%-640-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-1") {

                    @Override
                    public BufferedImage pull(BufferedImage image) {
                        // cut it%%
                        image = image.getSubimage(250, 312, 139, 139); // asp ratio 1:1
                        return resize(image, 128, 128);
                    }
                }, SkinType.fromName("3DHEAD"), new SkinRequest(server + "fullskin-%NAME%-640-344-39-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-1") {

                    @Override
                    public BufferedImage pull(BufferedImage image) {
                        // cut it
                        image = image.getSubimage(222, 282, 202, 202); // asp ratio 1:1
                        // resize to 128x128
                        return resize(image, 128, 128);
                    }
                },

                // "+server+"fullskin-sander2798-640-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0
                SkinType.fromName("SKIN"), new SkinRequest(server + "fullskin-%NAME%-640-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0") {

                    @Override
                    public BufferedImage pull(BufferedImage image) {
                        // cut it
                        return image.getSubimage(150, 95, 305, 490);
                    }
                });
    }

    private static BufferedImage resize(BufferedImage img, int w, int h) {
        BufferedImage d = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = d.createGraphics();
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return d;
    }

    public BufferedImage getSkin(String name, SkinType type) throws IOException {
        SkinRequest r = this.typeLinks.get(type);
        return Main.getInstance().fetchImage(r.getLink().replace("%NAME%", name));
    }
}
