package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.adapter.EventsAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentEventsBinding;
import com.bettingtipsking.app.model.events.EventsModel;
import com.bettingtipsking.app.model.events.Response;
import com.bettingtipsking.app.viewmodel.EventsViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.EventsViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class EventsFragment extends Fragment {

    FragmentEventsBinding binding;
    EventsViewModel viewModel;
    EventsAdapter adapter;
    List<EventsModel> list;
    List<Response> responseList;
    int fixture_id;

    public EventsFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
        this.fixture_id = fixture_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this, new EventsViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(EventsViewModel.class);
        viewModel.getEventsByFixture(fixture_id);

        list = new ArrayList<>();
        responseList = new ArrayList<>();


        viewModel.getEventsLiveData().observe(getViewLifecycleOwner(),eventsModel -> {

            responseList = eventsModel.getResponse();

            adapter = new EventsAdapter(getContext(),responseList);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);

        });

        return binding.getRoot();
    }
}