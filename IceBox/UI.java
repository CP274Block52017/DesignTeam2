package controller;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Optional;
import javafx.util.Callback;

public class UI extends Application {

	private Text actionStatus;

	public static void main(String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("OmniPredictor");	
		
		Label label = new Label("Predict the DOW!");
		label.setFont(Font.font("Arial Black", FontWeight.BOLD, 36));
		HBox comment = new HBox();
		comment.setAlignment(Pos.CENTER);
		comment.getChildren().add(label);

		Button btn = new Button("Configure training");
		btn.setOnAction(new DialogButtonListener());
		HBox buttonHb = new HBox(10);
		buttonHb.setAlignment(Pos.CENTER);
		buttonHb.getChildren().addAll(btn);
		
		actionStatus = new Text();
		VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(comment, buttonHb, actionStatus);
	
		Scene scene = new Scene(vbox, 500, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private class DialogButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			displayDialog();
		}
	}
	
	private void displayDialog() {
	
		actionStatus.setText("");
		
		// Custom dialog
		Dialog<dateBounds> dialog = new Dialog<>();
		dialog.setTitle("OmniPredictor");
		dialog.setHeaderText("Enter starting bounds and  \n" +
			"ending bounds (YYYY-MM-DD)");
		dialog.setResizable(true);

		Label startDate = new Label("Start Date: ");
		Label endDate = new Label("End Date: ");
		TextField textStartDate = new TextField();
		TextField textEndDate = new TextField();
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 35, 20, 35));
		grid.add(startDate, 1, 1);
		grid.add(textStartDate, 2, 1);
		grid.add(endDate, 1, 2); 
		grid.add(textEndDate, 2, 2);
		dialog.getDialogPane().setContent(grid);
		
		ButtonType buttonTypeOk = new ButtonType("Enter", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk );

		dialog.setResultConverter(new Callback<ButtonType, dateBounds>() {
			@Override
			public dateBounds call(ButtonType b) {

				if (b == buttonTypeOk) {
					return new dateBounds(textStartDate.getText(), textEndDate.getText());
				}
				return null;
			}
		});
		
		Optional<dateBounds> result = dialog.showAndWait();
		
		if (result.isPresent()) {

			actionStatus.setText("Result: " + result.get());
		}
	}
	
	private class dateBounds {
	
		private String StartDate;
		private String EndDate;
		
		dateBounds(String startDate, String endDate) {
		
			this.StartDate = startDate;
			this.EndDate = endDate;
		}
		
		@Override
		public String toString() {
		
			return (StartDate + ", " + EndDate);
		}
	}
}