package uk.ac.hope.mcse.android.coursework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.hope.mcse.android.coursework.R;
import uk.ac.hope.mcse.android.coursework.model.Expense;
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position){
        Expense current = expenseList.get(position);
        holder.textViewCategory.setText(current.getCategory());
        holder.textViewAmount.setText("£" + String.format("%.2f", current.getAmount()));
        holder.textViewDescription.setText(current.getDescription());
        holder.textViewDate.setText(current.getDate());
    }

    @Override
    public int getItemCount(){
        return expenseList.size();
    }

    public void setExpenses(List<Expense> expenses){
        this.expenseList = expenses;
        notifyDataSetChanged();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder{
        TextView textViewCategory, textViewAmount, textViewDescription, textViewDate;

        public ExpenseViewHolder(@NonNull View itemView){
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
