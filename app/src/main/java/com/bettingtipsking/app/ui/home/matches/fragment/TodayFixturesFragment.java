package com.bettingtipsking.app.ui.home.matches.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentLiveFixturesBinding;
import com.bettingtipsking.app.databinding.FragmentTodayFixturesBinding;
import com.bettingtipsking.app.model.FinalFixturesModel;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Fixture;
import com.bettingtipsking.app.model.fixtures.Goals;
import com.bettingtipsking.app.model.fixtures.League;
import com.bettingtipsking.app.model.fixtures.Response;
import com.bettingtipsking.app.model.fixtures.Score;
import com.bettingtipsking.app.model.fixtures.Teams;
import com.bettingtipsking.app.viewmodel.LiveFixturesViewModel;
import com.bettingtipsking.app.viewmodel.TodayFixturesViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LiveFixturesViewModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.TodayFixturesViewModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TodayFixturesFragment extends Fragment implements ItemClickListener {
    FragmentTodayFixturesBinding binding;
    TodayFixturesViewModel viewModel;
    FixturesAdapter adapter;
    Map<Integer,Integer> map;
    List<FinalFixturesModel> list;

    public TodayFixturesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodayFixturesBinding.inflate(inflater, container, false);


        viewModel = new ViewModelProvider(this, new TodayFixturesViewModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(TodayFixturesViewModel.class);
        viewModel.getTodayFixtures("2022-03-27");

        map = new HashMap<>();
        list = new ArrayList<>();

        adapter = new FixturesAdapter(getContext(),list,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getTodayMatchLiveData().observe(getViewLifecycleOwner(), fixturesModel -> {
            if (fixturesModel != null) {
                for (int i = 0; i < fixturesModel.getResponse().size(); i++) {

                    List<Response> response = fixturesModel.getResponse();
                    Fixture fixture = fixturesModel.getResponse().get(i).getFixture();
                    League league = response.get(i).getLeague();
                    Goals goals = fixturesModel.getResponse().get(i).getGoals();
                    Score score = fixturesModel.getResponse().get(i).getScore();
                    Teams teams = fixturesModel.getResponse().get(i).getTeams();

                    com.bettingtipsking.app.model.FinalFixturesModel finalFixturesModel = new com.bettingtipsking.app.model.FinalFixturesModel(league,new ArrayList<>());
                    FinalMatchesModel finalMatchDetailsModel = new FinalMatchesModel(fixture,league,goals,score,teams);
                    if (!map.containsKey(league.getId())){
                        list.add(finalFixturesModel);
                        map.put(league.getId(),list.indexOf(finalFixturesModel));
                    }
                    list.get(map.get(league.getId())).getMatches().add(finalMatchDetailsModel);
                }

                if (list != null && !list.isEmpty()) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onItemClicked(String position, Object obj) {

    }
}