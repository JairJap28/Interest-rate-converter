package com.example.economyapp.Tests;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.economyapp.FeeCalculation.entities.EntityProcessFeeCalculation;
import com.example.economyapp.R;

import java.util.Formatter;
import java.util.Locale;

public class DynamicTable extends LinearLayout {
    //region Properties
    private TableLayout table;
    private EntityProcessFeeCalculation result;

    public DynamicTable(Context context) {
        super(context);
    }
    //endregion

    public void setResult(EntityProcessFeeCalculation result) {
        this.result = result;
    }

    public void initComponents() {
        initTableContent();
        createHeader();
        addContentToTable();
        addTableToMainLayout();
    }

    private void initTableContent() {
        table = new TableLayout(getContext());
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(15, 10, 15, 0);
        table.setLayoutParams(tableRowParams);
        table.setPadding(10, 10, 10, 10);
        table.setBackgroundColor(Color.WHITE);
    }

    private void createHeader() {
        TableRow rowTitle = new TableRow(getContext());
        rowTitle.setBackgroundColor(Color.rgb(200, 200, 200));
        rowTitle.addView(createTextViewHeader("#"));
        rowTitle.addView(createTextViewHeader("Valor presente"));
        rowTitle.addView(createTextViewHeader("Valor cuota..."));
        rowTitle.addView(createTextViewHeader("n"));

        table.addView(rowTitle);
    }

    private void addContentToTable() {
        for (int i = 0; i < result.getDueDetails().size(); i++) {
            TableRow row = new TableRow(getContext());
            row.addView(createTextViewContent("%d", Integer.toString(i + 1)));
            row.addView(createTextViewContent("$ %.1f", new Formatter().format("%8.1f", result.getDueDetails().get(i).getPresentValue()).toString()));
            row.addView(createTextViewContent("$ %.1f", new Formatter().format("%8.1f", result.getDueDetails().get(i).getValueDue()).toString()));
            row.addView(createTextViewContent("%.0f", new Formatter().format("%8.0f", result.getDueDetails().get(i).getnPeriod()).toString()));
            table.addView(row);
        }
    }

    private void addTableToMainLayout() {
        this.addView(table);
    }

    private TextView createTextViewHeader(String label) {
        TextView textView = new TextView(getContext());
        textView.setText(label);
        textView.setTypeface(Typeface.SERIF, Typeface.BOLD);
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        return textView;
    }

    private TextView createTextViewContent(String label, String format) {
        TextView textView = new TextView(getContext());
        textView.setText(String.format(Locale.ENGLISH, format, label));
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setBackgroundResource(R.drawable.table_divider);
        return textView;
    }
}
