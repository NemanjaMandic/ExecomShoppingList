package com.example.nemus.execomshoppinglist.model;


public class ShoppingListModel {

    private String id;
    private String name;
    private String amount;
    private boolean purchased;


    public void setId(String id){

        this.id=id;
    }
    public String getId(){

        return id;
    }
    public void setName(String name){

        this.name=name;
    }

    public String getName(){

        return name;
    }

    public void setAmount(String amount){

        this.amount=amount;
    }

    public String getAmount(){

        return amount;
    }


    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id);
        sb.append("Name: ").append(name);
        sb.append("Purchased: ").append(purchased);
        return sb.toString();
    }
}
