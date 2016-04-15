import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class TrianlgeKiller extends Application {
	public static void main(String[] args) throws Exception
	{
		launch(args);
	}
	public static double x = 10.0, y = 0.0;
	public static double[] userTrianglePoints = new double[]{
		    900.0, 400.0,
		    910.0, 380.0,
		    890.0, 380.0 };
	@Override
	public void start(Stage primaryStage) throws Exception{
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 1700, 900);
		Polygon usersTriangle = new Polygon(userTrianglePoints);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if (e.getCode().equals(KeyCode.RIGHT)){
					move(10.0, 0.0, pane, usersTriangle, e);
				
				} else if(e.getCode().equals(KeyCode.LEFT)){
					move(-10.0, 0.0, pane, usersTriangle, e);
				
				} else if(e.getCode().equals(KeyCode.UP)){
					move(0.0, -10.0, pane, usersTriangle, e);
				
				} else if(e.getCode().equals(KeyCode.DOWN))	{
					move(0.0, 10.0, pane, usersTriangle, e);
				}
            }
		});

		scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
            }
		});
		
		pane.getChildren().addAll(usersTriangle);
		primaryStage.setTitle("Triangle Killer"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show();

	}

public void move(double x, double y, Pane pane, Polygon usersTriangle, KeyEvent event){

	usersTriangle.setLayoutX(usersTriangle.getLayoutX()+x);
	usersTriangle.setLayoutY(usersTriangle.getLayoutY()+y);
		
	}

}




