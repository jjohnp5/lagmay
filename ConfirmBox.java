package cardgame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Created by quickfire on 1/10/2017.
 */
public class ConfirmBox {
        static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();

        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        HBox buttons = new HBox(25);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(yesButton, noButton);
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setMargin(label, new Insets(10));
        layout.setMargin(buttons, new Insets(10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
