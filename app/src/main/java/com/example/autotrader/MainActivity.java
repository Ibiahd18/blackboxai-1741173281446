package com.example.autotrader;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.List;
import com.example.autotrader.model.Car;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private List<Car> selectedCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize selected cars list
        selectedCars = new ArrayList<>();

        // Setup bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(new CarListFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        
        if (item.getItemId() == R.id.navigation_home) {
            fragment = new CarListFragment();
        } else if (item.getItemId() == R.id.navigation_compare) {
            fragment = new CompareFragment();
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
            return true;
        }
        return false;
    }

    // Methods to manage car selection for comparison
    public void addCarToCompare(Car car) {
        if (!selectedCars.contains(car) && selectedCars.size() < 3) {
            selectedCars.add(car);
            car.setSelected(true);
        }
    }

    public void removeCarFromCompare(Car car) {
        selectedCars.remove(car);
        car.setSelected(false);
    }

    public List<Car> getSelectedCars() {
        return selectedCars;
    }

    public void clearSelectedCars() {
        for (Car car : selectedCars) {
            car.setSelected(false);
        }
        selectedCars.clear();
    }
}
