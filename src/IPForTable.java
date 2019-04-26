import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class IPForTable extends RecursiveTreeObject{
    String subnet;
    String networkIp;
    String prefix;
    String subnetMask;
    String firstIp;
    String lastIp;
    String broadcast;

    public IPForTable(String subnet, String networkIp, String prefix, String subnetMask, String firstIp, String lastIp, String broadcast) {
        this.subnet = subnet;
        this.networkIp = networkIp;
        this.prefix = prefix;
        this.subnetMask = subnetMask;
        this.firstIp = firstIp;
        this.lastIp = lastIp;
        this.broadcast = broadcast;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getNetworkIp() {
        return networkIp;
    }

    public void setNetworkIp(String networkIp) {
        this.networkIp = networkIp;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getFirstIp() {
        return firstIp;
    }

    public void setFirstIp(String firstIp) {
        this.firstIp = firstIp;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }
}
