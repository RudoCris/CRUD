package pro.rudo.crud.app;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by rudolf on 04.06.14.
 */
public class ShowInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    ShowInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public String getPickets() {
        int[][] pickets = new int[][]{
                {0, 0, 0},
                {70, 70, 0},
                {0, 50, 0},
                {0, 0, 0},
                {40, 80, 0}
        };

        Gson gson = new Gson();
        return gson.toJson(pickets);
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
