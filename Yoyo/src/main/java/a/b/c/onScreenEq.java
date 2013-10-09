package a.b.c;

/**
 * Created by Colin_000 on 10/6/13.
 *
 *  records where on screen an equations is drawn, this is only the location of Eq.nam not the location and it sub equations
 */
public class OnScreenEq {
    float x;
    float y;
    float width;
    float height;

    // pro tip: Alt+Enter can be used to auto generate these
    public OnScreenEq(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
