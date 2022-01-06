package controller;

import java.util.ArrayList;
import java.util.List;

public class NetworkObserver {

        private String networkMessage;
        private List<NetworkInterface> networkInterfaceList = new ArrayList<>();

        public void addObserver(NetworkInterface networkInterface) {
            this.networkInterfaceList.add(networkInterface);
        }

        public void removeObserver(NetworkInterface networkInterface) {
            this.networkInterfaceList.remove(networkInterface);
        }

        public void setMessage(String networkMessage) {
            this.networkMessage = networkMessage;
            for (NetworkInterface networkInterface : this.networkInterfaceList) {
                networkInterface.update(this.networkMessage);
            }
        }
    }

