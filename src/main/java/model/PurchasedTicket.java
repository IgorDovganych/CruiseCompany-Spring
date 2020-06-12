package model;

import java.util.List;

public class PurchasedTicket {
    int cruiseId;
    model.Cruise cruise;
    List<Excursion> purchasedExcursions;
    String ticketType;

    public PurchasedTicket(int cruiseId, List<Excursion> purchasedExcursions, String ticketType) {
        this.cruiseId = cruiseId;
        this.purchasedExcursions = purchasedExcursions;
        this.ticketType = ticketType;
    }

    public String getPurchasedExcursionsString(){

        StringBuilder str = new StringBuilder();
        for(int i=0;i<purchasedExcursions.size();i++){
            if(i!=0){
                str.append(", ");
            }
            str.append(purchasedExcursions.get(i).getName());
        }
        return str.toString();
    }
    public int getCruiseId() {
        return cruiseId;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public List<Excursion> getPurchasedExcursions() {
        return purchasedExcursions;
    }

    public String getTicketType() {
        return ticketType;
    }


    @Override
    public String toString() {
        return "PurchasedTicket{" +
                "cruiseId=" + cruiseId +
//                ", cruise=" + cruise +
                ", purchasedExcursions=" + purchasedExcursions +
                ", ticketType='" + ticketType + '\'' +
                '}';
    }

    public void setCruise(model.Cruise cruise) {
        this.cruise = cruise;
    }
}
