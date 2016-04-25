/*
Toni Malbasic
David Robinson
Seth Walz

Adv_Java Game | Triangle Killer

*/

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.image.Image;

public class client extends Application {

	public static double x = 10.0, y = 0.0;

	public Image usersTriangle;
    public Image enemyImage;


    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

	public static void main(String[] args) throws Exception
	{
		launch(args);
	}



	@Override
	public void start(Stage primaryStage) throws Exception{


		loadGame();

		Pane playfieldLayer = new Pane();
		Scene scene = new Scene(playfieldLayer, 1700, 900);


		 AnimationTimer gameLoop = new AnimationTimer() {

		            @Override
		            public void handle(long now) {

		                // player input
		                players.forEach(sprite -> sprite.processInput());

		                // movement
		                players.forEach(sprite -> sprite.move());
		                enemies.forEach(sprite -> sprite.move());


		                // update sprites in scene
		                players.forEach(sprite -> sprite.updateUI());
		                enemies.forEach(sprite -> sprite.updateUI());

		                // check if sprite can be removed
		                enemies.forEach(sprite -> sprite.checkRemovability());

		            }

		        };
        gameLoop.start();


		//get points of mouse movement
		scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
            }
		});

		//Fire Weapon Towards mouse
		EventHandler fire = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
				   // Fire weapon systems. On Windows left mouse button
				} else if (event.getButton() == MouseButton.SECONDARY) {
				   // Navigate ship thrust. On Windows right mouse button
				}
			}
		};
		primaryStage.getScene().setOnMousePressed(fire);
		primaryStage.setTitle("Triangle Killer"); // Set the stage title
		primaryStage.setResizable(false);
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show();

	}

	//
	public void move(double x, double y, Pane pane, Polygon usersTriangle, KeyEvent event){

		usersTriangle.setLayoutX(usersTriangle.getLayoutX()+x);
		usersTriangle.setLayoutY(usersTriangle.getLayoutY()+y);

	}

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

	private void loadGame() {
		usersTriangle = new Image( getClass().getResource("userTriangle.png").toExternalForm());
	    enemyImage = new Image( getClass().getResource("enemyTriangle.png").toExternalForm());
    }
}




