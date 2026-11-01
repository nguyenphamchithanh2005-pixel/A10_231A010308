package vn.edu.vhu.ltdd.a10gallery;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.AnhViewHolder> {

    public interface OnAnhClickListener {
        void onAnhClick(int viTri);
    }

    static class AnhViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgAnh;
        final TextView tvChuThich;

        AnhViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            tvChuThich = itemView.findViewById(R.id.tvChuThich);
        }
    }

    private final List<Integer> anh;
    private final List<String> chuThich;
    private final OnAnhClickListener listener;

    public AnhAdapter(List<Integer> anh, List<String> chuThich, OnAnhClickListener listener) {
        this.anh = anh;
        this.chuThich = chuThich;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("A10_231A010308", "onCreateViewHolder - tạo ô mới");
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anh, parent, false);
        return new AnhViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        holder.imgAnh.setImageResource(anh.get(position));
        holder.tvChuThich.setText(chuThich.get(position));

        // NC2: chiều cao khác nhau cho lưới so le
        ViewGroup.LayoutParams lp = holder.imgAnh.getLayoutParams();
        float d = holder.itemView.getResources().getDisplayMetrics().density;
        int[] cao = {180, 120, 150};
        lp.height = (int) (cao[position % 3] * d);
        holder.imgAnh.setLayoutParams(lp);

        holder.itemView.setOnClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) listener.onAnhClick(p);
        });

        // NC4: giữ lâu để xóa
        holder.itemView.setOnLongClickListener(v -> {
            int p = holder.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) xoa(p, v);
            return true;
        });
    }

    private void xoa(int p, View v) {
        String ten = chuThich.get(p);
        anh.remove(p);
        chuThich.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, anh.size() - p);
        Toast.makeText(v.getContext(),
                v.getContext().getString(R.string.da_xoa, ten),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return anh.size();
    }
}