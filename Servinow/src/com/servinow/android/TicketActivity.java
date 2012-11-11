package com.servinow.android;

import java.util.LinkedList;
import java.util.List;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.servinow.android.dao.PedidoCache;
import com.servinow.android.domain.LineaPedido;
import com.servinow.android.domain.Pedido;
import com.servinow.android.widget.PurchasedItemAdapter;

import android.os.Bundle;

public class TicketActivity extends SherlockListActivity {
  private int restaurantID;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle parameters = getIntent().getExtras();
    if (parameters != null) {
      restaurantID = parameters.getInt("restaurantID");
      List<Pedido> pedidos = new PedidoCache(this).getPedidosNoPagados(restaurantID);
      List<LineaPedido> lineasPedido = new LinkedList<LineaPedido>();
      for(Pedido p : pedidos) {
        lineasPedido.addAll(p.getLineas());
      }
      PurchasedItemAdapter adapter = new PurchasedItemAdapter(this, R.layout.ticket_list_item, lineasPedido);
      setListAdapter(adapter);
      setContentView(R.layout.activity_ticket);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getSupportMenuInflater().inflate(R.menu.activity_ticket, menu);
    return true;
  }
}
