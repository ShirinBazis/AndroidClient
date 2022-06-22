package com.example.ex3.utill;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ex3.Ex3;
import com.example.ex3.R;

public class ChangeServer extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_change_server, null);
        TextView tv = view.findViewById(R.id.tvSettingsCurrentServer);
        tv.setText("Current server is: " + Ex3.server);
        builder.setView(view).setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {

        }).setPositiveButton("Change", (DialogInterface.OnClickListener) (dialog, which) -> {
            EditText et = view.findViewById(R.id.etSettingsChangeServer);
            if (et.getText().length() != 0) {
                Ex3.changeServer(et.getText().toString());
                Toast.makeText(Ex3.context, "Server has been changed", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }
}
