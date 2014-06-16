package pro.rudo.crud.app;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class EditOrDeleteDialog extends DialogFragment {


    public EditOrDeleteDialog() {
    }

    public static EditOrDeleteDialog newInstance(String title) {
        EditOrDeleteDialog dialog = new EditOrDeleteDialog();
        Bundle args =  new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    public interface EditOrDeleteDialogListener {
        public void onDialogClick(DialogFragment dialogFragment, int which);
    }

    EditOrDeleteDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (EditOrDeleteDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EditOrDeleteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title");
        builder.setTitle(title)
                .setItems(R.array.actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogClick(EditOrDeleteDialog.this, i);
                    }
                });
        return builder.create();
    }


}
