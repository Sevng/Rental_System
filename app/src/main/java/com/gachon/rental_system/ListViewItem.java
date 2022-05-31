package com.gachon.rental_system;

public class ListViewItem {

    private String Name;
    private String RentalDate;
    private String ReturnDate;

    public void setName(String name){
        Name = name;
    }
    public void setRentalDate(String rentalDate){
        RentalDate = rentalDate;
    }
    public void setReturnDate(String returnDate){
        ReturnDate = returnDate;
    }

    public String getName(){
        return this.Name;
    }
    public String getRentalDate(){
        return this.RentalDate;
    }
    public String getReturnDate(){
        return this.ReturnDate;
    }
}
