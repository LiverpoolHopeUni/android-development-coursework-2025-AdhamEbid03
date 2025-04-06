package uk.ac.hope.mcse.android.coursework.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name  = "amount")
    private double amount;

    @ColumnInfo(name  = "category")
    private String category;

    @ColumnInfo(name  = "description")
    private String description;

    @ColumnInfo(name  = "date")
    private String date;

    public Expense(double amount, String category, String description, String date){
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String Category){
        this.category = category;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}
