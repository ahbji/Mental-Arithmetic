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

import com.codeingnight.android.mentalarithmetic.databinding.FragmentLoseBinding;


public class LoseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MainViewModel.class);
        FragmentLoseBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lose, container, false);
        binding.setData(mainViewModel);
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }
}
