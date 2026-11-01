package vn.edu.vhu.ltdd.a10gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A10_231A010308";
    public static final String EXTRA_VI_TRI = "extra_vi_tri";
    public static final String EXTRA_DS_ANH = "extra_ds_anh";

    public static final int[] ANH = {
            R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4,
            R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8,
    };

    private final List<Integer> dsAnh = new ArrayList<>();
    private final List<String> chuThich = new ArrayList<>();
    private AnhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        for (int lan = 0; lan < 4; lan++) {
            for (int i = 0; i < ANH.length; i++) {
                dsAnh.add(ANH[i]);
                chuThich.add(getString(R.string.anh_so, i + 1));
            }
        }

        RecyclerView rv = findViewById(R.id.rvAnh);
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new AnhAdapter(dsAnh, chuThich, viTri -> {
            Log.d(TAG, "Mở ảnh lớn tại vị trí " + viTri);
            int[] ds = new int[dsAnh.size()];
            for (int k = 0; k < ds.length; k++) ds[k] = dsAnh.get(k);
            Intent i = new Intent(this, ViewerActivity.class);
            i.putExtra(EXTRA_VI_TRI, viTri);
            i.putExtra(EXTRA_DS_ANH, ds);
            startActivity(i);
        });
        rv.setAdapter(adapter);

        findViewById(R.id.btnDoiCot).setOnClickListener(v -> doiSoCot(rv));
    }

    private void doiSoCot(RecyclerView rv) {
        RecyclerView.LayoutManager m = rv.getLayoutManager();
        int soCot;
        if (m instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) m;
            soCot = lm.getSpanCount() + 1;
            if (soCot > 4) soCot = 2;
            lm.setSpanCount(soCot);
        } else if (m instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) m;
            soCot = lm.getSpanCount() + 1;
            if (soCot > 4) soCot = 2;
            lm.setSpanCount(soCot);
        } else {
            return;
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(this, getString(R.string.so_cot, soCot), Toast.LENGTH_SHORT).show();
    }
}

// commit 2