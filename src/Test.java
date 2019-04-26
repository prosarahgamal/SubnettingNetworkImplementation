import java.util.ArrayList;

public class Test {

    static ArrayList<Integer> numOfNetwork = new ArrayList<>();
    static ArrayList<IP> ips = new ArrayList<>();
    static ArrayList<IP> nextIp = new ArrayList<>();

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
            ip.indexOfNextIp = index;
        }else {
            ip.indexOfNextIp = nextIp.size();
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
                    octal3 = newOctal3 ;
                    octal4 = 0;
                    newOctal4 = 0;
                    flag++;
                }
            }else{
                octal4 = newOctal4;
            }

            IP next = new IP(octal1, octal2, octal3, octal4, newMask);

            if (i == numOfSubnets-2){

                if (ips.size() == 1){
                    next.setIndexOfNextIp(1);
                }else {

                    //next.setIndexOfNextIp(nextIp.get((nextIp.size() - 1) - (numOfSubnets - 2 )-2).getIndexOfNextIp());  //wrong!
                    next.setIndexOfNextIp(index);
                }

            }else {
                next.setIndexOfNextIp(nextIp.size()+1);
            }

            nextIp.add(next);
        }
    }



    public static void main(String[] args) {
        ArrayList<Integer> hosts = new ArrayList<>();
        hosts.add(50);
        hosts.add(28);
        hosts.add(125);
        hosts.add(90);
        hosts.add(60);
        hosts.add(2);
        hosts.add(2);
        hosts.add(2);

        ArrayList<String> names = new ArrayList<>();
        names.add("s1");
        names.add("s2");
        names.add("s3");
        names.add("s4");
        names.add("s1");
        names.add("s2");
        names.add("s3");
        names.add("s4");

        int octal1 = 190;
        int octal2 = 12;
        int octal3 = 40;
        int octal4 = 0;
        int mask = 23;

        sort(names, hosts);
        addTwo(hosts);
        computeNumOfNetwork(hosts);

        subnetIps(octal1, octal2, octal3, octal4, mask, 0,  numOfNetwork.get(0));

        for (int i = 1; i < hosts.size(); i++) {

            System.out.println(i +" : " + ips.get(ips.size()-1).toString());


            int pointer = ips.get(ips.size()-1).getIndexOfNextIp();
            subnetIps(nextIp.get(pointer).getOctal1(), nextIp.get(pointer).getOctal2(), nextIp.get(pointer).getOctal3(), nextIp.get(pointer).getOctal4(), nextIp.get(pointer).getMask(), nextIp.get(pointer).getIndexOfNextIp(), numOfNetwork.get(i));

        }

        for (int i = 0; i < hosts.size(); i++) {
            System.out.println(hosts.get(i) +"\t"+names.get(i)+"\t"+numOfNetwork.get(i) + "\t" + ips.get(i));
        }

        for (int i = 0; i < nextIp.size(); i++) {
            System.out.println(nextIp.get(i).toString());
        }
    }
}
