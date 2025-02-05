package com.bettingtipsking.app.ui.home.matches.details.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;

import com.bettingtipsking.app.databinding.FragmentH2HBinding;
import com.bettingtipsking.app.model.FinalFixturesModel;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Fixture;
import com.bettingtipsking.app.model.fixtures.Goals;
import com.bettingtipsking.app.model.fixtures.League;
import com.bettingtipsking.app.model.fixtures.Response;
import com.bettingtipsking.app.model.fixtures.Score;
import com.bettingtipsking.app.model.fixtures.Teams;
import com.bettingtipsking.app.viewmodel.H2HViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.H2HModelFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class H2HFragment extends Fragment implements ItemClickListener {

    FragmentH2HBinding binding;
    H2HViewModel viewModel;
    FixturesAdapter adapter;
    Map<Integer, Integer> map;
    List<FinalFixturesModel> list;
    int team_home_id;
    int team_away_id;

    public H2HFragment(int fixture_id, int league_id, int team_home_id, int team_away_id) {
        this.team_home_id = team_home_id;
        this.team_away_id = team_away_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentH2HBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new H2HModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(H2HViewModel.class);
        //todo date

        viewModel.getHeadToHeadBetweenTwoTeams(team_home_id+"-"+team_away_id);

        map = new HashMap<>();
        list = new ArrayList<>();

        adapter = new FixturesAdapter(getContext(), list, this, true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getHeadToHeadLiveData().observe(getViewLifecycleOwner(), fixturesModel -> {
            if (fixturesModel != null) {
                for (int i = 0; i < fixturesModel.getResponse().size(); i++) {
                    List<Response> response = fixturesModel.getResponse();
                    Fixture fixture = fixturesModel.getResponse().get(i).getFixture();
                    League league = response.get(i).getLeague();
                    Goals goals = fixturesModel.getResponse().get(i).getGoals();
                    Score score = fixturesModel.getResponse().get(i).getScore();
                    Teams teams = fixturesModel.getResponse().get(i).getTeams();

                    com.bettingtipsking.app.model.FinalFixturesModel finalFixturesModel = new com.bettingtipsking.app.model.FinalFixturesModel(league, new ArrayList<>());
                    FinalMatchesModel finalMatchDetailsModel = new FinalMatchesModel(fixture, league, goals, score, teams);
                    if (!map.containsKey(league.getId())) {
                        list.add(finalFixturesModel);
                        map.put(league.getId(), list.indexOf(finalFixturesModel));
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
    public void onItemClicked(int position, FinalMatchesModel finalFixturesModel) {

    }

    @Override
    public void onH2HIconClick(int teamHomeId, int teamAwayId) {

    }
}