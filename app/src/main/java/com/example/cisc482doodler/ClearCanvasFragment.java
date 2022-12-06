package com.example.cisc482doodler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class ClearCanvasFragment extends DialogFragment {
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    NoticeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.clear_message)
                .setPositiveButton(R.string.clear_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // clear screen
                        listener.onDialogPositiveClick(ClearCanvasFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user cancelled clear, close fragment
                    }
                });
        return builder.create();
    }
}
