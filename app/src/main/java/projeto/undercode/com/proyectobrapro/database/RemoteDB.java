package projeto.undercode.com.proyectobrapro.database;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindString;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.base.controllerDB;
import projeto.undercode.com.proyectobrapro.models.AlertaPraga;
import projeto.undercode.com.proyectobrapro.models.Device;
import projeto.undercode.com.proyectobrapro.requests.ArrayRequest;
import projeto.undercode.com.proyectobrapro.requests.StringsRequest;

/**
 * Created by Level on 15/03/2017.
 */

public class RemoteDB extends controllerDB {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    private Context appContext;

    public RemoteDB(Context context) {
        super(context);
        appContext = context;
    }

    public String validarDevice(Device device, String login) {

        String retorno="";

        JSONObject aux = new JSONObject();
        String wsValidarDevice = appContext.getResources().getString(R.string.ConsultaBalance);

        try {

            aux.put("pim", encripta(device.getPim()));
            aux.put("imei", encripta(device.getImei()));
            aux.put("login", encripta(login));

            StringsRequest sr = new StringsRequest(appContext, wsValidarDevice, aux, "ValidarDevice");
            sr.postRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }


    public void ManutencaoColheita(JSONObject aux, String option ) {


        String wsManutencaoCosecha = appContext.getResources().getString(R.string.ManutencaoCosechas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoCosecha, aux, option);
        ar.makeRequest();

    }

    public void ConsultaColheita(JSONObject aux, String option) {

        String wsCosechasConsult = appContext.getResources().getString(R.string.ConsultaCosechas);
        ArrayRequest ar = new ArrayRequest(appContext, wsCosechasConsult, aux, option);
        ar.makeRequest();

    }

    public void ConsultaAlertaPraga(JSONObject aux, String option) {

        String wsAlertaPlagaConsult = appContext.getResources().getString(R.string.ConsultaAlertaPlagas);
        ArrayRequest ar = new ArrayRequest(appContext, wsAlertaPlagaConsult, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoAlertaPragas(JSONObject aux, String option) {

        String wsManutencaoAlertaPlagas = appContext.getResources().getString(R.string.ManutencaoAlertaPlagas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoAlertaPlagas, aux, option);
        ar.makeRequest();

    }

    public void ManutencaoSector(JSONObject aux, String option) {

        String wsManutencaoSector = appContext.getResources().getString(R.string.ManutencaoSector);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoSector, aux, option);
        ar.makeRequest();

    }

    public void ConsultaCoordenadas(JSONObject aux, String option) {

        String wsConsultaCoordenadas = appContext.getResources().getString(R.string.ConsultaCoordenadas);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaCoordenadas, aux, option);
        ar.makeRequest();
    }

    public void ConsultaSectores(JSONObject aux, String option) {

        String wsConsultaSectores = appContext.getResources().getString(R.string.ConsultaSectores);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaSectores, aux, option);
        ar.makeRequest();
    }

    public void ConsultaBalance(JSONObject aux, String option) {

        String wsConsultaBalance = appContext.getResources().getString(R.string.ConsultaBalance);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaBalance, aux, option);
        ar.makeRequest();
    }

    public void ConsultaClientes(JSONObject aux, String option) {

        String wsConsultaClientes = appContext.getResources().getString(R.string.ConsultaClientes);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaClientes, aux, option);
        ar.makeRequest();
    }

    public void ConsultaCotacoes(JSONObject aux, String option) {

        String wsConsultaCotacoes = appContext.getResources().getString(R.string.ConsultaCotacoes);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaCotacoes, aux, option);
        ar.makeRequest();
    }


    public void ConsultaCultivos(JSONObject aux, String option) {

        String wsConsultaCultivos = appContext.getResources().getString(R.string.ConsultaCultivos);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaCultivos, aux, option);
        ar.makeRequest();
    }

    public void ConsultaDespesas(JSONObject aux, String option) {

        String wsConsultaDespesas = appContext.getResources().getString(R.string.ConsultaDespesas);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaDespesas, aux, option);
        ar.makeRequest();
    }

    public void ConsultaEmpleados(JSONObject aux, String option) {

        String wsConsultaEmpleados = appContext.getResources().getString(R.string.ConsultaEmpleados);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaEmpleados, aux, option);
        ar.makeRequest();
    }

    public void ConsultaGado(JSONObject aux, String option) {

        String wsConsultaGado = appContext.getResources().getString(R.string.ConsultaGado);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaGado, aux, option);
        ar.makeRequest();
    }

    public void ConsultaHistorialConsumo(JSONObject aux, String option) {

        String wsConsultaHistorialConsumo = appContext.getResources().getString(R.string.ConsultaHistorialConsumo);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaHistorialConsumo, aux, option);
        ar.makeRequest();
    }

    public void ConsultaHistorialEngorde(JSONObject aux, String option) {

        String wsConsultaHistorialEngorde = appContext.getResources().getString(R.string.ConsultaHistorialEngorde);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaHistorialEngorde, aux, option);
        ar.makeRequest();
    }

    public void ConsultaHistorialLeche(JSONObject aux, String option) {

        String wsConsultaHistorialLeche = appContext.getResources().getString(R.string.ConsultaHistorialLeche);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaHistorialLeche, aux, option);
        ar.makeRequest();
    }

    public void ConsultaItemsMenu(JSONObject aux, String option) {

        String wsConsultaItemsMenu = appContext.getResources().getString(R.string.ConsultaItemsMenu);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaItemsMenu, aux, option);
        ar.makeRequest();
    }

    public void ConsultaLotegado(JSONObject aux, String option) {

        String wsConsultaLotegado = appContext.getResources().getString(R.string.ConsultaLotegado);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaLotegado, aux, option);
        ar.makeRequest();
    }

    public void ConsultaMaquinarias(JSONObject aux, String option) {

        String wsConsultaMaquinarias = appContext.getResources().getString(R.string.ConsultaMaquinarias);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaMaquinarias, aux, option);
        ar.makeRequest();
    }

    public void ConsultaMenu(JSONObject aux, String option) {

        String wsConsultaMenu = appContext.getResources().getString(R.string.ConsultaMenu);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaMenu, aux, option);
        ar.makeRequest();
    }

    public void ConsultaNegociacoes(JSONObject aux, String option) {

        String wsConsultaNegociacoes = appContext.getResources().getString(R.string.ConsultaNegociacoes);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaNegociacoes, aux, option);
        ar.makeRequest();
    }


    public void ConsultaPontosInteresse(JSONObject aux, String option) {

        String wsConsultaPontosInteresse = appContext.getResources().getString(R.string.ConsultaPontosInteresse);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaPontosInteresse, aux, option);
        ar.makeRequest();
    }

    public void ConsultaPrecos(JSONObject aux, String option) {

        String wsConsultaPrecos = appContext.getResources().getString(R.string.ConsultaPrecos);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaPrecos, aux, option);
        ar.makeRequest();
    }

    public void ConsultaPremios(JSONObject aux, String option) {

        String wsConsultaPremios = appContext.getResources().getString(R.string.ConsultaPremios);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaPremios, aux, option);
        ar.makeRequest();
    }

    public void ConsultaProducto(JSONObject aux, String option) {

        String wsConsultaProducto = appContext.getResources().getString(R.string.ConsultaProducto);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaProducto, aux, option);
        ar.makeRequest();
    }

    public void ConsultaSolo(JSONObject aux, String option) {

        String wsConsultaSolo = appContext.getResources().getString(R.string.ConsultaSolo);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaSolo, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTareas(JSONObject aux, String option) {

        String wsConsultaTareas = appContext.getResources().getString(R.string.ConsultaTareas);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTareas, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTaxas(JSONObject aux, String option) {

        String wsConsultaTaxas = appContext.getResources().getString(R.string.ConsultaTaxas);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTaxas, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTipoContrato(JSONObject aux, String option) {

        String wsConsultaTipoContrato = appContext.getResources().getString(R.string.ConsultaTipoContrato);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTipoContrato, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTipoDespesa(JSONObject aux, String option) {

        String wsConsultaTipoDespesa = appContext.getResources().getString(R.string.ConsultaTipoDespesa);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTipoDespesa, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTipoDespesaTempo(JSONObject aux, String option) {

        String wsConsultaTipoDespesaTempo = appContext.getResources().getString(R.string.ConsultaTipoDespesaTempo);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTipoDespesaTempo, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTipoProducto(JSONObject aux, String option) {

        String wsConsultaTipoProducto = appContext.getResources().getString(R.string.ConsultaTipoProducto);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTipoProducto, aux, option);
        ar.makeRequest();
    }

    public void ConsultaTipoTarea(JSONObject aux, String option) {

        String wsConsultaTipoTarea = appContext.getResources().getString(R.string.ConsultaTipoTarea);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaTipoTarea, aux, option);
        ar.makeRequest();
    }

    public void ConsultaUsers(JSONObject aux, String option) {

        String wsConsultaUsers = appContext.getResources().getString(R.string.ConsultaUsers);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaUsers, aux, option);
        ar.makeRequest();
    }

    public void ConsultaUsuarioAtivar(JSONObject aux, String option) {

        String wsConsultaUsuarioAtivar = appContext.getResources().getString(R.string.ConsultaUsuarioAtivar);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaUsuarioAtivar, aux, option);
        ar.makeRequest();
    }

    public void ConsultaVentaGado(JSONObject aux, String option) {

        String wsConsultaVentaGado = appContext.getResources().getString(R.string.ConsultaVentaGado);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaVentaGado, aux, option);
        ar.makeRequest();
    }

    public void ConsultaVisitas(JSONObject aux, String option) {

        String wsConsultaVisitas = appContext.getResources().getString(R.string.ConsultaVisitas);
        ArrayRequest ar = new ArrayRequest(appContext, wsConsultaVisitas, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoAlertaPlagas(JSONObject aux, String option) {

        String wsManutencaoAlertaPlagas = appContext.getResources().getString(R.string.ManutencaoAlertaPlagas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoAlertaPlagas, aux, option);
        ar.makeRequest();

    }

    public void ManutencaoAlterarSenha(JSONObject aux, String option) {

        String wsManutencaoAlterarSenha = appContext.getResources().getString(R.string.ManutencaoAlterarSenha);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoAlterarSenha, aux, option);
        ar.makeRequest();

    }

    public void ManutencaoClientes(JSONObject aux, String option) {
        String wsManutencaoClientes = appContext.getResources().getString(R.string.ManutencaoClientes);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoClientes, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoCosechas(JSONObject aux, String option) {
        String wsManutencaoCosechas = appContext.getResources().getString(R.string.ManutencaoCosechas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoCosechas, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoCultivos(JSONObject aux, String option) {
        String wsManutencaoCultivos = appContext.getResources().getString(R.string.ManutencaoCultivos);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoCultivos, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoDespesas(JSONObject aux, String option) {
        String wsManutencaoDespesas = appContext.getResources().getString(R.string.ManutencaoDespesas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoDespesas, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoEmpleados(JSONObject aux, String option) {
        String wsManutencaoEmpleados = appContext.getResources().getString(R.string.ManutencaoEmpleados);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoEmpleados, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoHistorialConsumo(JSONObject aux, String option) {
        String wsManutencaoHistorialConsumo = appContext.getResources().getString(R.string.ManutencaoHistorialConsumo);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoHistorialConsumo, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoHistorialEngorde(JSONObject aux, String option) {
        String wsManutencaoHistorialEngorde = appContext.getResources().getString(R.string.ManutencaoHistorialEngorde);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoHistorialEngorde, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoHistorialLeche(JSONObject aux, String option) {
        String wsManutencaoHistorialLeche = appContext.getResources().getString(R.string.ManutencaoHistorialLeche);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoHistorialLeche, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoGado(JSONObject aux, String option) {
        String wsManutencaoGado = appContext.getResources().getString(R.string.ManutencaoGado);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoGado, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoLotegado(JSONObject aux, String option) {
        String wsManutencaoLotegado = appContext.getResources().getString(R.string.ManutencaoLotegado);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoLotegado, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoMaquinarias(JSONObject aux, String option) {
        String wsManutencaoMaquinarias = appContext.getResources().getString(R.string.ManutencaoMaquinarias);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoMaquinarias, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoNegociacoes(JSONObject aux, String option) {
        String wsManutencaoNegociacoes = appContext.getResources().getString(R.string.ManutencaoNegociacoes);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoNegociacoes, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoPontoInteresse(JSONObject aux, String option) {
        String wsManutencaoPontoInteresse = appContext.getResources().getString(R.string.ManutencaoPontoInteresse);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoPontoInteresse, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoPremios(JSONObject aux, String option) {
        String wsManutencaoPremios = appContext.getResources().getString(R.string.ManutencaoPremios);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoPremios, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoProductos(JSONObject aux, String option) {
        String wsManutencaoProductos = appContext.getResources().getString(R.string.ManutencaoProductos);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoProductos, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoRegistro(JSONObject aux, String option) {
        String wsManutencaoRegistro = appContext.getResources().getString(R.string.ManutencaoRegistro);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoRegistro, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoSectores_Coordenadas(JSONObject aux, String option) {
        String wsManutencaoSectores_Coordenadas = appContext.getResources().getString(R.string.ManutencaoSectores_Coordenadas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoSectores_Coordenadas, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoSolo(JSONObject aux, String option) {
        String wsManutencaoSolo = appContext.getResources().getString(R.string.ManutencaoSolo);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoSolo, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoSolo2(JSONObject aux, String option) {
        String wsManutencaoSolo2 = appContext.getResources().getString(R.string.ManutencaoSolo2);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoSolo2, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoTareas(JSONObject aux, String option) {
        String wsManutencaoTareas = appContext.getResources().getString(R.string.ManutencaoTareas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoTareas, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoUsuarioAtivar(JSONObject aux, String option) {
        String wsManutencaoUsuarioAtivar = appContext.getResources().getString(R.string.ManutencaoUsuarioAtivar);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoUsuarioAtivar, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoVentaGado(JSONObject aux, String option) {
        String wsManutencaoVentaGado = appContext.getResources().getString(R.string.ManutencaoVentaGado);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoVentaGado, aux, option);
        ar.makeRequest();
    }

    public void ManutencaoVisitas(JSONObject aux, String option) {
        String wsManutencaoVisitas = appContext.getResources().getString(R.string.ManutencaoVisitas);
        StringsRequest ar = new StringsRequest(appContext, wsManutencaoVisitas, aux, option);
        ar.makeRequest();
    }

    public void ConsultaPlagas(JSONObject aux, String option) {

        String wsConsultaPlagas = appContext.getResources().getString(R.string.ConsultaPlagas);
        ArrayRequest ar2 = new ArrayRequest(appContext, wsConsultaPlagas, aux, option);
        ar2.makeRequest();
    }


}
