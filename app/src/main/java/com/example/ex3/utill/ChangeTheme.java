package com.example.ex3.utill;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ex3.R;

public class ChangeTheme extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_change_theme, null);
        builder.setView(view).setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {

        }).setPositiveButton("Change", (DialogInterface.OnClickListener) (dialog, which) -> {

        });
        return builder.create();
    }
}
