package com.example.hang.simpletodo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hang on 11/20/15.
 */
public class DialogEdit extends DialogFragment {

    public DialogEdit() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DialogEdit newInstance(String text, int pos, MainActivity parent) {
        DialogEdit frag = new DialogEdit();
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putInt("position", pos);
        args.putSerializable("parent", parent);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch arguments from bundle and set title
        String text = getArguments().getString("text", "Enter Text: ");
        getDialog().setTitle("Edit the item:");
        // Show soft keyboard automatically and request focus to field
        final EditText editText = (EditText)view.findViewById(R.id.editText);
        editText.setText(text);
        editText.setSelection(text.length());
        editText.requestFocus();

        Button fab = (Button) view.findViewById(R.id.btnSubmit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = editText.getText().toString();
                int pos = getArguments().getInt("position",0);
                MainActivity parent = (MainActivity)getArguments().getSerializable("parent");
                parent.onDeleteItem(pos,newText);
                dismiss();
            }
        });

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }
}
