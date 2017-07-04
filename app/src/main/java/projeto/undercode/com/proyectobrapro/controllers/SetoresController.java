package projeto.undercode.com.proyectobrapro.controllers;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.database.LocalDB;
import projeto.undercode.com.proyectobrapro.database.RemoteDB;
import projeto.undercode.com.proyectobrapro.googlemaps.FirstMapFragment;
import projeto.undercode.com.proyectobrapro.models.Coordenadas;
import projeto.undercode.com.proyectobrapro.models.Setor;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;
import projeto.undercode.com.proyectobrapro.utils.CalculatePolygonArea;
import projeto.undercode.com.proyectobrapro.utils.ControladorDevice;
import projeto.undercode.com.proyectobrapro.utils.ControladorGPS;
import projeto.undercode.com.proyectobrapro.utils.ControladorGPS2;

/**
 * Created by Level on 08/02/2017.
 */

public class SetoresController extends BaseController implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    SharedPreferences prefs;
    String nome;
    int id_user;
    LocalDB localdb;
    RemoteDB remotedb;
    public int conn ;

    ControladorDevice mControladorDevice =  ControladorDevice.getInstance();
    @BindString(R.string.ConsultaCoordenadas)
    String wsConsultaCoordenadas;
    @BindString(R.string.ManutencaoSectores_Coordenadas)
    String wsManutencaoSectores_Coordenadas;
    @BindString(R.string.ManutencaoSector)
    String wsManutencaoSector;
    @BindView(R.id.map_type_selector)
    Spinner mMapTypeSelector;
    @BindView(R.id.et_nome_sector)
    EditText nome_sector;

    SweetAlertDialog dialogSystem;
    FirstMapFragment mFirstMapFragment;
    ArrayList<Coordenadas> auxCoordendas;
    ArrayList<String> longitude;
    ArrayList<String> latitude;
    GoogleMap mMap;
    ArrayList<Marker> markers = new ArrayList<Marker>();
    static final int POLYGON_POINTS = 3;
    Polygon shape;
    String state = "V";
    ArrayAdapter<String> dataAdapterProducto;
    Double valorhectareas = 0.0;

    String Rastreiostate = "Sim";
    private boolean sair = false;
    private boolean processando = false;
    ArrayList<Marker> markers2 = new ArrayList<Marker>();
    private Rastreamento objRastreamento = null;

    ArrayList<String> latitude_strings2 = new ArrayList<String>();
    ArrayList<String> longitud_strings2 = new ArrayList<String>();

    HashMap<String, String> options;
    Polygon polyline;
    List<LatLng> area;
    CalculatePolygonArea calculo;

    @BindView(R.id.menu) FloatingActionMenu fab_menu;
    @BindView(R.id.fab_btnCreate) FloatingActionButton fab_btnCreate;
    @BindView(R.id.fab_btnDelete) FloatingActionButton fab_btnDelete;
    @BindView(R.id.fab_btnSave) FloatingActionButton fab_btnSave;
    @BindView(R.id.fab_btnRastreio) FloatingActionButton fab_btnRastreio;


    @OnClick(R.id.fab_btnDelete)
    public void submitDelete() {
        if (polyline != null){
            polyline.remove();
        }
        deleteCoordenadas();
    }


    @OnClick(R.id.fab_btnCreate)
    public void submitCreate() {

        ToastMsg("imprensa na tela para criar pontos de setores");

        if (polyline != null){
            polyline.remove();
        }
        fab_btnSave.setEnabled(true);
        fab_btnCreate.setEnabled(false);
        fab_btnDelete.setEnabled(false);
        nome_sector.setEnabled(true);
        mMapTypeSelector.setEnabled(false);
        fab_btnRastreio.setEnabled(false);
        state = "C";
    }


    @OnClick(R.id.fab_btnSave)
    public void submitSave() {

        if ( nome_sector.getText().toString().matches("")) {

            ToastMsg("atribui um nome para seu setor");
            nome_sector.setFocusable(true);

        } else {

            if (markers.size() < 3) {

                ToastMsg("Você precisa de pelo menos 3 pontos");

            } else {

                ArrayList<String> latitude_strings = new ArrayList<String>();
                ArrayList<String> longitud_strings = new ArrayList<String>();

                area = new ArrayList<>();

                for (int i = 0; i < markers.size(); i++) {

                    LatLng ll = markers.get(i).getPosition();
                    double latitude = ll.latitude;
                    double longitude = ll.longitude;

                    area.add(ll);

                    latitude_strings.add(String.valueOf(latitude));
                    longitud_strings.add(String.valueOf(longitude));

                }


                calculo = new CalculatePolygonArea();

                valorhectareas = calculo.calculateAreaOfGPSPolygonOnEarthInSquareMeters(area);

                fab_btnCreate.setEnabled(true);
                fab_btnDelete.setEnabled(true);
                fab_btnSave.setEnabled(false);
                nome_sector.setEnabled(false);
                mMapTypeSelector.setEnabled(true);
                fab_btnRastreio.setEnabled(true);
                state = "V";
                removeEverything();
                dataAdapterProducto = null;

                SaveSector(latitude_strings.toString(), longitud_strings.toString());

            }

        }


    }

    @OnClick(R.id.fab_btnRastreio)
    public void submitRastreio() {

        if (polyline != null){
            polyline.remove();
        }

        area = new ArrayList<>();

        if (Rastreiostate.equals("Sim")){
            Rastreiostate = "Nao";
            iniciarRastreamento();

            fab_btnSave.setEnabled(false);
            fab_btnCreate.setEnabled(false);
            fab_btnDelete.setEnabled(false);
            nome_sector.setEnabled(false);
            mMapTypeSelector.setEnabled(false);

        } else {

            fab_btnSave.setEnabled(true);
            fab_btnCreate.setEnabled(false);
            fab_btnDelete.setEnabled(false);
            nome_sector.setEnabled(true);
            mMapTypeSelector.setEnabled(false);


            sair = true;
            //iniciarRastreamento();
            Rastreiostate = "Sim";


            for (int i = 0; i < latitude_strings2.size(); i++) {

                setMarker("Test", "Test", Double.valueOf(latitude_strings2.get(i).toString()), Double.valueOf(longitud_strings2.get(i).toString()));

            }

        }
    }


    @Override
    public int getLayout() {
        return R.layout.activity_mapa_google_maps2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localdb = new LocalDB(this);
        remotedb = new RemoteDB(this);
        conn = RemoteDB.getConnectivityStatus(getApplicationContext());

        prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        nome = prefs.getString("nome", "No name defined");
        id_user = prefs.getInt("codigo", 0);
        //id_user = 9;

        fab_btnSave.setEnabled(false);
        nome_sector.setEnabled(false);
        mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mFirstMapFragment)
                .commit();


        fab_btnCreate.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_btnCreate.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_btnDelete.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_btnDelete.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));

        fab_btnSave.setShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
        fab_btnSave.setHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down));


        // Registrar escucha onMapReadyCallback
        mFirstMapFragment.getMapAsync(this);
        getCoordenadas();
        mMapTypeSelector.setOnItemSelectedListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mControladorDevice.finalizarGPS1();
    }

    public static double CalculatePolygonArea(List<LatLng> coordinates)
    {
        //radio de la tierra en metros
        int R = 6378137;
        double area = 0;

        if (coordinates.size() > 2)
        {
            for (int i = 0; i < coordinates.size()-1; i++)
            {
                LatLng p1, p2;
                p1 = coordinates.get(i);
                p2 = coordinates.get(i+1);
                area += (p2.longitude - p1.longitude) * (2 + Math.sin(ConvertToRadian(p1.latitude))
                        + Math.sin(ConvertToRadian(p2.latitude)));
            }

            area = area * R * R / 2;


        }

        return Math.abs(area);
    }

    private static double ConvertToRadian(double input)
    {
        return input * Math.PI / 180;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ToastMsg("ativar seu GPS");

        } else {

            mMap.setMyLocationEnabled(true);
            mControladorDevice.iniciarGPS1(getApplicationContext());
        }

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

    }

    public void getCoordenadas() {

        nome_sector.setText("");

        JSONObject aux = new JSONObject();

        switch (conn) {
            case (1):
            case (2):
                //Remoto
                try {

                    aux.put("id_usuario", String.valueOf(id_user));
                    remotedb.ConsultaCoordenadas(aux,"Coordenadas");

                    // TODO: Go to getArrayResults() to get the JSON from DB

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break ;
            case (0):
                //Local
                auxCoordendas = localdb.ConsultaSectores_Coordenadas(id_user,"Criado");
                updateProductoArray(auxCoordendas);
                break;
            default:
                Log.d("conn", ""+conn); break;
        }

    }


    public void SaveSector(String latitude, String longitude) {

        JSONObject aux = new JSONObject();

        try {

            aux.put("acao", "I");
            aux.put("id_usuario", String.valueOf(id_user));
            aux.put("status", "activo");
            aux.put("nombre", nome_sector.getText().toString().replace(" ","%20"));

            //aux.put("hectareas", "20");
            aux.put("hectareas", String.valueOf(Math.round(valorhectareas)));

            aux.put("latitude", latitude.replace(" ","").replace("[","").replace("]",""));
            aux.put("longitude", longitude.replace(" ","").replace("[","").replace("]",""));
            aux.put("id_sector", "");

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoSector(aux, "SaveSector");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoSector(aux);
                    getCoordenadas();
                    break;
                default:
                    Log.d("conn", ""+conn); break;
            }

            // TODO: Go to getArrayResults() to get the JSON from DB

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void deleteCoordenadas() {

        JSONObject aux = new JSONObject();

        try {

            String productoName = mMapTypeSelector.getSelectedItem().toString();
            String productoValue = options.get(productoName);


            aux.put("acao", "D");
            aux.put("id_usuario", String.valueOf(id_user));
            aux.put("status", "");
            aux.put("nombre", "");
            aux.put("hectareas", "");
            aux.put("latitude", "");
            aux.put("longitude", "");
            aux.put("id_sector", productoValue);

            switch (conn) {
                case (1):
                case (2):
                    //Remoto
                    remotedb.ManutencaoSector(aux, "delete_sector");
                    break ;
                case (0):
                    //Local
                    localdb.ManutencaoSector(aux);
                    auxCoordendas = null;
                    longitude = null;
                    latitude = null;
                    options = null;
                    polyline = null;
                    getCoordenadas();
                    break;
                default:
                    Log.d("conn", ""+conn); break;
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getArrayResults(JSONArray response, String option) {

        if (option.equals("Coordenadas")) {

            Coordenadas p;
            auxCoordendas = new ArrayList<Coordenadas>();
            JSONObject jo = null;

            try {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        jo = response.getJSONObject(i);

                        Setor s = new Setor(
                                jo.getString("Id_sector"),
                                jo.getString("Id_usuario"),
                                jo.getString("Id_cultivo"),
                                jo.getString("Status"),
                                jo.getString("Nombre"),
                                jo.getString("Hectareas")
                        );

                        p = new Coordenadas(
                                jo.getInt("id_coordenada"),
                                jo.getString("latitude"),
                                jo.getString("longitude"),
                                s
                        );
                        Log.d("latilong" + i, p.getLatitude());
                        Log.d("latilong" + i, p.getLongitude());
                        auxCoordendas.add(p);
                    }

                    updateProductoArray(auxCoordendas);

                    Log.d("Cordenadas", auxCoordendas.iterator().toString());

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (option.equals("delete_sector")) {
            auxCoordendas = null;
            longitude = null;
            latitude = null;
            options = null;
            polyline = null;
            getCoordenadas();
        }

        if (option.equals("SaveSector")) {
            getCoordenadas();
        }

    }

    private void drawpolygon(ArrayList<String> longitude, ArrayList<String> latitude) {

        int length_longitude = longitude.size();
        int length_latitude = latitude.size();

        // Optional length checks. Modify yourself.
        if(length_longitude == 0 || length_latitude == 0)
        {
            // Do whatever you like then get out. Do not run the following.
            return;
        }

        // We have a length of not 0 so...
        PolygonOptions poly = new PolygonOptions();
        poly.strokeColor(Color.BLACK);
        poly.fillColor(0x5500ff00);
        poly.strokeWidth(1);

        // Initial point


        // ... then the rest.
        for(int i = 0; i < length_longitude; i++)
        {
            poly.add(new LatLng(Double.valueOf(latitude.get(i)), Double.valueOf(longitude.get(i).toString())));
        }

        // Done! Add to map.
        polyline = mMap.addPolygon(poly);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i = 0; i < length_longitude; i++)
        {
            builder.include(new LatLng(Double.valueOf(latitude.get(i)), Double.valueOf(longitude.get(i).toString())));
        }
        LatLngBounds bounds = builder.build();

        int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);

    }

    public void updateProductoArray(List<Coordenadas> Lp) {

        int quantidade= 0;
        String quantiadeverify = "0";

        for (int i=0; i< Lp.size(); i++) {


            //if (Lp.get(i).getSetor().getId_sector() != quantiadeverify) {
            if (!(Lp.get(i).getSetor().getId_sector().equals(quantiadeverify))) {
                quantiadeverify = Lp.get(i).getSetor().getId_sector();
                quantidade++;
            }

        }

        String[] optionsCordenadas  = new String[quantidade];
        options = new HashMap<String,String>();
        String j = "0";
        int quantidade2 = 0;

        for (int i=0; i< Lp.size(); i++) {

                if (!(Lp.get(i).getSetor().getId_sector().equals(j))) {

                j = Lp.get(i).getSetor().getId_sector();
                options.put(Lp.get(i).getSetor().getNombre(), String.valueOf(Lp.get(i).getSetor().getId_sector()));

                optionsCordenadas[quantidade2] = Lp.get(i).getSetor().getNombre();
                quantidade2++;

            }

        }

        dataAdapterProducto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optionsCordenadas);
        dataAdapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMapTypeSelector.setAdapter(dataAdapterProducto);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String sectorName = mMapTypeSelector.getSelectedItem().toString();

        String sectorValue = options.get(sectorName);

        longitude = new ArrayList<String>();
        latitude = new ArrayList<String>();

        for (int i=0; i< auxCoordendas.size(); i++) {

            if (auxCoordendas.get(i).getSetor().getId_sector().equals(sectorValue)) {
                longitude.add(auxCoordendas.get(i).getLongitude());
                latitude.add(auxCoordendas.get(i).getLatitude());
            }

        }

        if (polyline != null){
            polyline.remove();
        }

        drawpolygon(longitude, latitude);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onMapClick(LatLng ll) {

        if (state.equals("C")) {
            Geocoder gc = new Geocoder(SetoresController.this);
            List<Address> list = null;

            try {
                list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Address add = list.get(0);
            SetoresController.this.setMarker(add.getLocality(), "test", ll.latitude, ll.longitude);
        }
    }



    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    private void setMarker(String locality, String country, double lat, double lng){
        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat,lng))
                .icon(BitmapDescriptorFactory.defaultMarker())
                .draggable(true);
        if (country.length() > 0){
            options.snippet(country);
        }

        markers.add(mMap.addMarker(options));

        if (markers.size() >= POLYGON_POINTS){
            if (shape != null){
                shape.remove();
            }
            drawPolygon();
        }

    }

    private void drawPolygon(){
        PolygonOptions options = new PolygonOptions()
                .fillColor(0x330000FF)
                .strokeWidth(3)
                .strokeColor(Color.BLUE);

        for (int i = 0; i < markers.size(); i++){
            options.add(markers.get(i).getPosition());
            Log.d("positions", markers.get(i).getPosition().toString());

        }

        shape = mMap.addPolygon(options);

    }

    private void removeEverything(){
        for (Marker marker : markers){
            marker.remove();
        }
        markers.clear();
        if (shape != null){
            shape.remove();
        }

        shape = null;
    }


    public void iniciarRastreamento() {

        Log.d("Fase2","Fase2");

        if (objRastreamento != null) {
            sair = true;
            Log.d("sair",String.valueOf(sair));
            try {
                objRastreamento.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        sair = false;
        String stempogps = "4000";
        String stempoenvio = "4000";

        try {

        } catch (Exception ex) {

        }


        if (stempogps == null) {
            stempogps = "15";
            stempoenvio = "4";
        }
        if (stempogps.equals("null")) {
            stempogps = "15";
            stempoenvio = "4";
        }




        objRastreamento = new Rastreamento(Long.valueOf(stempogps));
        objRastreamento.start();
        objRastreamento.interrupt();
    }


    public class Rastreamento extends Thread {
        private long tempoEspera;

        Rastreamento(long tempoEspera) {
            this.tempoEspera = tempoEspera;
        }



        public void run() {
            long tempo1, tempo2;
            while (!sair) {
                try {
                    tempo1 = System.currentTimeMillis();
                    for (;;) {
                        if (sair)
                            break;
                        sleep(1000);
                        tempo2 = System.currentTimeMillis() - tempo1;
                        if (tempo2 >= tempoEspera) {
                            enviarPosicaoAtual();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void enviarPosicaoAtual() {
        if (processando == true) {
            return;
        }


        processando = true;
        try {

            //Coordenadas mRastreio = new Coordenadas();
            String latitude = "";
            String longitude = "";
            Location location = ControladorGPS.getLocation();
            Location location2 = ControladorGPS2.getLocation();

            area.add(new LatLng(location.getLatitude(), location.getLongitude()));


            if (location != null) {
                try {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                } catch (Exception ex) {
                }
            }
            if (latitude == null)
                latitude = "";
            if (latitude.equals("")) {
                try {
                    latitude = String.valueOf(location2.getLatitude());
                    longitude = String.valueOf(location2.getLongitude());
                } catch (Exception ex) {
                }
            }
            if (latitude == null)
                latitude = "";
            if (longitude == null)
                longitude = "";


            latitude_strings2.add(String.valueOf(latitude));
            longitud_strings2.add(String.valueOf(longitude));

            ///dibujar();

/*            Log.d("MarkerOptions","MarkerOptions");
            MarkerOptions options = new MarkerOptions()
                    .title("novo")
                    .position(new LatLng(Double.valueOf(latitude),Double.valueOf(longitude)))
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .draggable(true);

            Log.d("markers1","markers1");
            mMap.addMarker(options);


            Log.d("create marker","create marker");
            markers.add(mMap.addMarker(options));
            Log.d("markers.size()", String.valueOf(markers.size()));


            SetoresController.this.setMarker("test", "test", Double.valueOf(latitude), Double.valueOf(longitude));




            LatLng markPosition = new LatLng(Double.valueOf(-15.83296419), Double.valueOf(-48.08457803));
            mMap.addMarker(new MarkerOptions()
                    .position(markPosition)
                    .title("Marker Ponto"));*/


/*            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(Double.valueOf(latitude),Double.valueOf(longitude)))
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .draggable(true);


            markers2.add(mMap.addMarker(options));*/


        } catch (Exception ex) {
            // showWindow(UnderActivity.ALERT_DIALOG, ex.getMessage());
        }
        processando = false;
    }


}
