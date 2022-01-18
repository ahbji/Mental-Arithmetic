package com.codeingnight.android.mentalarithmetic;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Objects;
import java.util.Random;

public class MainViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private static String KEY_HIGH_SCORE = "key_high_score";
    private static String KEY_LEFT_NUMBER = "key_left_number";
    private static String KEY_RIGHT_NUMBER = "key_right_number";
    private static String KEY_OPERATOR = "key_operator";
    private static String KEY_ANSWER = "key_answer";
    private static String SAVE_SHP_DATA_NAME = "save_shp_data_name";
    private static String KEY_CURRENT_SCORE = "key_current_score";
    private static String KEY_CURRENT_INPUT = "key_current_input";
    boolean win_flag = false;

    private final StringBuilder builder = new StringBuilder();

    public MainViewModel(Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0));
            handle.set(KEY_LEFT_NUMBER, 0);
            handle.set(KEY_RIGHT_NUMBER, 0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_ANSWER, 0);
            handle.set(KEY_CURRENT_INPUT, getApplication().getString(R.string.input_indicator));
            handle.set(KEY_CURRENT_SCORE, 0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<String> getCurrentInput() {
        return handle.getLiveData(KEY_CURRENT_INPUT);
    }

    public MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(KEY_ANSWER);
    }

    void generator() {
        int LEVEL = 20;
        Random random = new Random();
        int x, y;
        x = random.nextInt(LEVEL) + 1;
        y = random.nextInt(LEVEL) + 1;
        if (x % 2 == 0) {
            getOperator().setValue("+");
            if (x > y) {
                getAnswer().setValue(x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x - y);
            } else {
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y - x);
            }

        } else {
            getOperator().setValue("-");
            if (x > y) {
                getAnswer().setValue(x - y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            } else {
                getAnswer().setValue(y - x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    void save() {
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putInt(KEY_HIGH_SCORE, getHighScore().getValue());
        editor.apply();
    }

    @SuppressWarnings("ConstantConditions")
    void answerCorrect() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generator();
    }

    public void startChallenge(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_titleFragment_to_questionFragment);
        getCurrentScore().setValue(0);
        generator();
    }

    public void numberInput(View view) {
        int id = view.getId();
        if (id == R.id.button0) {
            builder.append("0");
        } else if (id == R.id.button1) {
            builder.append("1");
        } else if (id == R.id.button2) {
            builder.append("2");
        } else if (id == R.id.button3) {
            builder.append("3");
        } else if (id == R.id.button4) {
            builder.append("4");
        } else if (id == R.id.button5) {
            builder.append("5");
        } else if (id == R.id.button6) {
            builder.append("6");
        } else if (id == R.id.button7) {
            builder.append("7");
        } else if (id == R.id.button8) {
            builder.append("8");
        } else if (id == R.id.button9) {
            builder.append("9");
        } else if (id == R.id.buttonClear) {
            builder.setLength(0);
        }
        if (builder.length() == 0) {
            getCurrentInput().setValue(getApplication().getString(R.string.input_indicator));
        } else {
            getCurrentInput().setValue(builder.toString());
        }
    }

    public void submit(View view) {
        if (builder.length() == 0) {
            builder.append("-1");
        }
        if (Integer.valueOf(builder.toString()).intValue() == Objects.requireNonNull(getAnswer().getValue())) {
            answerCorrect();
            builder.setLength(0);
            getCurrentInput().setValue(getApplication().getString(R.string.answer_corrrect_message));
        } else {
            NavController controller = Navigation.findNavController(view);
            if (win_flag) {
                builder.setLength(0);
                getCurrentInput().setValue(getApplication().getString(R.string.input_indicator));
                controller.navigate(R.id.action_questionFragment_to_winFragment);
                win_flag = false;
                save();
            } else {
                builder.setLength(0);
                getCurrentInput().setValue(getApplication().getString(R.string.input_indicator));
                controller.navigate(R.id.action_questionFragment_to_loseFragment);
            }
        }
    }

    public void backToTitle(View view) {
        Navigation.findNavController(view).navigate(R.id.titleFragment);
    }
}
