/*
Toni Malbasic
David Robinson
Seth Walz

Adv_Java Game | Triangle Killer

*/

import java.util.LinkedList;
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
import javafx.scene.paint.Color;

public class client extends Application {

	public static double x = 10.0, y = 0.0;

	public static void main(String[] args) throws Exception
	{
		launch(args);
	}

	// Start location of the triangle
	public static double[] userTrianglePoints = new double[]{
		    900.0, 400.0,
		    910.0, 380.0,
		    890.0, 380.0
	};

	final double triangleHSpeed = 200 ; // pixels per second
	final double triangleVSpeed = 200 ;
	final double minX = -895 ;
	final double maxX = 800 ;
	final double minY = -380 ;
	final double maxY = 500 ;
	Pane pane = new Pane();
	Scene scene = new Scene(pane, 1700, 900);
	Polygon usersTriangle = new Polygon(userTrianglePoints);

	@Override
	public void start(Stage primaryStage) throws Exception{



		//stack for getting movements and placing into stack
		final LinkedList<KeyCode> keyStack = new LinkedList<>();
		scene.setOnKeyPressed(event -> {
			KeyCode code = event.getCode();
			if (! keyStack.contains(code)) {
				keyStack.push(code);
			}
		});
		// deleting built up keystacks
        scene.setOnKeyReleased(event ->
            keyStack.remove(event.getCode()));

		//getting key strokes for movement and applying them
		final LongProperty lastUpdateTime = new SimpleLongProperty();
		final AnimationTimer usersTriangleAnimation = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
            if (! keyStack.isEmpty() && lastUpdateTime.get() > 0) {
              final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0 ;
              double deltaX = 0 ;
              double deltaY = 0 ;
              switch(keyStack.peek()) {
              case W:
                  deltaY = -triangleVSpeed * elapsedSeconds;
                  break ;
              case S:
                  deltaY = triangleVSpeed * elapsedSeconds ;
                  break ;
              case A:
                  deltaX = -triangleHSpeed * elapsedSeconds ;
                  break ;
              case D:
                  deltaX = triangleHSpeed * elapsedSeconds ;
              default:
                  break ;
              }
              double oldX = usersTriangle.getTranslateX() ;
              double oldY = usersTriangle.getTranslateY() ;
              usersTriangle.setTranslateX(clamp(oldX + deltaX, minX, maxX));
              usersTriangle.setTranslateY(clamp(oldY + deltaY, minY, maxY));
            }
            lastUpdateTime.set(timestamp);
          }
        };

		//get points of mouse movement
		scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
            }
		});



		// addding and starting stage
		setupInput(primaryStage);
		usersTriangleAnimation.start();
		pane.getChildren().addAll(usersTriangle);
		primaryStage.setTitle("Triangle Killer"); // Set the stage title
		primaryStage.setResizable(false);
		primaryStage.setScene(scene); // Place the scene in the stage
		setupInput(primaryStage);
		primaryStage.show();

	}
	private void setupInput(Stage primaryStage) {
		//Fire Weapon Towards mouse
		EventHandler fire = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
				   usersTriangle.setFill(Color.RED);
				} else if (event.getButton() == MouseButton.SECONDARY) {
				   System.out.println("leftclick");
				}
			}
		};
		primaryStage.getScene().setOnMousePressed(fire);
	};

	//
	public void move(double x, double y, Pane pane, Polygon usersTriangle, KeyEvent event){

		usersTriangle.setLayoutX(usersTriangle.getLayoutX()+x);
		usersTriangle.setLayoutY(usersTriangle.getLayoutY()+y);

	}

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }


}




