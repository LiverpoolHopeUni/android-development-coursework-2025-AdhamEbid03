package uk.ac.hope.mcse.android.coursework.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.ac.hope.mcse.android.coursework.model.Expense;

@Database(entities = {Expense.class}, version = 1, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase{

    private static ExpenseDatabase instance;

    public abstract ExpenseDao expenseDao();

    public static synchronized ExpenseDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ExpenseDatabase.class,
                    "expense_database"
            )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}
