package uk.ac.hope.mcse.android.coursework.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

import uk.ac.hope.mcse.android.coursework.model.Expense;
@Dao
public interface ExpenseDao {

    @Insert
    void insertExpense (Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("DELETE FROM expenses")
    void deleteAllExpenses();

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    LiveData<List<Expense>> getExpensesByCategory(String category);

    @Query("SELECT SUM(amount) FROM expenses")
    LiveData<Double> getTotalSpent();

    @Query("SELECT SUM(amount) FROM expenses WHERE category = :category")
    LiveData<Double> getTotalSpentByCategory(String category);
}
