package spinacht.viz;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nachtm on 5/14/17.
 */
public class Visualizer extends Application {

    public static final String outpath = "outfiles/points.txt";

    @Override
    public void start(Stage primaryStage) throws Exception {

        List<Point2D.Double> points = new ArrayList<>();

        FlowPane root = new FlowPane(Orientation.HORIZONTAL);

        //build the canvases
        GridPane canvases = new GridPane();

        StackPane canvasPane = new StackPane();
        StackPane xOnlyPane = new StackPane();
        StackPane yOnlyPane = new StackPane();

        Canvas canv = new Canvas(500, 500);
        Canvas xOnly = new Canvas(500, 25);
        Canvas yOnly = new Canvas(25, 500);

        Pane[] panes = {canvasPane, xOnlyPane, yOnlyPane};
        Node[] nodes = {canv, xOnly, yOnly};
        addNodesAndStyle(panes, nodes, "bordered");

        canvases.add(yOnlyPane, 0, 1);
        canvases.add(xOnlyPane, 1, 0);
        canvases.add(canvasPane, 1, 1);
        root.getChildren().add(canvases);

        //build the controls
        FlowPane controls = new FlowPane(Orientation.VERTICAL);
        controls.setVgap(10);

        FlowPane buttons = new FlowPane();
        buttons.setHgap(5);
        Button saveButton = new Button("Save points");
        Button goCluster = new Button("Cluster this!");
        buttons.getChildren().add(saveButton);
        buttons.getChildren().add(goCluster);

        FlowPane numPoints = new FlowPane();
        numPoints.getChildren().add(new Label("NumPoints:"));
        Spinner<Integer> ptSpinner = new Spinner<>(1, Integer.MAX_VALUE, 50, 5);
        ptSpinner.setEditable(true);
        numPoints.getChildren().add(ptSpinner);

        FlowPane epsilonSetter = new FlowPane();
        Slider slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);
        epsilonSetter.getChildren().add(slider);
        epsilonSetter.getChildren().add(new Canvas(300, 300));

        controls.getChildren().add(buttons);
        controls.getChildren().add(numPoints);
        controls.getChildren().add(epsilonSetter);
        root.getChildren().add(controls);

        saveButton.setOnMouseClicked((MouseEvent e) -> {
            File f = new File(outpath);
            try (FileWriter fw = new FileWriter(f)){
                for(Point2D.Double p : points){
                    fw.write(p.getX() + " " + p.getY() + "\n");
                }
            } catch (Exception err){
                System.err.println(err);
            }
            System.out.println("Points saved!");
        });

        canv.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            double minX = x - 2 > 0 ? x - 2 : 0;
            double minY = y - 2 > 0 ? y - 2 : 0;
            canv.getGraphicsContext2D().fillRect(minX, minY, 4,4);
            yOnly.getGraphicsContext2D().fillRect(12, minY, 4,4);
            xOnly.getGraphicsContext2D().fillRect(minX, 12, 4, 4);
//            GraphicsContext gc = canv.getGraphicsContext2D();
//            gc.fillRect(minX, minY, 4, 4);
            points.add(new Point2D.Double(x, y));
            System.out.println(x + " " + y);
        });



        Scene s = new Scene(root, 1000, 600);
        s.getStylesheets().add("borders.css");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private void addNodesAndStyle(Pane[] panes, Node[] nodes, String style){
        if(panes.length != nodes.length) {throw new IllegalArgumentException("Nodes and Panes must be same length");}
        for(int i = 0; i < panes.length; i++){
            panes[i].getChildren().add(nodes[i]);
            panes[i].getStyleClass().add(style);
        }
    }
}
