package com.example.autotrader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.autotrader.model.Car;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarViewHolder> {
    private final Context context;
    private final List<Car> cars;
    private final CarClickListener listener;

    public interface CarClickListener {
        void onCarClick(Car car);
        void onCompareClick(Car car, boolean isChecked);
    }

    public CarListAdapter(Context context, List<Car> cars, CarClickListener listener) {
        this.context = context;
        this.cars = cars;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        
        // Set car image
        Glide.with(context)
            .load(car.getImageUrl())
            .placeholder(R.drawable.car_placeholder)
            .error(R.drawable.car_error)
            .centerCrop()
            .into(holder.carImage);

        // Set car title (year make model)
        holder.carTitle.setText(String.format("%d %s %s", car.getYear(), car.getMake(), car.getModel()));

        // Format and set price
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        holder.carPrice.setText(currencyFormat.format(car.getPrice()));

        // Set car details
        String details = String.format("%s • %s • %s miles", 
            car.getTransmission(), 
            car.getEngineSize(), 
            car.getMileage());
        holder.carDetails.setText(details);

        // Set compare checkbox state
        holder.compareCheckbox.setChecked(car.isSelected());

        // Set click listeners
        holder.itemView.setOnClickListener(v -> listener.onCarClick(car));
        holder.compareCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> 
            listener.onCompareClick(car, isChecked));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView carTitle;
        TextView carPrice;
        TextView carDetails;
        CheckBox compareCheckbox;

        CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.image_car);
            carTitle = itemView.findViewById(R.id.text_car_title);
            carPrice = itemView.findViewById(R.id.text_car_price);
            carDetails = itemView.findViewById(R.id.text_car_details);
            compareCheckbox = itemView.findViewById(R.id.checkbox_compare);
        }
    }
}
