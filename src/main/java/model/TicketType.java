package model;

import java.util.List;

public class TicketType {
    int id;
    String type;
    Double price_multiplier;
    List<String> bonuses;

    public TicketType(int id, String type, Double price_multiplier, List<String> bonuses) {
        this.id = id;
        this.type = type;
        this.price_multiplier = price_multiplier;
        this.bonuses = bonuses;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Double getPrice_multiplier() {
        return price_multiplier;
    }

    public List<String> getBonuses() {
        return bonuses;
    }

    public String getBonusesString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bonuses.size(); i++) {
            str.append(bonuses.get(i));
            if(i!=bonuses.size()-1){
                str.append(", ");
            }
        }
        return str.toString();
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price_multiplier=" + price_multiplier +
                ", bonuses=" + bonuses +
                '}';
    }
}
