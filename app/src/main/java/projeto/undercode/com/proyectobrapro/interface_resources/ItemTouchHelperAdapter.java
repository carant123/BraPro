package projeto.undercode.com.proyectobrapro.interface_resources;

/**
 * Created by Level on 13/12/2016.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}