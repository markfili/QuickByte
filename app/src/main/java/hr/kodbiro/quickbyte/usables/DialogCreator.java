/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.usables;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Toast;

import hr.kodbiro.quickbyte.activities.MenuActivity;
import hr.kodbiro.quickbyte.activities.R;

/**
 * Created by marko on 26.8.2014..
 * DialogCreator class for showing AlertDialogs when needed
 *
 */
public class DialogCreator extends DialogFragment {

    int caller;
    int layout;
    String message;
    EditText quantityEditText;

    public static DialogCreator newInstance(int caller, int layout, String message) {
        DialogCreator frag = new DialogCreator();
        Bundle args = new Bundle();
        args.putInt("caller", caller);
        args.putInt("layout", layout);
        args.putString("message", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        caller = getArguments().getInt("caller");
        layout = getArguments().getInt("layout");
        message = getArguments().getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(layout, null);
        builder.setView(view).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String quantityText = "";
                Integer quantity = 0;

                if (caller == android.R.id.list) {
                    quantityText = quantityEditText.getText().toString();
                    quantity = Integer.valueOf(quantityText);
                }

                    Log.i("quantity", String.valueOf(quantity));

                    ((MenuActivity) getActivity()).doPositiveClick(caller, quantity, quantityText);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        if (caller == android.R.id.list){
            seekBarChanges(view);
        }
        return builder.create();
    }


    public void seekBarChanges(View view){
        quantityEditText = (EditText) view.findViewById(R.id.quantityEditText);
        SeekBar seekBer = (SeekBar) view.findViewById(R.id.dialogSeekBar);
        seekBer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                quantityEditText.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
