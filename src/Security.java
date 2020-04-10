/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Security {

    static boolean result = false;
    static Stage window;
    static Button confirm_button = new Button("Confirm");
    static BorderPane pane = new BorderPane();
    static HBox layout1;
    static VBox layout2;
    static Label numbers, password, error;
    static int num1, num2;
    static TextField answer = new TextField();
    static TextField form_password = new TextField();
    static Scene scene = new Scene(pane, 400, 300);

    static boolean display() {

        result = false;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Security");

        //Generate numbers
        num1 = new Random().nextInt(98) + 1;
        num2 = new Random().nextInt(98) + 1;

        //Labels 
        numbers = new Label(String.valueOf(num1) + " + " + String.valueOf(num2) + " = ");
        numbers.setFont(Font.font(30));
        password = new Label("Enter Username.");
        error = new Label();

        //Text Fields
        answer.setFont(Font.font(30));
        answer.setMaxWidth(90);
        answer.setTextFormatter(new TextFormatter<String>((Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 3) {
                return null;
            } else {
                return change;
            }
        }));
        answer.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    answer.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        form_password.setPromptText("username");
        form_password.setMaxWidth(200);

        //Buttons
        confirm_button.setOnAction(e -> {
            try {
                result = verify_password(answer, form_password);
            } catch (IOException ex) {
                Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Function
            if (result) {
                window.close();
                answer.clear();
                form_password.clear();
            }
        });

        //Layout 
        layout1 = new HBox(10);
        layout1.getChildren().addAll(numbers, answer);
        layout1.setAlignment(Pos.CENTER);

        layout2 = new VBox(10);
        layout2.getChildren().addAll(password, form_password, confirm_button, error);
        layout2.setAlignment(Pos.CENTER);

        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setCenter(layout2);
        pane.setTop(layout1);

        //Scene
        scene.setRoot(pane);

        //Keyboard
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER: {
                try {
                    result = verify_password(answer, form_password);
                } catch (IOException ex) {
                    Logger.getLogger(Security.class.getName()).log(Level.SEVERE, null, ex);
                }
                    //Function
                    if (result) {
                        window.close();
                        answer.clear();
                        form_password.clear();
                    }
                }
            }
        });

        //Stage 
        window.setScene(scene);
        window.showAndWait();

        if(result){
            ChangePass.display();
        }
        return result;

        
    }

    static private boolean verify_password(TextField sum, TextField pass) throws IOException {
        boolean verified = false;
        File files = new File("mypass.txt");  
        Scanner scan = new Scanner(files);
        //Verify sum
        if (sum.getText().compareTo(String.valueOf(num1 + num2)) == 0) {
            //Verify password
            for(int i = 0;i<10;i++){
                if (pass.getText().compareTo(scan.nextLine()) == 0) {
                    verified = true;
                } 
                else {
                    error.setText("Incorrect Username. Please try again.");
                }
            }
            
        } else {
            sum.clear();
            pass.clear();
            num1 = new Random().nextInt(98) + 1;
            num2 = new Random().nextInt(98) + 1;
            numbers.setText(String.valueOf(num1) + " + " + String.valueOf(num2) + " = ");
            error.setText("Incorrect. Please try again.");
            verified = false;
        }

        return verified;
    }
}
