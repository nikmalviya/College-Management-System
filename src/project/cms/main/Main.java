
package project.cms.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Label username = new Label("User Name");
        Label password = new Label("Password");
        TextField usertext = new TextField();
        PasswordField pass = new PasswordField();
        GridPane grid = new GridPane();
        grid.addColumn(0,username,password);
        grid.addColumn(1,usertext,pass);
        Button btn = new Button("login");
        TextField t = new TextField();
        t.setEditable(false);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            System.out.println("login clicked");
            t.setText(usertext.getText()+" "+pass.getText());
        });
        VBox pane = new VBox(10,grid,t,btn);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
