import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ChangePass {
    
        static boolean result;
        static Stage window,window2;
        static Scene scene1,scene2;
        static Button confirmButton;
        static Label user,old,neww,error;
        static Text txt;
        static GridPane layout1;
        static VBox layout2;
        static TextField username = new TextField();
        static PasswordField oldpass = new PasswordField();
        static PasswordField newpass = new PasswordField();     
        
    public  static void display(){
        //Security sc = new Security();
        
        //windows
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ChangePassword");
        //Labels
        //user = new Label("Username");
        old = new Label("Old Password");
        neww = new Label("New Password");
        error = new Label();
        txt = new Text("Enter your password");
        txt.setFont(Font.font("Tahoma",FontWeight.LIGHT, 25));
        //username.setPromptText("username");
        oldpass.setPromptText("oldpassword");
        newpass.setPromptText("newpassword");
        //Buttons
        confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e ->{
            try {
                result = verify(oldpass,newpass);
            } catch (IOException ex) {
                Logger.getLogger(ChangePass.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(result);
            if(result){
                window.close();
            }
        });
        
        //Layout
        layout1 = new GridPane();
        layout1.setAlignment(Pos.CENTER);
        layout1.setVgap(10);
        layout1.setHgap(10);
        layout1.setPadding(new Insets(10));
        layout1.add(txt,0,0);
        //layout1.add(user,0,1);
        layout1.add(old,0,2);
        layout1.add(neww,0,3);
        //layout1.add(username,1,1);
        layout1.add(oldpass,1,2);
        layout1.add(newpass,1,3);
        layout1.add(confirmButton,1,4);
        
        layout2 = new VBox(10);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(error);
        
        //Scenes
        scene1 = new Scene(layout1,600,450);
        scene2 = new Scene(layout2,200,200);
        
        //Stage
        window.setScene(scene1);
        
        window.showAndWait();

    }
    
    static private boolean verify(PasswordField oldpass,PasswordField newpass) throws IOException{
        File files = new File("mypass.txt");  
        Scanner scan = new Scanner(files);
        Scanner scan2 = new Scanner(files);
        Scanner scan3 = new Scanner(files);
        //String pass =scan.nextLine();
        window2 = new Stage();       
        window2.initModality(Modality.APPLICATION_MODAL);
        window2.setScene(scene2);
        boolean verified;
        boolean grantAccess = false;
        
        String allpass = "";
        while(scan.hasNextLine()){
            allpass = allpass.concat(scan.nextLine() + "\n");
        }
        
        ArrayList<String> users = new ArrayList<>();
        while(scan2.hasNextLine()){
            users.add(scan2.nextLine());
        }
        for(int i=0; i<10; i++){
            if(scan3.nextLine().equals(oldpass.getText())){ 
                System.out.println(users.get(i));
                grantAccess=true;
                users.set(i, newpass.getText());
                break;
                //i++;
                /*if(scan3.nextLine().equals(oldpass.getText())){ 
                    
                }*/
            }
        }      
        scan.close();
        scan2.close();
        scan3.close();
        //Verify oldpass
            if(grantAccess == true){
            //Verify newpass
                if(checkString(newpass.getText())==true){
                    FileWriter writer = new FileWriter("mypass.txt");
                    for(int i=0; i<10; i++){
                        writer.write(users.get(i) + "\n");
                    }
                    writer.close();
                    //username.clear();
                    oldpass.clear();
                    newpass.clear();
                    error.setText("Success!!");
                    window2.showAndWait();
                    verified = true;
                }
                else{
                    error.setText("Incorrect. Please try again.");
                    window2.showAndWait();     
                    verified = false;
                }
            }
            else{       
                error.setText("Incorrect Username. Please try again.");
                window2.showAndWait();     
                verified = false;
            }
 
        return verified;
    }
    
    public static boolean checkString(String str){
            
        if(str != null && (!str.equals(""))&& (str.matches("^[a-zA-Z0-9]*$")))
        {
            return true;
        }
        else{
            return false;
        }
    }
}
