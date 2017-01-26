package rad.iit.com.baya.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import rad.iit.com.baya.R;

public class ExitAppFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Exit App?")
               .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
/*
                       getActivity().finish();
*/

                       Intent intent = new Intent(Intent.ACTION_MAIN);
                       intent.addCategory(Intent.CATEGORY_HOME);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       getActivity().startActivity(intent);
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                       dismiss();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}