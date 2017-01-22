package com.example.saloni.myapplicationtodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by saloni on 1/21/2017.
 */

public class MainEditDlg extends DialogFragment {

    private EditText mEditText;
    int selectedItemId;
    Button saveBtn;

    public interface SaveItemListener {
        void onFinishEditDlg(String inputText, int position);
    }

    public MainEditDlg() {
        // Empty constructor required for DialogFragment
    }

    public static MainEditDlg newInstance(String title) {
        MainEditDlg frag = new MainEditDlg();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        getDialog().setCanceledOnTouchOutside(true);


        mEditText = (EditText) view.findViewById(R.id.txt_edit_item_dlg);
        Bundle bundle = getArguments();
        String toBeEditedText = bundle.getString("editText");
        selectedItemId = bundle.getInt("selectedItemId");
        String title = bundle.getString("title");
        getDialog().setTitle(title);
        mEditText.setText(toBeEditedText);
        mEditText.setSelection(mEditText.getText().length()); //cursor to be at end of text




        saveBtn = (Button) view.findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return input text back to activity through the implemented listener
                SaveItemListener listener = (SaveItemListener) getActivity();
                listener.onFinishEditDlg(mEditText.getText().toString(), selectedItemId);
                // Close the dialog and return back to the parent activity
                dismiss();
            }
        });

        return view;
    }

}