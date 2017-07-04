package projeto.undercode.com.proyectobrapro.requests;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import projeto.undercode.com.proyectobrapro.activity.BalanceTotalController;
import projeto.undercode.com.proyectobrapro.base.BaseController;
import projeto.undercode.com.proyectobrapro.base.BaseRequest;
import projeto.undercode.com.proyectobrapro.controllers.AlertaPragasController;
import projeto.undercode.com.proyectobrapro.controllers.AlterarSenhaController;
import projeto.undercode.com.proyectobrapro.controllers.AtivarUsuariosController;
import projeto.undercode.com.proyectobrapro.controllers.BalanceController;
import projeto.undercode.com.proyectobrapro.controllers.ClientesController;
import projeto.undercode.com.proyectobrapro.controllers.ColheitasController;
import projeto.undercode.com.proyectobrapro.controllers.ConsultaController;
import projeto.undercode.com.proyectobrapro.controllers.CotacoesController;
import projeto.undercode.com.proyectobrapro.controllers.EmpregadosController;
import projeto.undercode.com.proyectobrapro.controllers.PragasController;
import projeto.undercode.com.proyectobrapro.controllers.RegistroController;
import projeto.undercode.com.proyectobrapro.controllers.SafrasController;
import projeto.undercode.com.proyectobrapro.controllers.DespesasController;
import projeto.undercode.com.proyectobrapro.controllers.GadoController;
import projeto.undercode.com.proyectobrapro.controllers.GadoUnitarioController;
import projeto.undercode.com.proyectobrapro.controllers.HistorialController;
import projeto.undercode.com.proyectobrapro.controllers.LoginController;
import projeto.undercode.com.proyectobrapro.controllers.MaquinariasController;
import projeto.undercode.com.proyectobrapro.controllers.MensagemsController;
import projeto.undercode.com.proyectobrapro.controllers.MenuController;
import projeto.undercode.com.proyectobrapro.controllers.NegociacoesController;
import projeto.undercode.com.proyectobrapro.controllers.PontoInteresseController;
import projeto.undercode.com.proyectobrapro.controllers.PrecoController;
import projeto.undercode.com.proyectobrapro.controllers.PremiosController;
import projeto.undercode.com.proyectobrapro.controllers.ProdutosController;
import projeto.undercode.com.proyectobrapro.controllers.Sectores2Controller;
import projeto.undercode.com.proyectobrapro.controllers.SetoresController;
import projeto.undercode.com.proyectobrapro.controllers.SincronizacaoController;
import projeto.undercode.com.proyectobrapro.controllers.SoloController;
import projeto.undercode.com.proyectobrapro.controllers.TarefasController;
import projeto.undercode.com.proyectobrapro.controllers.TaxasController;
import projeto.undercode.com.proyectobrapro.controllers.VentaGadoController;
import projeto.undercode.com.proyectobrapro.controllers.VisitasController;
import projeto.undercode.com.proyectobrapro.forms.AlertaPragaForm;
import projeto.undercode.com.proyectobrapro.forms.EmpregadoForm;
import projeto.undercode.com.proyectobrapro.forms.SafraForm;
import projeto.undercode.com.proyectobrapro.forms.DespesaForm;
import projeto.undercode.com.proyectobrapro.forms.GadoForm;
import projeto.undercode.com.proyectobrapro.forms.HistorialConsumoForm;
import projeto.undercode.com.proyectobrapro.forms.NegociacaoForm;
import projeto.undercode.com.proyectobrapro.forms.PremioForm;
import projeto.undercode.com.proyectobrapro.forms.ProdutoForm;
import projeto.undercode.com.proyectobrapro.forms.SoloForm;
import projeto.undercode.com.proyectobrapro.forms.TarefaForm;
import projeto.undercode.com.proyectobrapro.forms.VentaGadoForm;
import projeto.undercode.com.proyectobrapro.forms.VisitaForm;
import projeto.undercode.com.proyectobrapro.utils.ApplicationController;

/**
 * Created by Level on 16/09/2016.
 */
public class ArrayRequest extends BaseRequest {

    ProgressDialog progressDialog;

    public ArrayRequest (Context context, String url, JSONObject params, String option) {
        super(context, url, params, option);
    }

    public void returnControl(JSONArray response) {


        BaseController controller = null;

        // Si no se define no aparece el menu

        Log.d("holi", "returnControl");

        if (getContext().getClass().getSimpleName().equals("LoginController")) {
            controller = (LoginController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("Sectores2Controller")) {
            controller = (Sectores2Controller) getContext();
        } else if (getContext().getClass().getSimpleName().equals("ColheitasController")) {
            controller = (ColheitasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("SafrasController")) {
            controller = (SafrasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("PragasController")) {
            controller = (PragasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("CotacoesController")) {
            controller = (CotacoesController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("ClientesController")) {
            controller = (ClientesController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("MaquinariasController")) {
            controller = (MaquinariasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("EmpregadosController")) {
            controller = (EmpregadosController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("VisitasController")) {
            controller = (VisitasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("AlertaPragasController")) {
            controller = (AlertaPragasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("SafraForm")) {
            controller = (SafraForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("AlertaPragaForm")) {
            controller = (AlertaPragaForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("VisitaForm")) {
            controller = (VisitaForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("NegociacoesController")) {
            controller = (NegociacoesController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("NegociacaoForm")) {
            controller = (NegociacaoForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("TaxasController")) {
            controller = (TaxasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("PrecoController")) {
            controller = (PrecoController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("ProdutosController")) {
            controller = (ProdutosController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("ProdutoForm")) {
            controller = (ProdutoForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("PremiosController")) {
            controller = (PremiosController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("DespesasController")) {
            controller = (DespesasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("AlterarSenhaController")) {
            controller = (AlterarSenhaController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("MensagemsController")) {
            controller = (MensagemsController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("PontoInteresseController")) {
            controller = (PontoInteresseController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("TarefaForm")) {
            controller = (TarefaForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("TarefasController")) {
            controller = (TarefasController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("BalanceController")) {
            controller = (BalanceController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("PremioForm")) {
            controller = (PremioForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("MenuController")) {
            controller = (MenuController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("EmpregadoForm")) {
            controller = (EmpregadoForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("DespesaForm")) {
            controller = (DespesaForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("BalanceTotalController")) {
            controller = (BalanceTotalController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("GadoController")) {
            controller = (GadoController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("GadoUnitarioController")) {
            controller = (GadoUnitarioController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("GadoForm")) {
            controller = (GadoForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("HistorialController")) {
            controller = (HistorialController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("HistorialConsumoForm")) {
            controller = (HistorialConsumoForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("VentaGadoController")) {
            controller = (VentaGadoController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("VentaGadoForm")) {
            controller = (VentaGadoForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("SoloForm")) {
            controller = (SoloForm) getContext();
        } else if (getContext().getClass().getSimpleName().equals("SoloController")) {
            controller = (SoloController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("SetoresController")) {
            controller = (SetoresController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("ConsultaController")) {
            controller = (ConsultaController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("RegistroController")) {
            controller = (RegistroController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("AtivarUsuariosController")) {
            controller = (AtivarUsuariosController) getContext();
        } else if (getContext().getClass().getSimpleName().equals("SincronizacaoController")) {
            controller = (SincronizacaoController) getContext();
        }


        if ( controller != null ){
            if (getOption() == "ItemsMenu") {
                //BaseDrawer.renderMenu(response);
            } else {

                controller.getArrayResults(response, getOption());
                progressDialog.dismiss();

            }
        }

    }



    public void makeRequest() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        //RequestQueue queue = Volley.newRequestQueue(getContext());


        String urlQuery = getUrlQuery();

        Log.d("holi", urlQuery);

            JsonArrayRequest getRequest =new JsonArrayRequest(urlQuery,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("holi", "returnControlFuera1");
                            returnControl(response);
                            Log.d("holi", "returnControlFuera2");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("holi", "returnControlFueraError");
                    Log.d("holi", error.toString());
                    returnControl(null);
                }
            }
        );

        ApplicationController.getInstance().addToRequestQueue(getRequest);

        //queue.add(getRequest);


    }
}

