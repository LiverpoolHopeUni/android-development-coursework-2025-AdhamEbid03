package uk.ac.hope.mcse.android.coursework.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.hope.mcse.android.coursework.database.ExpenseDao;
import uk.ac.hope.mcse.android.coursework.database.ExpenseDatabase;
import uk.ac.hope.mcse.android.coursework.model.Expense;
public class ExpenseRepository {

    private final ExpenseDao expenseDao;
    private final LiveData<List<Expense>> allExpense;
    private final LiveData<Double> totalSpent;

    public ExpenseRepository(Application application){
        ExpenseDatabase database = ExpenseDatabase.getInstance(application);
        expenseDao = database.expenseDao();
        allExpense = expenseDao.getAllExpenses();
        totalSpent = expenseDao.getTotalSpent();
    }

    public void insert(Expense expense){
        ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseDao.insertExpense(expense));
    }
    public void update(Expense expense){
        ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseDao.updateExpense(expense));
    }
    public void delete(Expense expense){
        ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseDao.deleteExpense(expense));
    }

    public void deleteAllExpenses(){
        ExpenseDatabase.databaseWriteExecutor.execute(expenseDao::deleteAllExpenses);
    }

    public LiveData<List<Expense>> getAllExpense(){
        return allExpense;
    }

    public LiveData<Double> getTotalSpent(){
        return totalSpent;
    }

}
