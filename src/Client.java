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
    private PrintWriter outputToServer ; // send message to server
    private Scanner inputFromServer;    // gets response back from the server
    private Socket socket;
    private static InetAddress host = null;
    final int PORT = 1234;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
            }

            catch (UnknownHostException ex)
            {
            System.out.println("Host ID Not Found");
            }

        do
            {
            launch(args);
            }

            while (true);
    }

    @FXML
    private TextField Input;

    @FXML
    public void start(Stage stage) throws Exception {
        // set up variables
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        Scene scene;
        scene = new Scene(root, 800, 500);
        //add the scene to the stage
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void loader(){
        try
        {
            socket = new Socket(host, PORT);
            inputFromServer = new Scanner(socket.getInputStream());
            outputToServer = new PrintWriter(socket.getOutputStream(),true);

        }
        catch (IOException e)
        {

            e.printStackTrace();
            return;
        }

        if(this.outputToServer == null)
        {
            System.out.println("NULL OUTPUT TO SERVER");
        }
    }


    @FXML
    private void loginButton(ActionEvent event) throws  IOException
    {
        //Calling the Server codes
        loader();
        //Making sure that outputToServer is running
        if (outputToServer == null)
        {
            System.out.println("NULL IN LOGIN BUTTON FUNCTION");
        }
        //Getting TextBox value
        String t = Input.getText();
        //Send it to the Server
        validateUsername(t);
    }


    @FXML
    private void validateUsername(String username) throws IOException
    {
        if (username.isEmpty())
        {
            //message.setText("Please enter your username");
        }
        else
            {

                this.outputToServer.println(username);

                String serverRequest = inputFromServer.nextLine();

                if (serverRequest.equals("true"))
                {
                LoadClient();
                }

                else if (serverRequest.equals("false"))
                {
                System.out.println("false login");
                }
            }
    }


    @FXML private javafx.scene.control.Button login;

    //For closing the old Scene once user logs in.
    @FXML
    private void closeButtonAction(){
        // get a name of the button to the stage
        Stage stage = (Stage) login.getScene().getWindow();
        // close the old stage
        stage.close();
    }

    //Loading the new Scene
    private void LoadClient() throws IOException
    {
        Scene scene;
//        VBox vbox;
        Stage stage;
//        Button inbox = new Button("Inbox");
//        Button email = new Button("Email");
//        Button quit = new Button("Quit");
//
//        inbox.setOnAction(e -> getInbox());
//        email.setOnAction(e -> getEmail());
//        quit.setOnAction(e -> quitApp());
//
//        // add buttons to layout
//        vbox = new VBox();
//        vbox.getChildren().add(inbox);
//        vbox.getChildren().add(email);
//        vbox.getChildren().add(quit);
        outputToServer = new PrintWriter(socket.getOutputStream(),true);
        closeButtonAction();

        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        scene = new Scene(root, 800, 500);
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