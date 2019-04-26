
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Sub {

    static ArrayList<Integer> numOfNetwork = new ArrayList<>();
    static ArrayList<IP> ips = new ArrayList<>();
    static ArrayList<IP> nextIp = new ArrayList<>();
    Interface in;

    public Sub(){
        in = new Interface();
        in.display();


        in.calculate.setOnAction(e -> {

            ArrayList<Integer> hosts = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();

            ips.clear();
            numOfNetwork.clear();
            nextIp.clear();

            //read from text field
            for (int i = 0; i < in.subnetsNames.size(); i++) {
                names.add(in.subnetsNames.get(i).getText());
                hosts.add(Integer.parseInt(in.subnetsHosts.get(i).getText()));
            }

            int octal1 = Integer.parseInt(in.octal1.getText());
            int octal2 = Integer.parseInt(in.octal2.getText());
            int octal3 = Integer.parseInt(in.octal3.getText());
            int octal4 = Integer.parseInt(in.octal4.getText());
            int mask = Integer.parseInt(in.mask.getText());


            sort(names, hosts);
            addTwo(hosts);
            computeNumOfNetwork(hosts);

            subnetIps(octal1, octal2, octal3, octal4, mask, 0,  numOfNetwork.get(0));

            for (int i = 1; i < hosts.size(); i++) {

                int pointer = ips.get(ips.size()-1).getIndexOfNextIp();
                subnetIps(nextIp.get(pointer).getOctal1(), nextIp.get(pointer).getOctal2(), nextIp.get(pointer).getOctal3(), nextIp.get(pointer).getOctal4(), nextIp.get(pointer).getMask(), nextIp.get(pointer).getIndexOfNextIp(), numOfNetwork.get(i));

            }


            Stage stage = new Stage();
            Pane pane = new FlowPane();

            TableView tableView = new TableView();
            TableColumn subnet = new TableColumn("Subnet Name");
            TableColumn networkIP = new TableColumn("Network IP");
            TableColumn prefix = new TableColumn("Prefix");
            TableColumn subnetMask = new TableColumn("Subnet Mask");
            TableColumn firstIp = new TableColumn("First IP");
            TableColumn lastIp = new TableColumn("Last IP");
            TableColumn broadcast = new TableColumn("BroadCast");

            subnet.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("subnet"));
            networkIP.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("networkIp"));
            prefix.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("prefix"));
            subnetMask.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("subnetMask"));
            firstIp.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("firstIp"));
            lastIp.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("lastIp"));
            broadcast.setCellValueFactory(new PropertyValueFactory<IPForTable, String>("broadcast"));

            subnet.setPrefWidth(150);
            networkIP.setPrefWidth(150);
            prefix.setPrefWidth(150);
            subnetMask.setPrefWidth(150);
            firstIp.setPrefWidth(150);
            lastIp.setPrefWidth(150);
            broadcast.setPrefWidth(150);

            ArrayList<IPForTable> ipsForTables = new ArrayList<>();
            for (int i = 0; i < ips.size(); i++) {
                IPForTable ipp = new IPForTable(names.get(i), ips.get(i).getNetworkIp(), Integer.toString(ips.get(i).getMask()), ips.get(i).getSubnetMask(), ips.get(i).getFirstIp(),
                        ips.get(i).getLastIp(nextIp.get(ips.get(i).getIndexOfNextIp()).octal3, nextIp.get(ips.get(i).getIndexOfNextIp()).octal4),
                        ips.get(i).getBroadcast(nextIp.get(ips.get(i).getIndexOfNextIp()).octal3, nextIp.get(ips.get(i).getIndexOfNextIp()).octal4));
                ipsForTables.add(ipp);
            }

            tableView.getColumns().addAll(subnet, networkIP, prefix, subnetMask, firstIp, lastIp, broadcast);


            tableView.setItems(FXCollections.observableArrayList(ipsForTables));



            pane.getChildren().add(tableView);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();


            //****************************************//
            for (int i = 0; i < hosts.size(); i++) {
                System.out.println(hosts.get(i) +"\t"+names.get(i)+"\t"+numOfNetwork.get(i) + "\t" + ips.get(i));
            }

            for (int i = 0; i < nextIp.size(); i++) {
                System.out.println(nextIp.get(i).toString());
            }

        });
    }

    public static void sort(ArrayList<String> names, ArrayList<Integer> hosts){
        int n = hosts.size();
        for (int i=1; i<n; ++i)
        {
            int key = hosts.get(i);
            String name = names.get(i);
            int j = i-1;
            while (j>=0 && hosts.get(j) < key)
            {
                hosts.set(j+1,hosts.get(j) );
                names.set(j+1, names.get(j));
                j = j-1;
            }
            hosts.set(j+1, key);
            names.set(j+1, name);
        }
    }

    public static void addTwo (ArrayList<Integer> hosts){
        for (int i = 0; i < hosts.size(); i++) {
            hosts.set(i, hosts.get(i)+2);
        }
    }

    public static void computeNumOfNetwork(ArrayList<Integer> hosts){
        for (int i = 0; i < hosts.size(); i++) {
            numOfNetwork.add((int)(Math.ceil(Math.log(hosts.get(i)) / Math.log(2))));
        }
    }

    public static void subnetIps(int octal1, int octal2, int octal3, int octal4, int mask, int index, int numOfNetwork){

        int borrowedBits = 32 - mask - numOfNetwork;
        int blockSize = (int)Math.pow(2, numOfNetwork);
        int numOfSubnets = (int)Math.pow(2, borrowedBits);
        int newMask = mask + borrowedBits;

        int newOctal1 = octal1;
        int newOctal2 = octal2;
        int newOctal3 = octal3;
        int newOctal4 = octal4;

        int flag = 0;

        IP ip = new IP(octal1, octal2, octal3, octal4, newMask);
        ips.add(ip);

        if (numOfSubnets == 1){
            ip.setIndexOfNextIp(index);
        }else {
            ip.setIndexOfNextIp(nextIp.size());
        }

        for (int i = 0; i < numOfSubnets-1; i++) {

            newOctal4 += blockSize;
            if(newOctal4 > 255){
                int temp = (int)(Math.floor(newOctal4 / 255));
                newOctal3 += temp;
                if (newOctal3 > 255){
                    int temp2 = (int)(Math.floor(newOctal3 / 255));
                    newOctal2 += temp2;
                    octal2 = newOctal2;
                }else{
                    newOctal3 -= flag;
                    octal3 = newOctal3;
                    octal4 = 0;
                    flag++;
                }
            }else{
                octal4 = newOctal4;
            }

            IP next = new IP(octal1, octal2, octal3, octal4, newMask);

            if (i == numOfSubnets-2){
                if (ips.size() == 1){
                    next.setIndexOfNextIp(0);
                }else {
//                    next.setIndexOfNextIp(ips.get(ips.size() - 2).getIndexOfNextIp());
                    next.setIndexOfNextIp(index);
                }
            }else {
                next.setIndexOfNextIp(nextIp.size()+1);
            }

            nextIp.add(next);
        }
    }

}
