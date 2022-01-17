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
import androidx.navigation.Navigation;

import com.codeingnight.android.mentalarithmetic.databinding.FragmentWinBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WinFragment extends Fragment {


    public WinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MainViewModel.class);
        FragmentWinBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_win, container, false);
        binding.setData(mainViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_winFragment_to_titleFragment);
            }
        });
        return binding.getRoot();
    }
}
