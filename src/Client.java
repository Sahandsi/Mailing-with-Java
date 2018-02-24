import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Client extends Application // for GUI
{

    private PrintWriter outputToServer; // send message to server
    private Scanner inputFromServer;    // gets response back from the server
    //private String username;
    private Socket socket;
    private static InetAddress host = null;
    final int PORT = 1234;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("Host ID Not Found");
        }

        do {
            launch(args);
        } while (true);

    }

    // GUI

    public void start(Stage stage) throws Exception {
        // set up variables

        socket = new Socket(host, PORT);
        // scanner set up so that it can scan for any input stream (responses) that come from the server
        inputFromServer = new Scanner(socket.getInputStream());
        outputToServer = new PrintWriter(socket.getOutputStream(), true);


        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));


        //stage.setScene(new Scene(root,800,500));
        Scene scene;
//        TextField textUsername;
//        Button buttonValidate;
//        Label message ,firstNamePromt;
//        VBox vbox;

        // initialise the username and the button
//        firstNamePromt = new Label("Enter your Username :");
//        textUsername = new TextField();
//        textUsername.setPrefWidth(900);
//
//        buttonValidate = new Button("Submit");
//        buttonValidate.setPrefWidth(100);
//
//        message = new Label();

        // event handler to validate username when the button is pressed
        //buttonValidate.setOnAction(e -> validateUsername(textUsername.getText(), message));

        // add the textfield and the button to the layout
//        vbox = new VBox();
//
//        vbox.getChildren().add(firstNamePromt);
//        vbox.getChildren().add(textUsername);
//        vbox.getChildren().add(buttonValidate);
//        vbox.getChildren().add(message);
//        // add the layout to the scene, layout, width, height
        scene = new Scene(root, 500, 500);
        //add the scene to the stage
        stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private TextField Input;

    @FXML
    private void loginButton (ActionEvent event)
    {

        String t = Input.getText();
        validateUsername(t);
        System.out.println(t);

    }



    private void validateUsername(String username) {
//        if (username.isEmpty()) {
//           // message.setText("Please enter your username");
//        } else {
            // send username across to the server
            outputToServer.println(username);
            String serverRequest = inputFromServer.nextLine();

            if (serverRequest.equals("true")) {
                LoadClient();
            }
//            else {
////               // message.setText("wrong username please try again");
////            }
        }
//    }

    private void LoadClient() {
        Scene scene;
        VBox vbox;
        Stage stage;

        Button inbox = new Button("Inbox");
        Button email = new Button("Email");
        Button quit = new Button("Quit");

        inbox.setOnAction(e -> getInbox());
        email.setOnAction(e -> getEmail());
        quit.setOnAction(e -> quitApp());

        // add buttons to layout
        vbox = new VBox();
        vbox.getChildren().add(inbox);
        vbox.getChildren().add(email);
        vbox.getChildren().add(quit);

        scene = new Scene(vbox, 500, 500);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void getInbox() {
        System.out.println("BEFORE SENDING INBOX REQUEST");
        outputToServer.println("get_inbox");
        System.out.println("AFTER SENDING INBOX REQUEST");
    }

    public void getEmail() {
        outputToServer.println("send_email");
    }

    public void quitApp() {
        outputToServer.println("close");
    }

}
