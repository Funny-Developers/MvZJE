package org.funnydevelopers.mvzje.client.render;

import org.overrun.swgl.core.gl.GLDrawMode;
import org.overrun.swgl.core.gl.GLStateMgr;
import org.overrun.swgl.core.gui.font.SwglEasyFont;

import java.util.HashMap;
import java.util.Map;

import static org.overrun.swgl.core.gl.ims.GLImmeMode.*;
import static org.overrun.swgl.core.gl.ims.GLLists.*;

/**
 * @author squid233
 * @since 0.1.0
 */
public class TextRenderer {
    private static final Map<String, Integer> textLists = new HashMap<>();

    public static void drawText(float x, float y, String text) {
        if (!textLists.containsKey(text)) {
            var desc = SwglEasyFont.createTextDesc(0, 0, text);
            float invTexSz = 1.0f / SwglEasyFont.getTextureSize();
            int lst = lglGenList();
            lglNewList(lst);
            lglBegin(GLDrawMode.QUADS);
            lglColor(1, 1, 1, 1);
            for (int i = 0, c = desc.getLength(); i < c; i++) {
                var xy0 = desc.getXY0(i);
                var xy1 = desc.getXY1(i);
                var glyph = desc.getGlyph(i);
                lglTexCoord(glyph.u0() * invTexSz, glyph.v0() * invTexSz);
                lglVertex(xy0.x, xy0.y);
                lglEmit();
                lglTexCoord(glyph.u0() * invTexSz, glyph.v1() * invTexSz);
                lglVertex(xy0.x, xy1.y);
                lglEmit();
                lglTexCoord(glyph.u1() * invTexSz, glyph.v1() * invTexSz);
                lglVertex(xy1.x, xy1.y);
                lglEmit();
                lglTexCoord(glyph.u1() * invTexSz, glyph.v0() * invTexSz);
                lglVertex(xy1.x, xy0.y);
                lglEmit();
            }
            lglEnd();
            lglEndList();
            textLists.put(text, lst);
        }
        SwglEasyFont.bindTexture();
        GLStateMgr.enableTexture2D();
        lglSetTexCoordArrayState(true);
        lglPushMatrix();
        lglGetMatrixMode().translateLocal(x, y, 0);
        lglCallList(textLists.get(text));
        lglSetTexCoordArrayState(false);
        SwglEasyFont.unbindTexture();
        lglPopMatrix();
    }
}
