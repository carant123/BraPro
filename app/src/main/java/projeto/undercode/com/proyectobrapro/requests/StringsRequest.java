package projeto.undercode.com.proyectobrapro.requests;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import projeto.undercode.com.proyectobrapro.base.BaseRequest;
import projeto.undercode.com.proyectobrapro.controllers.AlertaPragasController;
import projeto.undercode.com.proyectobrapro.controllers.AlterarSenhaController;
import projeto.undercode.com.proyectobrapro.controllers.AtivarUsuariosController;
import projeto.undercode.com.proyectobrapro.controllers.ClientesController;
import projeto.undercode.com.proyectobrapro.controllers.ColheitasController;
import projeto.undercode.com.proyectobrapro.controllers.DespesasController;
import projeto.undercode.com.proyectobrapro.controllers.EmpregadosController;
import projeto.undercode.com.proyectobrapro.controllers.GadoController;
import projeto.undercode.com.proyectobrapro.controllers.HistorialController;
import projeto.undercode.com.proyectobrapro.controllers.MaquinariasController;
import projeto.undercode.com.proyectobrapro.controllers.NegociacoesController;
import projeto.undercode.com.proyectobrapro.controllers.PremiosController;
import projeto.undercode.com.proyectobrapro.controllers.ProdutosController;
import projeto.undercode.com.proyectobrapro.controllers.RegistroController;
import projeto.undercode.com.proyectobrapro.controllers.SetoresController;
import projeto.undercode.com.proyectobrapro.controllers.SincronizacaoController;
import projeto.undercode.com.proyectobrapro.controllers.SoloController;
import projeto.undercode.com.proyectobrapro.controllers.TarefasController;
import projeto.undercode.com.proyectobrapro.controllers.VentaGadoController;
import projeto.undercode.com.proyectobrapro.controllers.VisitasController;
import projeto.undercode.com.proyectobrapro.forms.ClienteForm;
import projeto.undercode.com.proyectobrapro.forms.ColheitaForm;
import projeto.undercode.com.proyectobrapro.forms.EmpregadoForm;
import projeto.undercode.com.proyectobrapro.forms.SafraForm;
import projeto.undercode.com.proyectobrapro.forms.DespesaForm;
import projeto.undercode.com.proyectobrapro.forms.GadoForm;
import projeto.undercode.com.proyectobrapro.forms.HistorialConsumoForm;
import projeto.undercode.com.proyectobrapro.forms.HistorialEngordeForm;
import projeto.undercode.com.proyectobrapro.forms.HistorialLecheForm;
import projeto.undercode.com.proyectobrapro.forms.LoteGadoForm;
import projeto.undercode.com.proyectobrapro.forms.MaquinariaForm;
import projeto.undercode.com.proyectobrapro.forms.MensagemForm;
import projeto.undercode.com.proyectobrapro.forms.NegociacaoForm;
import projeto.undercode.com.proyectobrapro.forms.PontoInteresseForm;
import projeto.undercode.com.proyectobrapro.forms.PremioForm;
import projeto.undercode.com.proyectobrapro.forms.ProdutoForm;
import projeto.undercode.com.proyectobrapro.forms.SoloForm;
import projeto.undercode.com.proyectobrapro.forms.TarefaForm;
import projeto.undercode.com.proyectobrapro.forms.VentaGadoForm;
import projeto.undercode.com.proyectobrapro.forms.VisitaForm;
import projeto.undercode.com.proyectobrapro.fragments.AtivarUsuariosFragment;
import projeto.undercode.com.proyectobrapro.utils.ApplicationController;

/**
 * Created by Tecnew on 24/05/2016.
 */
public class StringsRequest  extends BaseRequest {


    ProgressDialog progressDialog;


    public StringsRequest (Context context, String url, JSONObject params, String option) {
        super(context, url, params, option);
    }

    public void returnControl(String response2) {
        JSONArray response = new JSONArray();

        if ( getContext().getClass().getSimpleName().equals("ColheitaForm") ) {
            ColheitaForm sc = (ColheitaForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("SafraForm") ) {
            SafraForm sc = (SafraForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("ClienteForm") ) {
            ClienteForm sc = (ClienteForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("EmpregadoForm") ) {
            EmpregadoForm sc = (EmpregadoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("MaquinariaForm") ) {
            MaquinariaForm sc = (MaquinariaForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("VisitaForm") ) {
            VisitaForm sc = (VisitaForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("NegociacaoForm") ) {
            NegociacaoForm sc = (NegociacaoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("ProdutoForm") ) {
            ProdutoForm sc = (ProdutoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("DespesaForm") ) {
            DespesaForm sc = (DespesaForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("AlterarSenhaController") ) {
            AlterarSenhaController sc = (AlterarSenhaController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("MensagemForm") ) {
            MensagemForm sc = (MensagemForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("PontoInteresseForm") ) {
            PontoInteresseForm sc = (PontoInteresseForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("ColheitasController") ) {
            ColheitasController sc = (ColheitasController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("TarefaForm") ) {
            TarefaForm sc = (TarefaForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("MaquinariasController") ) {
            MaquinariasController sc = (MaquinariasController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("EmpregadosController") ) {
            EmpregadosController sc = (EmpregadosController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("ClientesController") ) {
            ClientesController sc = (ClientesController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("AlertaPragasController") ) {
            AlertaPragasController sc = (AlertaPragasController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("ProdutosController") ) {
            ProdutosController sc = (ProdutosController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("NegociacoesController") ) {
            NegociacoesController sc = (NegociacoesController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("TarefasController") ) {
            TarefasController sc = (TarefasController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("VisitasController") ) {
            VisitasController sc = (VisitasController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("DespesasController") ) {
            DespesasController sc = (DespesasController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("PremiosController") ) {
            PremiosController sc = (PremiosController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("PremioForm") ) {
            PremioForm sc = (PremioForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("LoteGadoForm") ) {
            LoteGadoForm sc = (LoteGadoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("GadoController") ) {
            GadoController sc = (GadoController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("GadoForm") ) {
            GadoForm sc = (GadoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("HistorialEngordeForm") ) {
            HistorialEngordeForm sc = (HistorialEngordeForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("HistorialConsumoForm") ) {
            HistorialConsumoForm sc = (HistorialConsumoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("HistorialLecheForm") ) {
            HistorialLecheForm sc = (HistorialLecheForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("HistorialController") ) {
            HistorialController sc = (HistorialController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("VentaGadoController") ) {
            VentaGadoController sc = (VentaGadoController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("VentaGadoForm") ) {
            VentaGadoForm sc = (VentaGadoForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("SoloForm") ) {
            SoloForm sc = (SoloForm) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("SoloController") ) {
            SoloController sc = (SoloController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("SetoresController") ) {
            SetoresController sc = (SetoresController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("RegistroController") ) {
            RegistroController sc = (RegistroController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("AtivarUsuariosController") ) {
            AtivarUsuariosController sc = (AtivarUsuariosController) getContext();
            sc.getArrayResults(response, getOption());
        } else if ( getContext().getClass().getSimpleName().equals("SincronizacaoController") ) {
            SincronizacaoController sc = (SincronizacaoController) getContext();
            //sc.getArrayResults(response, getOption());
            sc.getStringresults(response2, getOption());
        }

        progressDialog.dismiss();
    }

    public void makeRequest() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        //RequestQueue queue = Volley.newRequestQueue(getContext());
        String urlQuery = getUrlQuery();
        Log.d("urlQuery", urlQuery);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlQuery,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //ToastMsg("Done!");
                    //ToastMsg("Feito!");
                    returnControl(response);
                }
            }, new Response.ErrorListener() {
            @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error Holi", error.toString());
                    //ToastMsg(error.toString());
                }
            }
        );

        //queue.add(stringRequest);
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
    }

    public void ToastMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


    public void postRequest() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        final JSONObject jsonBody = getParameters();

        final String mRequestBody = jsonBody.toString();

        StringRequest sr = new StringRequest(Request.Method.POST, getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastMsg("Done!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastMsg("Failed!");
                Log.d("error Holi", error.toString());
            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                byte[] aux = null;

                try {
                    aux = mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, "utf-8");
                }

                return aux;
            }
        };

        queue.add(sr);
    }



}
