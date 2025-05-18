package uk.ac.hope.mcse.android.coursework;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.ac.hope.mcse.android.coursework.adapter.ExpenseAdapter;
import uk.ac.hope.mcse.android.coursework.databinding.FragmentFirstBinding;
import uk.ac.hope.mcse.android.coursework.model.Expense;
import uk.ac.hope.mcse.android.coursework.viewmodel.ExpenseViewModel;

/**
 *
 */
public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ExpenseViewModel expenseViewModel;
    private ExpenseAdapter expenseAdapter;

    private void showAddExpenseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Expense");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_expense, null);
        builder.setView(dialogView);

        EditText editAmount = dialogView.findViewById(R.id.editAmount);
        EditText editCategory = dialogView.findViewById(R.id.editCategory);
        EditText editDescription = dialogView.findViewById(R.id.editDescription);
        EditText editDate = dialogView.findViewById(R.id.editDate);

        builder.setPositiveButton("Add",(dialog, which) ->{
            String amountStr = editAmount.getText().toString();
            String category = editCategory.getText().toString();
            String description = editDescription.getText().toString();
            String date = editDate.getText().toString();

            if(!amountStr.isEmpty() && !category.isEmpty() && !date.isEmpty()){
                double amount = Double.parseDouble(amountStr);
                Expense newExpense = new Expense(amount, category, description, date);
                expenseViewModel.insert(newExpense);
            }
        });

        builder.setNegativeButton("Cancel",(dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void updateTotalSpent(List<Expense> expenses){
        double total = 0;
        for(Expense e: expenses){
            total += e.getAmount();
        }

        String formatted = String.format("Total Spent: £%.2f", total);
        binding.textViewTotal.setText(formatted);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expenseAdapter = new ExpenseAdapter();
        LinearLayout categorySummaryContainer = view.findViewById(R.id.categorySummaryContainer);

        RecyclerView recyclerView = binding.recyclerViewExpenses;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(expenseAdapter);

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllExpenses().observe(getViewLifecycleOwner(), expenses -> {
            expenseAdapter.setExpenses(expenses);
            updateTotalSpent(expenses);

            categorySummaryContainer.removeAllViews();

            Map<String,Double> categoryTotals = new HashMap<>();
            for(Expense expense: expenses){
                String category = expense.getCategory();
                double amount = expense.getAmount();
                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0)+ amount);
            }

            for(Map.Entry<String, Double> entry: categoryTotals.entrySet()){
                View cardView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_category_summary,categorySummaryContainer, false);

                TextView categoryName = cardView.findViewById(R.id.textViewCategoryName);
                TextView categoryAmount = cardView.findViewById(R.id.textViewCategoryAmount);

                categoryName.setText(entry.getKey());
                categoryAmount.setText(String.format(Locale.UK, "£%.2f", entry.getValue()));

                categorySummaryContainer.addView(cardView);
            }
        });

        expenseAdapter.setOnItemClickListener(expense -> {
            expenseViewModel.delete(expense);
        });

        binding.buttonHelp.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.HelpFragment);
        });

        binding.fab.setOnClickListener(v -> showAddExpenseDialog());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}