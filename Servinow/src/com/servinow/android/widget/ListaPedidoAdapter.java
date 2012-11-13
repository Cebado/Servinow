/**
 * 
 */
package com.servinow.android.widget;

import java.util.ArrayList;

import com.servinow.android.R;
import com.servinow.android.domain.SelectedItem;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author escrichov
 *
 */
public class ListaPedidoAdapter extends ArrayAdapter<SelectedItem> {
	
	/* ViewHolder pattern
	 * Se usa en los ListView para reducir el numero de findById (optimizacion)
	 */
	static class ListaPedidoViewHolder {
	  TextView name;
	  ImageView image;
	  TextView unitPrice;
	  TextView quantity;
	  CheckBox checkBox;
	}
	
	private int layoutResourceId;
	
	public ListaPedidoAdapter(Context context, int layoutResourceId, ArrayList<SelectedItem> objects) {
		super(context, R.layout.lista_pedido_row, objects);
		this.layoutResourceId = layoutResourceId;
	}
	
	@Override	
	public View getView(int position, View convertView, ViewGroup parent) {
		ListaPedidoViewHolder holder = null;
		View row = convertView;
		
	  if(row == null)
	  {      
		    LayoutInflater inflater = ( (Activity) getContext()).getLayoutInflater();
		    row = inflater.inflate(layoutResourceId, parent, false);
		    
		    //ViewHolder pattern
		    holder = new ListaPedidoViewHolder();
		    holder.name = (TextView) row.findViewById(R.id.lista_pedido_row_titulo);
		    holder.image = (ImageView) row.findViewById(R.id.lista_pedido_row_image);
		    holder.unitPrice = (TextView) row.findViewById(R.id.lista_pedido_row_precio_unidad);
		    holder.quantity = (TextView) row.findViewById(R.id.lista_pedido_row_cantidad);
		    holder.checkBox = (CheckBox) row.findViewById(R.id.lista_pedido_row_checkbox);
		    
		    // Optimization: Tag the row with it's child views, so we don't have to 
	        // call findViewById() later when we reuse the row.
		    row.setTag(holder);
		    
	        // If CheckBox is toggled, update the planet it is tagged with.
	        holder.checkBox.setOnClickListener( new View.OnClickListener() {
	          public void onClick(View v) {
	            CheckBox cb = (CheckBox) v ;
	            SelectedItem selectedItem = (SelectedItem) cb.getTag();
	            selectedItem.setChecked( cb.isChecked() );
	          }
	        });
	  }
	  else
	  {
		    holder = (ListaPedidoViewHolder) row.getTag();
	  }
	  
	    SelectedItem selectedItem = (SelectedItem) getItem(position);
	    
	  	// Tag the CheckBox with the Planet it is displaying, so that we can
      	// access the planet in onClick() when the CheckBox is toggled.
      	holder.checkBox.setTag( selectedItem ); 
	    
	    holder.name.setText(selectedItem.getName());
	    holder.image.setImageURI(Uri.parse(selectedItem.geturlImage()));
	    holder.image.setVisibility(selectedItem.getImageVisibility());
	    holder.unitPrice.setText(selectedItem.getUnitPrice()+"�");
	    holder.quantity.setText(""+selectedItem.getQuantity());
	    holder.checkBox.setChecked(selectedItem.isChecked());
	    holder.checkBox.setVisibility(selectedItem.getCheckBoxVisibility());
	    
	    return row;
	}

}