import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

public class Lazor extends SpriteBase {

    public Lazor(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {

        super(layer, image, x, y, r, dx, dy, dr, health, damage);
    }


    @Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0 || Double.compare( getY(), Settings.SCENE_HEIGHT) < 0) {
            setRemovable(true);
        }


    }
}