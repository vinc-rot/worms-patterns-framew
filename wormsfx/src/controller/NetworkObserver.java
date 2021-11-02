package controller;

import java.util.ArrayList;
import java.util.List;

public class NetworkObserver {

        private String networkMessage;
        private List<InGameNetworkInterface> inGameNetworkInterfaceList = new ArrayList<>();

        public void addObserver(InGameNetworkInterface inGameNetworkInterface) {
            this.inGameNetworkInterfaceList.add(inGameNetworkInterface);
        }

        public void removeObserver(InGameNetworkInterface inGameNetworkInterface) {
            this.inGameNetworkInterfaceList.remove(inGameNetworkInterface);
        }

        public void setMessage(String networkMessage) {
            this.networkMessage = networkMessage;
            for (InGameNetworkInterface inGameNetworkInterface : this.inGameNetworkInterfaceList) {
                inGameNetworkInterface.update(this.networkMessage);
            }
        }
    }

