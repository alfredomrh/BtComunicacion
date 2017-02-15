package com.example.alfredomartinromo.btcomunicacion.interfaces;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by alfredo on 14/02/17.
 */

public interface ILDpresenter {

    public void getLinkedDevices();

    public void showList();

    public void onItemClick(AdapterView<?> parent, View view, int position, long id);


}
