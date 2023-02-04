/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package appinformes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Adrian
 */
public class AppInformes extends Application {
    public static Connection conexion = null;
    
    @Override
    public void start(Stage primaryStage) {
           //Probamos conexion a la BD
           conectaBD();

           //Creamos la escena
           primaryStage.setTitle("Informes");
           MenuBar menuBar = new MenuBar();

           Menu menuInformes = new Menu("Informes");
           menuBar.getMenus().add(menuInformes);

           MenuItem listadoFacturas = new MenuItem("Listado Facturas");
           menuInformes.getItems().add(listadoFacturas);

           MenuItem ventasTotales = new MenuItem("Ventas Totales");
           menuInformes.getItems().add(ventasTotales);
           
           MenuItem facturasPorCliente = new MenuItem("Facturas por Cliente");
           menuInformes.getItems().add(facturasPorCliente);
           
           MenuItem subinformeListadoFacturas = new MenuItem("Subinforme Listado Facturas");
           menuInformes.getItems().add(subinformeListadoFacturas);
           
           Pane pane = new Pane(menuBar);
           Scene scene = new Scene(pane, 960, 600);

           listadoFacturas.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   generaInforme1();
                   
               }


           });

           ventasTotales.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   generaInforme2();
                   
               }


           });
           
           facturasPorCliente.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   generaInforme3();
                   
               }


           });
           
           subinformeListadoFacturas.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   generaInforme4();
                   
               }


           });
           
           primaryStage.setScene(scene);
           primaryStage.show();

    }
    @Override
    public void stop() throws Exception {
        try {
            DriverManager.getConnection("jdbc:hsqldb:hsql://localhost;shutdown=true");

        } catch (Exception ex) {
            System.out.println("No se pudo cerrar la conexion a la BD");
            
        }
    }

    public void conectaBD(){
        //Establecemos conexión con la BD
        String baseDatos = "jdbc:hsqldb:hsql://localhost/SampleDB";
        String usuario = "sa";
        String clave = "";
        
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            conexion = DriverManager.getConnection(baseDatos,usuario,clave);
        
        }
        catch (ClassNotFoundException cnfe){
            System.err.println("Fallo al cargar JDBC");
            System.exit(1);
            
        }
        catch (SQLException sqle){
            System.err.println("No se pudo conectar a BD");
            System.exit(1);
            
        }
        catch (java.lang.InstantiationException sqlex){
            System.err.println("Imposible Conectar");
            System.exit(1);
            
        }
        catch (Exception ex){
            System.err.println("Imposible Conectar");
            System.exit(1);
            
        }
    }
    
    public void generaInforme1() { 
        try {
            JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Listado_Facturas.jasper"));
            
            //Map de parámetros
            Map parametros = new HashMap();
//            int nproducto = 33;
//            parametros.put("CodigoCliente", nproducto);

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp);
            
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void generaInforme2() { 
        try {
            JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Ventas_Totales.jasper"));
            
            //Map de parámetros
            Map parametros = new HashMap();

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp);
            
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void generaInforme3() { 
        try {
            JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Facturas_por_Cliente.jasper"));
            
            //Map de parámetros
            Map parametros = new HashMap();
            int nproducto = 33;
            parametros.put("CodigoCliente", nproducto);

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp);
            
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    public void generaInforme4() { 
        try {
            JasperReport jr = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Subinforme_Listado_Facturas.jasper"));
            
            //Map de parámetros
            Map parametros = new HashMap();

            JasperReport subreport = (JasperReport)JRLoader.loadObject(AppInformes.class.getResource("Subinforme_Facturas.jasper"));
            
            parametros.put("subReportParameter", subreport);
            
            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
            JasperViewer.viewReport(jp, false);
            
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
            
        }
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
    launch(args);
    }
}
