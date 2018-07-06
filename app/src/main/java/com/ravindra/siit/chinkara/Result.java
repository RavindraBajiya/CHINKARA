package com.ravindra.siit.chinkara;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ravindra.siit.chinkara.Adapter.ResultAdapter;
import com.ravindra.siit.chinkara.Adapter.ResultListAdapter;
import com.ravindra.siit.chinkara.DataObj.DataModel;
import com.ravindra.siit.chinkara.DataObj.QuestionStudentResponse;
import com.ravindra.siit.chinkara.DataObj.QuestionsObj;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {
    ListView listView;
    TableLayout tableLayout;
    ArrayList<QuestionsObj> questionsObjs;
    int question;
    ListView listView1;
    ArrayList<QuestionStudentResponse> questionStudentResponses;
    Button goToListBtn;
//    ScrollView scrollView;
    TextView totQue;
    TextView attQue;
    TextView rigQue;
    TextView wroQue;
    TextView totMark;
//    TextView negMark;
    TextView oveResult;
//    TextView oveResultMarks;
    float positive;
    float negative;
    float getMarks;
    float minusMarks;
    float oveMarks;

    PieChart pieChart;
    public static final int[] COLORS = {Color.rgb(169, 17, 17),
            Color.rgb(0, 153, 51),
            Color.rgb(181, 184, 204)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Result");
        tableLayout = findViewById(R.id.tableLayout);
        totQue = findViewById(R.id.maxQueTV);
        attQue = findViewById(R.id.attQueTV);
        rigQue = findViewById(R.id.rigQueTV);
        wroQue = findViewById(R.id.wroQueTV);
        totMark = findViewById(R.id.totMarTV);
        oveResult = findViewById(R.id.resultTV);
        goToListBtn = findViewById(R.id.goListButton);
        Intent intent = getIntent();
        questionsObjs = intent.getParcelableArrayListExtra("totQues");
        question = intent.getIntExtra("question", 0);
        questionStudentResponses = intent.getParcelableArrayListExtra("questionList");
        ArrayList<QuestionsObj> questionsObjs1 = new ArrayList<>();
        for (int i=0;i<question;i++){
            questionsObjs1.add(i,questionsObjs.get(i));
        }
        ResultAdapter resultAdapter = new ResultAdapter(this, questionStudentResponses, questionsObjs1);
        Toast.makeText(this, "question obj == "+questionsObjs.size(), Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.resultList);
        listView1 = findViewById(R.id.resultList1);
        listView.setAdapter(resultAdapter);
        positive = intent.getFloatExtra("positive",0);
        negative = intent.getFloatExtra("negative",0);
        setResultData();
    }

    private void setPiaChart(int tot,int att,int right) {
        pieChart = findViewById(R.id.piaChart);
        pieChart.setRotationEnabled(false);
        pieChart.setUsePercentValues(true);

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        int notatt = tot-att;
        int wrong = att - right;
        right = (right*100/tot);
        notatt = notatt*100/tot;
        wrong = wrong*100/tot;
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(wrong, 0));
        yvalues.add(new Entry(right, 1));
        yvalues.add(new Entry(notatt, 2));
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Wrong");
        xVals.add("Right");
        xVals.add("Not Attempt");
//        PieData data = new PieData(xVals, dataSet);
        PieData data = new PieData(xVals,dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(0f);

        dataSet.setColors(COLORS);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;
                Log.i("VAL SELECTED",
                        "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                                + ", DataSet index: " + dataSetIndex);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        pieChart.animateXY(1400, 1400);

    }

    private void setResultData() {
//        maxQue.setText(question + "");

        int attempt=0;
        for (int i=0;questionStudentResponses.size()>i;i++){
            if (questionStudentResponses.get(i).getAttempt()==1){
                attempt++;
            }
        }
//        attQue.setText(attempt + "");
        int right = 0;
        int wrong = 0;
        for (int i=0;i<attempt;i++){
            if (questionStudentResponses.get(i).getAttempt()==1) {
                if (questionStudentResponses.get(i).getRealAns().trim().equals(questionStudentResponses.get(i).getUserAns().trim())) {
                    right++;
                }
            }
        }
        wrong = attempt-right;
//        rigQue.setText(right+"");
//        wroQue.setText(wrong+"");
        getMarks = positive*right;
        minusMarks = negative*wrong;
        oveMarks = getMarks-minusMarks;
        float result = (positive*right-wrong*negative)*100/(question*positive);
//        posMark.setText(getMarks+"");
//        negMark.setText(minusMarks+"");
//        oveResultMarks.setText(oveMarks+"/"+question*positive);
//        oveResult.setText(result+"%");
        String total = oveMarks+"/"+question*positive;
        setPiaChart(question,attempt,right);
        totQue.setText(question+"");
        attQue.setText(attempt+"");
        rigQue.setText(right+"");
        wroQue.setText(wrong+"");
        totMark.setText(total+"");
        oveResult.setText(result+"%");
        /*String arrResult[] = {question+"",attempt+"",right+"",wrong+"",getMarks+"",minusMarks+"",total,result+"%"};
        String arrName[] = getResources().getStringArray(R.array.resultItems);
        ArrayList<DataModel> dataModels = new ArrayList<>();
        DataModel temp;
        for (int i = 0;i<arrName.length;i++){
            temp = new DataModel(arrName[i],arrResult[i]);
            dataModels.add(temp);
        }
        ResultListAdapter adapter = new ResultListAdapter(dataModels,this);
        listView1.setAdapter(adapter);*/
    }

    public void goToList(View view) {
        if (listView.getVisibility() == View.VISIBLE) {
            listView.setVisibility(View.GONE);
            goToListBtn.setText("Get Detail...");
//            listView1.setVisibility(View.VISIBLE);
            tableLayout.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.VISIBLE);
        } else {
//            listView1.setVisibility(View.GONE);
            tableLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            goToListBtn.setText("Go Back...");
            pieChart.setVisibility(View.GONE);
        }
    }

    public void goHome(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}