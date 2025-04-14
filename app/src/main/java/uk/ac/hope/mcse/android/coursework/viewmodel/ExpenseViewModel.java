package uk.ac.hope.mcse.android.coursework.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.hope.mcse.android.coursework.model.Expense;
import uk.ac.hope.mcse.android.coursework.repository.ExpenseRepository;
public class ExpenseViewModel extends AndroidViewModel{
    private final ExpenseRepository repository;
    private final LiveData<List<Expense>> allExpenses;
    private final LiveData<Double> totalSpent;

    public ExpenseViewModel(@NonNull Application application){
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllExpense();
        totalSpent = repository.getTotalSpent();
    }

    public void insert (Expense expense){
        repository.insert(expense);
    }

    public void update(Expense expense){
        repository.update(expense);
    }

    public void delete(Expense expense){
        repository.delete(expense);
    }

    public void deleteAllExpenses(){
        repository.deleteAllExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses(){
        return allExpenses;
    }

    public LiveData<Double> getTotalSpent(){
        return totalSpent;
    }
}
