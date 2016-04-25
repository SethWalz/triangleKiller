import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.input.*;



public class Menu extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		Pane menuPane = new Pane();
		Scene menuScene = new Scene(menuPane, 400, 400);
		Stage menuStage = new Stage();
		menuStage.setTitle("Menu");
		menuStage.setScene(menuScene);

		Button playButton = new Button("Play");
		playButton.relocate(150,100);
	    playButton.setStyle("-fx-font: 24 arial;");

		Button exitButton = new Button("Exit:");
		exitButton.relocate(150,200);
	    exitButton.setStyle("-fx-font: 24 arial;");

		menuPane.getChildren().addAll(playButton, exitButton);
		menuStage.setTitle("Main Menu");
		menuStage.show();

		Pane pane = new Pane();
		Scene scene = new Scene(pane, 1700, 800);
		primaryStage.setTitle("Mulario"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage

		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent e)
		    {
				primaryStage.show();
				menuStage.hide();
		    }
        });

		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent e)
		    {
		    	menuStage.hide();
		    	primaryStage.hide();
		    }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				if(e.getCode().equals(KeyCode.ESCAPE)){
					menuStage.show();
				}

			}
		});

	}



}

