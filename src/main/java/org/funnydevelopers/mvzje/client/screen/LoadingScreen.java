package org.funnydevelopers.mvzje.client.screen;

import org.funnydevelopers.mvzje.client.MvZ;
import org.funnydevelopers.mvzje.client.render.TextRenderer;
import org.funnydevelopers.mvzje.client.screen.widget.Button;
import org.overrun.swgl.core.gl.GLDrawMode;
import org.overrun.swgl.core.gui.font.SwglEasyFont;

import static org.funnydevelopers.mvzje.client.Language.translate;
import static org.funnydevelopers.mvzje.client.Textures.*;
import static org.overrun.swgl.core.gl.GLStateMgr.bindTexture2D;
import static org.overrun.swgl.core.gl.ims.GLImmeMode.*;

/**
 * 开场动画,类似Minecraft启动时的Mojang Studio标志
 *
 * @author crazy-piggy, squid233
 * @since 0.1.0
 */
public class LoadingScreen extends Screen {
    private int progress;
    private int daisy;
    private boolean initialized;

    @Override
    public void init() {
        super.init();
        if (!initialized) {
            progress = 0;
            initialized = true;
        }
        addButton(new Button(width / 2 - 128,
            height - (24 + 32),
            256,
            32,
            System.out::println) {
            @Override
            public void render(double delta) {
                super.render(delta);
                String str;
                if (progress < 100) {
                    str = translate("main.loading");
                } else {
                    str = translate("main.start");
                }
                lglPushMatrix();
                lglScale(2.0f);
                TextRenderer.drawText(
                    x + (width - SwglEasyFont.getWidth(str) * 2.0f) / 2f,
                    y + (height - SwglEasyFont.getHeight(str) * 2.0f) / 2f,
                    str);
                lglPopMatrix();
            }

            @Override
            public void hover(double delta) {
                if (progress >= 100) {
                    lglColor(1, 1, 1, 0.5f);
                    lglBegin(GLDrawMode.QUADS);
                    lglVertex(x, y, 0);
                    lglEmit();
                    lglVertex(x, y + height, 0);
                    lglEmit();
                    lglVertex(x + width, y + height, 0);
                    lglEmit();
                    lglVertex(x + width, y, 0);
                    lglEmit();
                    lglEnd();
                }
            }
        });
    }

    @Override
    public void mousePress(int mouseX, int mouseY, int button) {
        super.mousePress(mouseX, mouseY, button);
    }

    @Override
    public void tick() {
        if (progress < 100) {
            progress += 2;
        } else {
            if (daisy < 16) {
                daisy += 2;
            } else if (daisy > 16) {
                daisy = 16;
            }
        }
    }

    @Override
    public void render(double delta) {
        TEXTURE_LOADING_BG.bind();
        lglColor(1, 1, 1);
        lglSetTexCoordArrayState(true);
        lglBegin(GLDrawMode.QUADS);
        lglTexCoord(0, 0);
        lglVertex(0, 0, 0);
        lglEmit();
        lglTexCoord(0, 1);
        lglVertex(0, height, 0);
        lglEmit();
        lglTexCoord(1, 1);
        lglVertex(width, height, 0);
        lglEmit();
        lglTexCoord(1, 0);
        lglVertex(width, 0, 0);
        lglEmit();
        lglEnd();

        lglPushMatrix();
        lglTranslate(width / 2f - 128, height - 24, 0);
        lglColor(1, 1, 1);
        TEXTURE_PROGRESS_BAR.bind();

        lglBegin(GLDrawMode.QUADS);

        lglTexCoord(0, 0.5f);
        lglVertex(0, -32, 0);
        lglEmit();
        lglTexCoord(0, 1);
        lglVertex(0, 0, 0);
        lglEmit();
        lglTexCoord(8.0f, 1);
        lglVertex(256, 0, 0);
        lglEmit();
        lglTexCoord(8.0f, 0.5f);
        lglVertex(256, -32, 0);
        lglEmit();

        float unmlProgress = progress * 2.56f;
        float u = unmlProgress / 32.0f;
        lglTexCoord(0, 0);
        lglVertex(0, -32, 0);
        lglEmit();
        lglTexCoord(0, 0.5f);
        lglVertex(0, 0, 0);
        lglEmit();
        lglTexCoord(u, 0.5f);
        lglVertex(unmlProgress, 0, 0);
        lglEmit();
        lglTexCoord(u, 0);
        lglVertex(unmlProgress, -32, 0);
        lglEmit();

        lglEnd();

        bindTexture2D(0);

        if (progress >= 100) {
            lglPushMatrix();
            lglTranslate(256 - 16, -32, 0);
            TEXTURE_DAISY.bind();
            float v = 1.0f - daisy / 16.0f;
            lglBegin(GLDrawMode.QUADS);
            lglTexCoord(0, v);
            lglVertex(0, -daisy, 0);
            lglEmit();
            lglTexCoord(0, 1);
            lglVertex(0, 0, 0);
            lglEmit();
            lglTexCoord(1, 1);
            lglVertex(16, 0, 0);
            lglEmit();
            lglTexCoord(1, v);
            lglVertex(16, -daisy, 0);
            lglEmit();
            lglEnd();
            bindTexture2D(0);
            lglPopMatrix();
        }
        lglSetTexCoordArrayState(false);

        lglPopMatrix();

        lglPushMatrix();
        var text = "v" + MvZ.VERSION;
        lglScale(2.0f);
        TextRenderer.drawText(width - SwglEasyFont.getWidth(text) * 2.0f, height - SwglEasyFont.getHeight(text) * 2.0f, text);
        lglPopMatrix();

        super.render(delta);
    }
}
