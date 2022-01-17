package com.codeingnight.android.mentalarithmetic;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.codeingnight.android.mentalarithmetic.databinding.FragmentTitleBinding;

public class TitleFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MainViewModel.class);
        FragmentTitleBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false);
        binding.setData(mainViewModel);
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }
}
