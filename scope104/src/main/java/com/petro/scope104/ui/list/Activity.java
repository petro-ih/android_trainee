package com.petro.scope104.ui.list;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.petro.scope104.R;
import com.petro.scope104.databinding.ActivityScope104Binding;
import com.petro.scope104.db.DataBase;
import com.petro.scope104.db.entity.UserEntity;
import com.petro.scope104.network.RetrofitInstance;
import com.petro.scope104.ui.WorkerUi;
import com.petro.scope104.ui.details.UserDetailsActivity;
import com.petro.scope104.ui.select.SelectBottomSheetFragment;
import com.petro.scope104.ui.select.SelectItem;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Activity extends AppCompatActivity implements WorkerListFragment.WorkerListInteractions, SelectBottomSheetFragment.OnItemSelected, WorkerListFragment.ListFilter {
    private static final int REQUEST_CODE_GENDER = 1;
    private static final int REQUEST_CODE_COUNTRY = 2;
    private final HashSet<String> currentSelectedCountries = new HashSet<>();
    private Gender currentSelectedGender = Gender.UNKNOWN;
    private ActivityScope104Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScope104Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager viewPager = binding.pager;
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        binding.test.setOnClickListener(v -> {

        });
    }

    @Override
    public void onItemClick(WorkerUi workerUi, ActivityOptions activityOptions) {
        UserDetailsActivity.start(this, workerUi, activityOptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.gender: {
                List<SelectItem> items = Arrays
                        .stream(Gender.values())
                        .filter(gen -> gen != Gender.UNKNOWN)
                        .map(gen -> new SelectItem(gen.displayName, currentSelectedGender == gen, gen))
                        .collect(Collectors.toList());

                fragment = SelectBottomSheetFragment.newInstance(REQUEST_CODE_GENDER, "SELECT SEX", items);
            }
            break;
            case R.id.city: {
                List<SelectItem> items = RetrofitInstance.INSTANCE.supportLocations.stream()
                        .map(current -> {
                            Locale locale = new Locale("", current);
                            return new SelectItem(locale.getDisplayCountry(), currentSelectedCountries.contains(current), current);
                        })
                        .collect(Collectors.toList());
                fragment = SelectBottomSheetFragment.newInstance(REQUEST_CODE_COUNTRY, "Select Country", items);
            }
            break;
            default:
                return false;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager();
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(int requestCode, List<SelectItem> items) {
        switch (requestCode) {
            case REQUEST_CODE_GENDER:
                int checkedCount = 0;
                for (SelectItem item : items) {
                    if (item.getValue()) {
                        checkedCount++;
                        currentSelectedGender = ((Gender) item.getObject());
                    }
                }
                if (checkedCount == 0 || checkedCount == 2) {
                    currentSelectedGender = Gender.UNKNOWN;
                }
                break;
            case REQUEST_CODE_COUNTRY:
                List<String> selected = items
                        .stream()
                        .filter(SelectItem::getValue)
                        .map(SelectItem::getObject)
                        .map(Object::toString)
                        .collect(Collectors.toList());
                currentSelectedCountries.clear();
                currentSelectedCountries.addAll(selected);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
        for (int i = 0; i < Objects.requireNonNull(binding.pager.getAdapter()).getCount(); i++) {
            Object object = binding.pager.getAdapter().instantiateItem(binding.pager, i);
            if (object instanceof WorkerListFragment) {
                ((WorkerListFragment) object).refresh();
            }
        }
    }

    @Override
    public Gender getCurrentSelectedGender() {
        return currentSelectedGender;
    }

    @Override
    public Set<String> getCountries() {
        return currentSelectedCountries;
    }

}

