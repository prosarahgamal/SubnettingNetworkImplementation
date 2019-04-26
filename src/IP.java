public class IP{
    int octal1;
    int octal2;
    int octal3;
    int octal4;
    int mask;
    int indexOfNextIp;

    public IP(int octal1, int octal2, int octal3, int octal4, int mask) {
        this.octal1 = octal1;
        this.octal2 = octal2;
        this.octal3 = octal3;
        this.octal4 = octal4;
        this.mask = mask;
    }

    public int getOctal1() {
        return octal1;
    }

    public void setOctal1(int octal1) {
        this.octal1 = octal1;
    }

    public int getOctal2() {
        return octal2;
    }

    public void setOctal2(int octal2) {
        this.octal2 = octal2;
    }

    public int getOctal3() {
        return octal3;
    }

    public void setOctal3(int octal3) {
        this.octal3 = octal3;
    }

    public int getOctal4() {
        return octal4;
    }

    public void setOctal4(int octal4) {
        this.octal4 = octal4;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public int getIndexOfNextIp() {
        return indexOfNextIp;
    }

    public void setIndexOfNextIp(int indexOfNextIp) {
        this.indexOfNextIp = indexOfNextIp;
    }

    @Override
    public String toString() {
        return "IP{" +
                "octal1=" + octal1 +
                ", octal2=" + octal2 +
                ", octal3=" + octal3 +
                ", octal4=" + octal4 +
                ", mask=" + mask +
                ", indexOfNextIp=" + indexOfNextIp +
                '}';
    }

    public String getNetworkIp(){
        String network = octal1 + "." + octal2 + "." + octal3 + "." + octal4;
        return network;
    }

    public String getSubnetMask(){

        String subnetMask = "";

        if(mask >= 24){
            subnetMask = "255.255.255.";
            int zeros = 32 - mask;
            int ones = 8 - zeros;
            String s = "";
            for (int i = 0; i < ones; i++) {
                s += "1";
            }
            for (int i = 0; i < zeros; i++) {
                s += "0";
            }
            subnetMask += Integer.parseInt(s, 2);
        }else if(mask < 24){
            subnetMask = "255.255.";
            int zeros = 24 - mask;
            int ones = 8 - zeros;
            String s = "";
            for (int i = 0; i < ones; i++) {
                s += "1";
            }
            for (int i = 0; i < zeros; i++) {
                s += "0";
            }
            subnetMask += Integer.parseInt(s, 2);
            subnetMask += ".0";
        }

        return subnetMask;
    }

    public String getFirstIp(){

        String firstIp = octal1 + "." + octal2 + "." + octal3 + "." + (octal4+1);

        return firstIp;
    }

    public String getLastIp(int newOctal3, int newOctal4){

        if(newOctal4 == 0){
            newOctal3--;
            newOctal4 = 254;
        }else{
            newOctal4 -= 2;
        }

        String lastIp = octal1 + "." + octal2 + "." + newOctal3 + "." + newOctal4;

        return lastIp;
    }

    public String getBroadcast(int newOctal3, int newOctal4){

        if(newOctal4 == 0){
            newOctal3--;
            newOctal4 = 255;
        }else{
            newOctal4--;
        }

        String broadcast = octal1 + "." + octal2 + "." + newOctal3 + "." + newOctal4;

        return broadcast;
    }
}
