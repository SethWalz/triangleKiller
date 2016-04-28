import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Game extends Application {

    Random rnd = new Random();

    static Pane playfieldLayer;
    Pane scoreLayer;

    int s;
	Label score = new Label("Score: 0");

	static Player player;
	static Lazor lazor;
    static Image playerImage;
    Image enemyImage;
    static Image lazorImage;

    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();
    static List<Lazor> lazorList = new ArrayList<>();

    Text collisionText = new Text();
    boolean collision = false;

    Scene scene;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();



        root.getChildren().add( playfieldLayer);
        root.getChildren().addAll( scoreLayer,score);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene( scene);
        primaryStage.show();

        loadGame();

        createScoreLayer();
        createPlayers();

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                players.forEach(sprite -> sprite.processInput());

                // add random enemies
                spawnEnemies( true);

                // movement
                players.forEach(sprite -> sprite.move());
                enemies.forEach(sprite -> sprite.move());
				lazorList.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                players.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());
                lazorList.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

                // update score, health, etc
                updateScore();
            }

        };


        gameLoop.start();

    }

    private void loadGame() {
        playerImage = new Image( getClass().getResource("userTriangle.png").toExternalForm());
        enemyImage = new Image( getClass().getResource("enemyTriangle.png").toExternalForm());
        lazorImage = new Image( getClass().getResource("Lazor.png").toExternalForm());
    }

    private void createScoreLayer() {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);


    }
    private void createPlayers() {

        // player input
        Input input = new Input( scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        player = new Player(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, input);

        // register player
        players.add( player);

    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }


        // image
        Image image = enemyImage;

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0, 0, speed, 0, 1,1);
        // manage sprite
        enemies.add(enemy);

    }

    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while( iter.hasNext()) {
            SpriteBase sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }

    private void checkCollisions() {

        collision = false;

        for( Player player: players) {
            for( Enemy enemy: enemies) {

            	try{
            		if( lazor.collidesWith(enemy)) {
            	        collision = true;
            	        s++;
            	        score.setText("Score: " + s);
            	        enemy.remove();
            	        lazor.remove();
            	    }
				}catch(Exception e){}

				if( player.collidesWith(enemy)) {
				   collision = true;
					s=0;
           	        score.setText("Score: " + s);
           	        enemy.remove();
            	}

            }
        }
    }

    private void updateScore() {
        if( collision) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void fireLazor(){
	    double x = player.getX() + playerImage.getWidth()/2 - 4;
	    double y = player.getY();

  		double speed = 8; //rnd.nextDouble() * 9.0 + 10.0;

        lazor = new Lazor(playfieldLayer, lazorImage, x, y, 0, 0, -speed, 0, 1,1);

		lazorList.add(lazor);
	}

}
