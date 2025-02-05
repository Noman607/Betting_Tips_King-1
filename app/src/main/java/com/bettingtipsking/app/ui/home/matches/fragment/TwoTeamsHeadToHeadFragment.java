package com.bettingtipsking.app.ui.home.matches.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bettingtipsking.app.Helper.ItemClickListener;
import com.bettingtipsking.app.R;
import com.bettingtipsking.app.adapter.FixturesAdapter;
import com.bettingtipsking.app.api.FixturesRetrofitHelper;
import com.bettingtipsking.app.api.FixturesService;
import com.bettingtipsking.app.databinding.FragmentLiveFixturesBinding;
import com.bettingtipsking.app.databinding.FragmentTwoTeamsHeadToHeadBinding;
import com.bettingtipsking.app.model.FinalFixturesModel;
import com.bettingtipsking.app.model.FinalMatchesModel;
import com.bettingtipsking.app.model.fixtures.Fixture;
import com.bettingtipsking.app.model.fixtures.Goals;
import com.bettingtipsking.app.model.fixtures.League;
import com.bettingtipsking.app.model.fixtures.Response;
import com.bettingtipsking.app.model.fixtures.Score;
import com.bettingtipsking.app.model.fixtures.Teams;
import com.bettingtipsking.app.ui.home.matches.details.MatchDetailsActivity;
import com.bettingtipsking.app.viewmodel.HeadToHeadBetweenTwoTeamsMainViewModel;
import com.bettingtipsking.app.viewmodel.LiveFixturesViewModel;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.HeadToHeadBetweenTwoTeamsModelFactory;
import com.bettingtipsking.app.viewmodel.viewmodelfactory.LiveFixturesViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TwoTeamsHeadToHeadFragment extends BottomSheetDialogFragment implements ItemClickListener {


    RecyclerView recyclerView;
    HeadToHeadBetweenTwoTeamsMainViewModel viewModel;
    Fragment parentFragment;
    FixturesAdapter adapter;
    Map<Integer,Integer> map;
    List<FinalFixturesModel> list;
    String h2h;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_two_teams_head_to_head, null);
        dialog.setContentView(contentView);

        recyclerView = dialog.findViewById(R.id.recyclerView);
        viewModel = new ViewModelProvider(this, new HeadToHeadBetweenTwoTeamsModelFactory(FixturesRetrofitHelper.INSTANCE.getInstance().create(FixturesService.class))).get(HeadToHeadBetweenTwoTeamsMainViewModel.class);

        if (h2h!=null & !h2h.isEmpty())
        viewModel.getHeadToHeadBetweenTwoTeams(h2h);

        map = new HashMap<>();
        list = new ArrayList<>();

        adapter = new FixturesAdapter(getContext(),list,this,true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getHeadToHeadLiveData().observe(this, fixturesModel -> {
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

    }

    public void setTaskId(Fragment parentFragment, String h2h) {
      this.parentFragment = parentFragment;
      this.h2h= h2h;
    }

    @Override
    public void onItemClicked(int position, FinalMatchesModel finalFixturesModel) {
        Intent intent = new Intent(getContext(), MatchDetailsActivity.class);
        intent.putExtra("fixture_id", finalFixturesModel.getFixture().getId());
        intent.putExtra("league_id", list.get(finalFixturesModel.getLeague().getId()));
        intent.putExtra("team_home_id", list.get(finalFixturesModel.getTeams().getHome().getId()));
        intent.putExtra("team_away_id", finalFixturesModel.getTeams().getAway().getId());
        startActivity(intent);
    }

    @Override
    public void onH2HIconClick(int teamHomeId, int teamAwayId) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}