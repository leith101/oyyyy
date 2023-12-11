/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalevent;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author leith
 */
public class Finalevent extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        

        
        Parent rooot = FXMLLoader.load(getClass().getResource("finalE.fxml"));
        
        Scene scenee = new Scene(rooot);
        
        primaryStage.setScene(scenee);
        primaryStage.show();
    }
    
    //@Override
    /*public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("finalE.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();*/
        
        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
