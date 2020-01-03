package clement.zentz.mareu;

import clement.zentz.mareu.models.Reunion;

public interface ActivityToRVAdapter {
    void launchMyActivity(Reunion reunion, int position);
    void callDeleteReunion(Reunion reunion, int position);
    void callAddReunion(Reunion reunion);
    void callUpdateReunion(Reunion reunion, int position);
}
