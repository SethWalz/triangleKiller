import java.util.BitSet;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.input.KeyEvent;


public class Input {

    /**
     * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is released.
     */
    private BitSet keyboardBitSet = new BitSet();

	//Setting inputs
    private KeyCode upKey = KeyCode.W;
    private KeyCode downKey = KeyCode.S;
    private KeyCode leftKey = KeyCode.A;
    private KeyCode rightKey = KeyCode.D;
    private KeyCode primaryWeaponKey = KeyCode.SPACE;
	boolean fire = false;

    Scene scene;

    public Input( Scene scene) {
        this.scene = scene;
    }

    public void addListeners() {

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }


    /**
     * "Key Pressed" handler for all input events: register pressed key in the bitset
     */
    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

			if(event.getCode() == KeyCode.SPACE){
			//	System.out.println(event);
				fire = true;
				isFirePrimaryWeapon();
			}

            // register key down
            keyboardBitSet.set(event.getCode().ordinal(), true);

        }
    };

    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     */
    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {

			if(event.getCode() == KeyCode.SPACE){
			//	System.out.println(event);
				fire = false;
				isFirePrimaryWeapon();
			}

            // register key up
            keyboardBitSet.set(event.getCode().ordinal(), false);

        }
    };

    // -------------------------------------------------
    // Evaluate bitset of pressed keys and return the player input.
    // -------------------------------------------------

    public boolean isMoveUp() {
        return keyboardBitSet.get( upKey.ordinal()) && !keyboardBitSet.get( downKey.ordinal());
    }

    public boolean isMoveDown() {
        return keyboardBitSet.get( downKey.ordinal()) && !keyboardBitSet.get( upKey.ordinal());
    }

    public boolean isMoveLeft() {
        return keyboardBitSet.get( leftKey.ordinal()) && !keyboardBitSet.get( rightKey.ordinal());
    }

    public boolean isMoveRight() {
        return keyboardBitSet.get( rightKey.ordinal()) && !keyboardBitSet.get( leftKey.ordinal());
    }

    public boolean isFirePrimaryWeapon() {
        return fire;
    }

}