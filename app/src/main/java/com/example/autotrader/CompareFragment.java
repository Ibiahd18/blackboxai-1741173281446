package com.example.autotrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.autotrader.model.Car;
import com.google.android.material.button.MaterialButton;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CompareFragment extends Fragment {
    private TextView noCarText;
    private LinearLayout compareContainer;
    private TableLayout specsTable;
    private MaterialButton clearButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare, container, false);

        noCarText = view.findViewById(R.id.text_no_cars);
        compareContainer = view.findViewById(R.id.layout_compare_container);
        specsTable = view.findViewById(R.id.table_specs);
        clearButton = view.findViewById(R.id.button_clear);

        clearButton.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).clearSelectedCars();
            updateComparison();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateComparison();
    }

    private void updateComparison() {
        List<Car> selectedCars = ((MainActivity) requireActivity()).getSelectedCars();
        
        compareContainer.removeAllViews();
        specsTable.removeAllViews();

        if (selectedCars.isEmpty()) {
            noCarText.setVisibility(View.VISIBLE);
            specsTable.setVisibility(View.GONE);
            clearButton.setVisibility(View.GONE);
            return;
        }

        noCarText.setVisibility(View.GONE);
        specsTable.setVisibility(View.VISIBLE);
        clearButton.setVisibility(View.VISIBLE);

        // Add car cards to horizontal scroll
        for (Car car : selectedCars) {
            View cardView = createCarCard(car);
            compareContainer.addView(cardView);
        }

        // Create specification comparison table
        createSpecsTable(selectedCars);
    }

    private View createCarCard(Car car) {
        View cardView = getLayoutInflater().inflate(R.layout.item_car, compareContainer, false);
        
        // Set fixed width for comparison cards
        int cardWidth = getResources().getDisplayMetrics().widthPixels / 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cardWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(params);

        // Hide compare checkbox in comparison view
        cardView.findViewById(R.id.checkbox_compare).setVisibility(View.GONE);

        // Set car details
        ImageView carImage = cardView.findViewById(R.id.image_car);
        TextView titleText = cardView.findViewById(R.id.text_car_title);
        TextView priceText = cardView.findViewById(R.id.text_car_price);
        TextView detailsText = cardView.findViewById(R.id.text_car_details);

        Glide.with(this)
            .load(car.getImageUrl())
            .placeholder(R.drawable.car_placeholder)
            .error(R.drawable.car_error)
            .centerCrop()
            .into(carImage);

        titleText.setText(String.format("%d %s %s", car.getYear(), car.getMake(), car.getModel()));
        priceText.setText(NumberFormat.getCurrencyInstance(Locale.US).format(car.getPrice()));
        detailsText.setText(String.format("%s • %s • %s miles", 
            car.getTransmission(), car.getEngineSize(), car.getMileage()));

        return cardView;
    }

    private void createSpecsTable(List<Car> cars) {
        // Add header row
        TableRow headerRow = new TableRow(requireContext());
        headerRow.addView(createTableCell("Specifications", true));
        for (Car car : cars) {
            headerRow.addView(createTableCell(car.getMake() + " " + car.getModel(), true));
        }
        specsTable.addView(headerRow);

        // Add specification rows
        addSpecRow("Year", cars, car -> String.valueOf(car.getYear()));
        addSpecRow("Price", cars, car -> NumberFormat.getCurrencyInstance(Locale.US).format(car.getPrice()));
        addSpecRow("Mileage", cars, car -> car.getMileage() + " miles");
        addSpecRow("Fuel Type", cars, Car::getFuelType);
        addSpecRow("Transmission", cars, Car::getTransmission);
        addSpecRow("Engine", cars, Car::getEngineSize);
    }

    private TextView createTableCell(String text, boolean isHeader) {
        TextView cell = new TextView(requireContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);
        cell.setLayoutParams(params);
        cell.setPadding(16, 16, 16, 16);
        cell.setText(text);
        if (isHeader) {
            cell.setTextSize(16);
            cell.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        return cell;
    }

    private void addSpecRow(String specName, List<Car> cars, CarSpecGetter specGetter) {
        TableRow row = new TableRow(requireContext());
        row.addView(createTableCell(specName, false));
        for (Car car : cars) {
            row.addView(createTableCell(specGetter.getSpec(car), false));
        }
        specsTable.addView(row);
    }

    private interface CarSpecGetter {
        String getSpec(Car car);
    }
}
