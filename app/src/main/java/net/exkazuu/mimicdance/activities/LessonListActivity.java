package net.exkazuu.mimicdance.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.exkazuu.mimicdance.LessonListAdapter;
import net.exkazuu.mimicdance.Lessons;

import java.util.ArrayList;
import java.util.List;

public class LessonListActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeList();
    }

    private void makeList() {
        List<String> lessonList = new ArrayList<>();
        int lessonCount = Lessons.getLessonCount();
        for (int i = 1; i <= lessonCount; i++) {
            lessonList.add("Lesson" + i);
        }
        LessonListAdapter listAdapter = new LessonListAdapter(
            this, android.R.layout.simple_list_item_1, lessonList);
        setListAdapter(listAdapter);
    }

    /**
     * Listが選択された時の動作
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(this, CoccoActivity.class);
        intent.putExtra("lessonNumber", position + 1);
        startActivity(intent);
    }
}
