/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodyorder;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ilyes
 */
public class AdminPanelController implements Initializable {
    AdminModel adminModel=new AdminModel();
    @FXML
    private JFXTextField lastNametxt;

    @FXML
    private JFXTextField firstNametxt;
    @FXML
    private ListView<String> listview;
  Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
                 tableConnection1();

          if(adminModel.isDbConnected()){
             System.out.println("Db connected");
        }else{
             System.out.println("Db not connected");
        }
    }
    @FXML
    public void exitScreen(ActionEvent event){
        System.exit(0);
    }

       public AdminPanelController(){
        con=SqlConnection.Connector();
    }
    
    public void AddEmployee(ActionEvent event){
       String fname=firstNametxt.getText();
       int lname=Integer.valueOf(lastNametxt.getText());
       try {
           if((fname.isEmpty()|| lname==0 )){
               infoBox("Enter valid fields",null,"Error");
           }else{
               adminModel.isAdd(fname, lname);
               infoBox(" Added Sucessfully\n Employee Id is:"+adminModel.aid,null,"Success" );
               firstNametxt.clear();
               lastNametxt.clear();
           }
           
       } catch (SQLException ex) {
           infoBox("please fill balnk fields",null,"Alert" );
           Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
     public void TakeOrderScreen(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("TakeOrder.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
  
          public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

      public void tableConnection1(){
        
        try {
             
            String query="SELECT * FROM menu ";
            ResultSet rs =con.createStatement().executeQuery(query);
            while(rs.next()){
           String name = rs.getString("menu_name");
           int id = rs.getInt("menu_id");
         int prix = rs.getInt("price");
String listOut = id + "******\"" + name + "*****\"" + prix  ;
      listview.getItems().add(listOut);
            }
        } catch (SQLException ex) {

            Logger.getLogger(AdminPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    
    @FXML
     public void TakeOrderScreen1(ActionEvent event) throws Exception  {
		Stage primaryStage =new Stage();
                primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root =FXMLLoader.load(getClass().getResource("api.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
                
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
	}
}
