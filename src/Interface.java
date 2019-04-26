import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Interface {

    Stage stage;

    BorderPane borderPane;

    VBox vBox;
    HBox hBox;
    HBox pluaAndMinus;
    VBox centerVBox;
    HBox btnshBox;
    HBox subnetLbls;

    Label subnetNameLbl;
    Label subnetHostLbl;

    ArrayList<JFXTextField> subnetsNames;
    ArrayList<JFXTextField> subnetsHosts;

    JFXButton addSubnetButton;
    JFXButton delSubnetButton;

    JFXButton calculate;

    Label networkLbl;
    Label slash;
    JFXTextField octal1;
    JFXTextField octal2;
    JFXTextField octal3;
    JFXTextField octal4;
    JFXTextField mask;

    int pointer = 0;

    public void display(){
        stage = new Stage();

        borderPane = new BorderPane();

        vBox = new VBox(20);
        hBox = new HBox(20);
        btnshBox = new HBox(20);

        networkLbl = new Label("NETWORK IP");
        slash = new Label("\n/");
        octal1 = new JFXTextField();
        octal2 = new JFXTextField();
        octal3 = new JFXTextField();
        octal4 = new JFXTextField();
        mask = new JFXTextField();

        String lblStyle = "-fx-text-fill: #ffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family:\"Century Gothic\";";

        String btnStyle = "-fx-background-color: #ffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-pref-width: 40px;" +
                "-fx-pref-height: 40px;" +
                "-fx-background-radius: 50;";

        String txtStyle = "-fx-pref-width: 50px;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family:\"Century Gothic\";";

        hBox.getChildren().addAll(networkLbl, octal1, octal2, octal3, octal4, slash, mask);
        hBox.setPadding(new Insets(50));
        networkLbl.setStyle(lblStyle);

        octal1.setStyle(txtStyle);
        octal2.setStyle(txtStyle);
        octal3.setStyle(txtStyle);
        octal4.setStyle(txtStyle);
        mask.setStyle(txtStyle);
        slash.setStyle("-fx-text-fill: #ffffff;");

        octal1.setFocusColor(new Color(1, 1, 1, 1));
        octal2.setFocusColor(new Color(1, 1, 1, 1));
        octal3.setFocusColor(new Color(1, 1, 1, 1));
        octal4.setFocusColor(new Color(1, 1, 1, 1));
        mask.setFocusColor(new Color(1, 1, 1, 1));

        subnetsNames = new ArrayList<>();
        subnetsHosts = new ArrayList<>();

        addSubnetButton = new JFXButton("+");
        delSubnetButton = new JFXButton("-");
        addSubnetButton.setStyle(btnStyle);
        delSubnetButton.setStyle(btnStyle);

        pluaAndMinus = new HBox(160);
        pluaAndMinus.getChildren().addAll(addSubnetButton, delSubnetButton);

        calculate = new JFXButton("CALCULATE");
        btnshBox.getChildren().add(calculate);

        subnetsNames.add(new JFXTextField());
        subnetsHosts.add(new JFXTextField());
        addStyleToText();

        subnetNameLbl = new Label("NAME");
        subnetHostLbl = new Label("HOSTS");

        subnetLbls = new HBox(70);
        subnetLbls.getChildren().addAll(subnetNameLbl, subnetHostLbl);
        vBox.setPadding(new Insets(0,0,0,10));

        subnetNameLbl.setStyle(lblStyle);
        subnetHostLbl.setStyle(lblStyle);

        HBox sub = new HBox(50);
        sub.getChildren().addAll(subnetsNames.get(pointer), subnetsHosts.get(pointer));
        vBox.getChildren().add(pointer, sub);
        vBox.setPadding(new Insets(0,0,0,20));

        centerVBox = new VBox(20);
        centerVBox.getChildren().addAll(subnetLbls, vBox, pluaAndMinus);
        centerVBox.setPadding(new Insets(20,20,20,100));

        calculate.setStyle("-fx-text-fill: #ffffff;" +
                "-fx-font-size: 20px;" +
                "-fx-pref-width: 300px;");


        borderPane.setTop(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        borderPane.setCenter(centerVBox);
        BorderPane.setAlignment(centerVBox, Pos.CENTER);
        borderPane.setBottom(calculate);
        BorderPane.setAlignment(calculate, Pos.CENTER);

        borderPane.setStyle("-fx-background-color: #49268F;");

        borderPane.setPadding(new Insets(20));

        Scene s = new Scene(borderPane, 800, 800);
        stage.setScene(s);
        stage.show();
        stage.setTitle("may this subnet your IP!");

        addSubnetButton.setOnAction(e ->{
            pointer++;
            subnetsNames.add(new JFXTextField());
            subnetsHosts.add(new JFXTextField());
            addStyleToText();
            HBox sub1 = new HBox(50);
            sub1.getChildren().addAll(subnetsNames.get(pointer), subnetsHosts.get(pointer));
            vBox.getChildren().add(pointer, sub1);
        });

        delSubnetButton.setOnAction(e ->{
            if((pointer - 1) >= 0) {
                subnetsNames.remove(pointer);
                vBox.getChildren().remove(pointer);
                pointer--;
            }
        });
    }

    public void addStyleToText(){
        subnetsNames.get(subnetsNames.size()-1).setFocusColor(new Color(1, 1, 1, 1));
        subnetsNames.get(subnetsNames.size()-1).setStyle("-fx-pref-width: 70px;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family:\"Century Gothic\";");

        subnetsHosts.get(subnetsHosts.size()-1).setFocusColor(new Color(1, 1, 1, 1));
        subnetsHosts.get(subnetsHosts.size()-1).setStyle("-fx-pref-width: 70px;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family:\"Century Gothic\";");

        subnetsHosts.get(subnetsHosts.size()-1).setText("");
    }

}
