package com.example.ex3.utill;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ex3.Ex3;
import com.example.ex3.R;

public class ChangeTheme extends AppCompatDialogFragment {
    ChangeListener listener;

    public ChangeTheme(ChangeListener changeListener) {
        this.listener = changeListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_change_theme, null);
        view.findViewById(R.id.btnThemeColor).setOnClickListener(v -> {
            Ex3.changeTheme(1);
            listener.onChange();
        });
        view.findViewById(R.id.btnThemeDark).setOnClickListener(v -> {
            Ex3.changeTheme(0);
            listener.onChange();
        });
        builder.setView(view);
        return builder.create();
    }
}
