package pro.rudo.crud.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pro.rudo.crud.app.model.Picket;
import pro.rudo.crud.app.model.PicketBuilder;

/**
 * Created by rudolf on 16.06.14.
 */
public class PicketsAdapter extends ArrayAdapter<Picket> {
    private Context context;
    private int layoutResourceId;
    private List<Picket> pickets = null;

    public PicketsAdapter(Context context, int layoutResourceId, List<Picket> pickets) {
        super(context, layoutResourceId, pickets);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.pickets = pickets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PicketHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PicketHolder();

            holder.fromTo = (TextView)row.findViewById(R.id.fromTo);
            holder.length = (TextView)row.findViewById(R.id.length);
            holder.azimuth = (TextView)row.findViewById(R.id.azimuth);
            holder.incline = (TextView)row.findViewById(R.id.incline);
            holder.backAzimuth = (TextView)row.findViewById(R.id.backAzimuth);
            holder.backIncline = (TextView)row.findViewById(R.id.backIncline);
            holder.left = (TextView)row.findViewById(R.id.left);
            holder.right = (TextView)row.findViewById(R.id.right);
            holder.up = (TextView)row.findViewById(R.id.up);
            holder.down = (TextView)row.findViewById(R.id.down);
            holder.comment = (TextView)row.findViewById(R.id.comment);

            row.setTag(holder);
        } else {
            holder = (PicketHolder) row.getTag();
        }

        Picket picket = pickets.get(position);

        holder.fromTo.setText(picket.getFrom() + "-" + picket.getTo());
        holder.length.setText(String.valueOf(picket.getLength()));
        holder.azimuth.setText(String.valueOf(picket.getAzimuth()));
        holder.incline.setText(String.valueOf(picket.getIncline()));
        holder.backAzimuth.setText(String.valueOf(picket.getBackAzimuth()));
        holder.backIncline.setText(String.valueOf(picket.getBackIncline()));
        holder.left.setText(String.valueOf(picket.getLeft()));
        holder.right.setText(String.valueOf(picket.getRight()));
        holder.up.setText(String.valueOf(picket.getUp()));
        holder.down.setText(String.valueOf(picket.getDown()));
        holder.comment.setText(picket.getComment());

        return row;
    }

    static class PicketHolder {
        TextView fromTo;
        TextView length;
        TextView azimuth;
        TextView incline;
        TextView backAzimuth;
        TextView backIncline;
        TextView left;
        TextView right;
        TextView up;
        TextView down;
        TextView comment;
    }
}
