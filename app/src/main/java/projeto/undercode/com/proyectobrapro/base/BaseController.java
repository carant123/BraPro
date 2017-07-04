package projeto.undercode.com.proyectobrapro.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import projeto.undercode.com.proyectobrapro.R;
import projeto.undercode.com.proyectobrapro.utils.GlobalVariables;

/**
 * Created by Tecnew on 24/05/2016.
 */
public abstract class BaseController extends AppCompatActivity {


    @BindString(R.string.Login) String wsLogin;
    @BindString(R.string.ConsultaSectores) String wsConsultaSectores;
    @BindString(R.string.ConsultaCosechas) String wsConsultaCosechas;
    @BindString(R.string.ManutencaoCosechas) String wsManutencaoCosechas;
    @BindString(R.string.ConsultaCultivos) String wsConsultaCultivos;
    @BindString(R.string.ConsultaCultivosDetalle) String wsConsultaCultivosDetalle;
    @BindString(R.string.ConsultaPlagas) String wsConsultaPlagas;
    @BindString(R.string.ConsultaCotacoes) String wsConsultaCotacoes;
    @BindString(R.string.ConsultaClientes) String wsConsultaClientes;
    @BindString(R.string.ManutencaoClientes) String wsManutencaoClientes;
    @BindString(R.string.ConsultaMaquinarias) String wsConsultaMaquinarias;
    @BindString(R.string.ManutencaoMaquinarias) String wsManutencaoMaquinarias;
    @BindString(R.string.ConsultaEmpleados) String wsConsultaEmpleados;
    @BindString(R.string.ManutencaoCultivos) String wsManutencaoCultivos;
    @BindString(R.string.ConsultaAlertaPlagas) String wsConsultaAlertaPlagas;
    @BindString(R.string.ManutencaoAlertaPlagas) String wsManutencaoAlertaPlagas;
    @BindString(R.string.ConsultaVisitas) String wsConsultaVisitas;
    @BindString(R.string.ManutencaoVisitas) String wsManutencaoVisitas;
    @BindString(R.string.ConsultaNegociacoes) String wsConsultaNegociacoes;
    @BindString(R.string.ManutencaoNegociacoes) String wsManutencaoNegociacoes;
    @BindString(R.string.ConsultaProducto) String wsConsultaProducto;
    @BindString(R.string.ConsultaTaxas) String wsConsultaTaxas;
    @BindString(R.string.ConsultaPrecos) String wsConsultaPrecos;
    @BindString(R.string.ConsultaTipoProducto) String wsConsultaTipoProducto;
    @BindString(R.string.ManutencaoProductos) String wsManutencaoProductos;
    @BindString(R.string.ConsultaPremios) String wsConsultaPremios;
    @BindString(R.string.ManutencaoPremios) String wsManutencaoPremios;
    @BindString(R.string.ManutencaoDespesas) String wsManutencaoDespesas;
    @BindString(R.string.ConsultaDespesas) String wsConsultaDespesas;
    @BindString(R.string.ManutencaoAlterarSenha) String wsManutencaoAlterarSenha;
    @BindString(R.string.ManutencaoMensagems) String wsManutencaoMensagems;
    @BindString(R.string.ConsultaMensagens) String wsConsultaMensagens;
    @BindString(R.string.ManutencaoPontoInteresse) String wsManutencaoPontoInteresse;
    @BindString(R.string.ConsultaPontosInteresse) String wsConsultaPontosInteresse;
    @BindString(R.string.ManutencaoTareas) String wsManutencaoTareas;
    @BindString(R.string.ConsultaTareas) String wsConsultaTareas;
    @BindString(R.string.ConsultaTipoTarea) String wsConsultaTipoTarea;
    @BindString(R.string.ConsultaBalance) String wsConsultaBalance;
    @BindString(R.string.ConsultaMenu) String wsConsultaMenu;
    @BindString(R.string.ConsultaTipoContrato) String wsConsultaTipoContrato;
    @BindString(R.string.ConsultaTipoDespesa) String wsConsultaTipoDespesa;
    @BindString(R.string.ConsultaTipoDespesaTempo) String wsConsultaTipoDespesaTempo;
    @BindString(R.string.ConsultaLotegado) String wsConsultaLotegado;
    @BindString(R.string.ManutencaoLotegado) String wsManutencaoLotegado;
    @BindString(R.string.ConsultaGado) String wsConsultaGado;
    @BindString(R.string.ManutencaoGado) String wsManutencaoGado;
    @BindString(R.string.ConsultaHistorialConsumo) String wsConsultaHistorialConsumo;
    @BindString(R.string.ManutencaoHistorialConsumo) String wsManutencaoHistorialConsumo;
    @BindString(R.string.ConsultaHistorialEngorde) String wsConsultaHistorialEngorde;
    @BindString(R.string.ManutencaoHistorialEngorde) String wsManutencaoHistorialEngorde;
    @BindString(R.string.ConsultaHistorialLeche) String wsConsultaHistorialLeche;
    @BindString(R.string.ManutencaoHistorialLeche) String wsManutencaoHistorialLeche;
    @BindString(R.string.ConsultaVentaGado) String wsConsultaVentaGado;
    @BindString(R.string.ManutencaoVentaGado) String wsManutencaoVentaGado;
    @BindString(R.string.ConsultaSolo) String wsConsultaSolo;
    @BindString(R.string.ManutencaoSolo) String wsManutencaoSolo;
    @BindString(R.string.ManutencaoSolo2) String wsManutencaoSolo2;

    @BindString(R.string.ConsultaItemsMenu) String wsConsultaItemsMenu;




    public abstract int getLayout();
    public abstract void getArrayResults(JSONArray response, String option);

    public Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        unbinder = ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        hideSoftKeyboard();
    }

    public void ToastMsg(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }




    public String getWsConsultaSectores() {
        return wsConsultaSectores;
    }

    public String getWsLogin() {
        return wsLogin;
    }

    public String getWsConsultaCosechas() {
        return wsConsultaCosechas;
    }

    public String getWsConsultaCultivos() {
        return wsConsultaCultivos;
    }

    public String getWsConsultaItemsMenu() {
        return wsConsultaItemsMenu;
    }

    public String getWsConsultaPlagas() {
        return wsConsultaPlagas;
    }

    public String getWsConsultaCotacoes() {
        return wsConsultaCotacoes;
    }

    public String getWsConsultaClientes() {
        return wsConsultaClientes;
    }

    public String getWsConsultaMaquinarias() {
        return wsConsultaMaquinarias;
    }

    public String getWsConsultaEmpleados() {
        return wsConsultaEmpleados;
    }

    public String getWsManutencaoCosechas() {
        return wsManutencaoCosechas;
    }

    public String getWsConsultaCultivosDetalle() {
        return wsConsultaCultivosDetalle;
    }

    public String getWsManutencaoCultivos() {
        return wsManutencaoCultivos;
    }

    public String getWsManutencaoClientes() {
        return wsManutencaoClientes;
    }

    public String getWsManutencaoMaquinarias() {
        return wsManutencaoMaquinarias;
    }

    public String getWsConsultaAlertaPlagas() {
        return wsConsultaAlertaPlagas;
    }

    public String getWsManutencaoAlertaPlagas() {
        return wsManutencaoAlertaPlagas;
    }

    public String getWsConsultaVisitas() {
        return wsConsultaVisitas;
    }

    public String getWsManutencaoVisitas() {
        return wsManutencaoVisitas;
    }

    public String getWsConsultaNegociacoes() {
        return wsConsultaNegociacoes;
    }

    public String getWsManutencaoNegociacoes() {
        return wsManutencaoNegociacoes;
    }

    public String getWsConsultaProducto() {
        return wsConsultaProducto;
    }

    public String getWsConsultaTaxas() {
        return wsConsultaTaxas;
    }

    public String getWsConsultaPrecos() {
        return wsConsultaPrecos;
    }

    public String getWsConsultaTipoProducto() {
        return wsConsultaTipoProducto;
    }

    public String getWsManutencaoProductos() {
        return wsManutencaoProductos;
    }

    public String getWsConsultaPremios() {
        return wsConsultaPremios;
    }

    public String getWsManutencaoDespesas() {
        return wsManutencaoDespesas;
    }

    public String getWsConsultaDespesas() {
        return wsConsultaDespesas;
    }

    public String getWsManutencaoAlterarSenha() {
        return wsManutencaoAlterarSenha;
    }

    public String getWsManutencaoMensagems() {
        return wsManutencaoMensagems;
    }

    public String getWsConsultaMensagens() {
        return wsConsultaMensagens;
    }

    public String getWsManutencaoPontoInteresse() {
        return wsManutencaoPontoInteresse;
    }

    public String getWsConsultaPontosInteresse() {
        return wsConsultaPontosInteresse;
    }

    public String getWsManutencaoTareas() {
        return wsManutencaoTareas;
    }

    public String getWsConsultaTareas() {
        return wsConsultaTareas;
    }

    public String getWsConsultaTipoTarea() {
        return wsConsultaTipoTarea;
    }

    public String getWsConsultaBalance() {
        return wsConsultaBalance;
    }

    public String getWsManutencaoPremios() {
        return wsManutencaoPremios;
    }

    public String getWsConsultaMenu() {
        return wsConsultaMenu;
    }

    public String getWsConsultaTipoContrato() {
        return wsConsultaTipoContrato;
    }

    public String getWsConsultaTipoDespesa() {
        return wsConsultaTipoDespesa;
    }

    public String getWsConsultaTipoDespesaTempo() {
        return wsConsultaTipoDespesaTempo;
    }

    public String getWsConsultaLotegado() {
        return wsConsultaLotegado;
    }

    public String getWsManutencaoLotegado() {
        return wsManutencaoLotegado;
    }

    public String getWsConsultaGado() {
        return wsConsultaGado;
    }

    public String getWsManutencaoGado() {
        return wsManutencaoGado;
    }

    public String getWsConsultaHistorialConsumo() {
        return wsConsultaHistorialConsumo;
    }

    public String getWsManutencaoHistorialConsumo() {
        return wsManutencaoHistorialConsumo;
    }

    public String getWsConsultaHistorialEngorde() {
        return wsConsultaHistorialEngorde;
    }

    public String getWsManutencaoHistorialEngorde() {
        return wsManutencaoHistorialEngorde;
    }

    public String getWsConsultaHistorialLeche() {
        return wsConsultaHistorialLeche;
    }

    public String getWsManutencaoHistorialLeche() {
        return wsManutencaoHistorialLeche;
    }

    public String getWsConsultaVentaGado() {
        return wsConsultaVentaGado;
    }

    public String getWsManutencaoVentaGado() {
        return wsManutencaoVentaGado;
    }

    public String getWsConsultaSolo() {
        return wsConsultaSolo;
    }

    public String getWsManutencaoSolo() {
        return wsManutencaoSolo;
    }

    public String getWsManutencaoSolo2() {
        return wsManutencaoSolo2;
    }


}
