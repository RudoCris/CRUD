package pro.rudo.crud.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import pro.rudo.crud.app.dummy.DummyContent;
import pro.rudo.crud.app.model.Cave;
import pro.rudo.crud.app.model.Picket;
import pro.rudo.crud.app.sqlite.PicketSQLiteHelper;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class PicketsFragment extends ListFragment implements EditOrDeleteDialog.EditOrDeleteDialogListener {

    private PicketSQLiteHelper db;
    private Button addBtn;
    final ArrayList<Picket> picketsLst = new ArrayList<Picket>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PicketsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pickets_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        final int caveId = args.getInt("caveId");
        addBtn = (Button) getView().findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PicketActivity.class).putExtra("caveId", caveId));
            }
        });
        final PicketsAdapter adapter = new PicketsAdapter(getActivity(), R.layout.listview_picket_row, picketsLst);
        Cave.find(caveId, new Cave.CaveHelper() {
            @Override
            public void onCaveFindDo(Cave cave) {
                cave.getPickets(new Cave.PicketsFinder() {
                    @Override
                    public void onPicketsFindDo(List<Picket> pickets) {
                        picketsLst.addAll(pickets);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = getListView().getItemAtPosition(i).toString();
                DialogFragment dialog = new EditOrDeleteDialog().newInstance(title, i);

                dialog.show(getFragmentManager(), "editOrDelete");
                return true;
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    @Override
    public void onDialogClick(DialogFragment dialogFragment, int which, int position) {
        final Picket picket = (Picket) getListView().getItemAtPosition(position);
        switch (which) {
            case 0:
                Toast.makeText(getActivity(), "EDITED!", Toast.LENGTH_SHORT).show();
                break;
            case 1:
//                picket.delete(new Picket.DeletePicketCallback() {
//                    @Override
//                    public void onDelete() {
//                        picketsLst.remove(picket);
//                    }
//                });
                Toast.makeText(getActivity(), "DELETED!", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
