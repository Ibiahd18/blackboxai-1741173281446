package com.example.autotrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.autotrader.model.Car;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class CarListFragment extends Fragment implements CarListAdapter.CarClickListener {
    private RecyclerView recyclerView;
    private CarListAdapter adapter;
    private ProgressBar progressBar;
    private TextView noCarsText;
    private List<Car> carList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cars);
        progressBar = view.findViewById(R.id.progress_bar);
        noCarsText = view.findViewById(R.id.text_no_cars);

        setupRecyclerView();
        loadDummyData();

        return view;
    }

    private void setupRecyclerView() {
        carList = new ArrayList<>();
        adapter = new CarListAdapter(requireContext(), carList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void loadDummyData() {
        // Show loading state
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noCarsText.setVisibility(View.GONE);

        // Create dummy data
        carList.clear();
        carList.add(new Car(1, "BMW", "3 Series", 2023, 45000,
                "https://example.com/bmw.jpg", "10,000", "Petrol", "Automatic", "2.0L"));
        carList.add(new Car(2, "Mercedes", "C-Class", 2023, 48000,
                "https://example.com/mercedes.jpg", "5,000", "Diesel", "Automatic", "2.0L"));
        carList.add(new Car(3, "Audi", "A4", 2023, 46000,
                "https://example.com/audi.jpg", "8,000", "Petrol", "Automatic", "2.0L"));
        carList.add(new Car(4, "Tesla", "Model 3", 2023, 52000,
                "https://example.com/tesla.jpg", "2,000", "Electric", "Automatic", "N/A"));
        carList.add(new Car(5, "Toyota", "Camry", 2023, 35000,
                "https://example.com/toyota.jpg", "12,000", "Hybrid", "Automatic", "2.5L"));

        // Update UI
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        if (carList.isEmpty()) {
            noCarsText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCarClick(Car car) {
        // Handle car click - could navigate to detail view
        Snackbar.make(requireView(), "Selected: " + car.toString(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCompareClick(Car car, boolean isChecked) {
        MainActivity activity = (MainActivity) requireActivity();
        if (isChecked) {
            if (activity.getSelectedCars().size() >= 3) {
                Snackbar.make(requireView(), "Can only compare up to 3 cars", Snackbar.LENGTH_SHORT).show();
                // Uncheck the checkbox
                car.setSelected(false);
                adapter.notifyDataSetChanged();
                return;
            }
            activity.addCarToCompare(car);
        } else {
            activity.removeCarFromCompare(car);
        }
    }
}
