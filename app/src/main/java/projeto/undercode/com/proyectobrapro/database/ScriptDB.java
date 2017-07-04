package projeto.undercode.com.proyectobrapro.database;

import android.provider.BaseColumns;

import projeto.undercode.com.proyectobrapro.models.VentaGado;

public class ScriptDB {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String IMAGE_TYPE = " IMAGE";
    private static final String DATE_TYPE = " DATE";
    private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";


    private ScriptDB() {}

    // *** ALERTA PLAGA TABLE *** //

    public static class Alerta_plagaEntry implements BaseColumns {
        public static final String TABLE_NAME = "alerta_plaga";
        public static final String COLUMN_Id_alerta_plaga = "Id_alerta_plaga";
        public static final String COLUMN_Id_sector = "Id_sector";
        public static final String COLUMN_Id_plaga = "Id_plaga";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Fecha_registro = "Fecha_registro";
        public static final String COLUMN_Descripcion = "Descripcion";
        public static final String COLUMN_Status = "Status";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_ALERTA_PLAGA =
            "CREATE TABLE "  +" IF NOT EXISTS " + Alerta_plagaEntry.TABLE_NAME + " (" +
                    Alerta_plagaEntry.COLUMN_Id_alerta_plaga + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_Id_sector + INTEGER_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_Id_plaga + INTEGER_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_Fecha_registro + DATE_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_Descripcion + TEXT_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_Status + TEXT_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    Alerta_plagaEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + Alerta_plagaEntry.COLUMN_Id_sector + ")" + " REFERENCES " + SectorEntry.TABLE_NAME + "(" + SectorEntry.COLUMN_Id_sector + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + Alerta_plagaEntry.COLUMN_Id_plaga + ")" + " REFERENCES " + PlagaEntry.TABLE_NAME + "(" + PlagaEntry.COLUMN_Id_plaga + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + Alerta_plagaEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

            " )";


    // *** ANIMAL TABLE *** //

    public static class AnimalEntry implements BaseColumns {
        public static final String TABLE_NAME = "animal";
        public static final String COLUMN_id_animal = "id_animal";
        public static final String COLUMN_id_lote_gado = "id_lote_gado";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_nombre = "nombre";
        public static final String COLUMN_peso_inicial = "peso_inicial";
        public static final String COLUMN_cod_adquisicao = "cod_adquisicao";
        public static final String COLUMN_tipo_adquisicao = "tipo_adquisicao";
        public static final String COLUMN_estado = "estado";

    }

    public static final String CREATE_ANIMAL =
            "CREATE TABLE "  +" IF NOT EXISTS " + AnimalEntry.TABLE_NAME + " (" +
                    AnimalEntry.COLUMN_id_animal + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    AnimalEntry.COLUMN_id_lote_gado + INTEGER_TYPE + COMMA_SEP +
                    AnimalEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    AnimalEntry.COLUMN_nombre + TEXT_TYPE + COMMA_SEP +
                    AnimalEntry.COLUMN_peso_inicial + INTEGER_TYPE + COMMA_SEP +
                    AnimalEntry.COLUMN_cod_adquisicao + INTEGER_TYPE + COMMA_SEP +
                    AnimalEntry.COLUMN_tipo_adquisicao + TEXT_TYPE + COMMA_SEP +
                    AnimalEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + AnimalEntry.COLUMN_id_lote_gado + ")" + " REFERENCES " + Lote_gadoEntry.TABLE_NAME + "(" + Lote_gadoEntry.COLUMN_id_lote_gado + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + AnimalEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

            " )";


    // *** CLIENTE TABLE *** //

    public static class ClienteEntry implements BaseColumns {
        public static final String TABLE_NAME = "cliente";
        public static final String COLUMN_id_cliente = "id_cliente";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_nombre = "nombre";
        public static final String COLUMN_organizacion = "organizacion";
        public static final String COLUMN_numero = "numero";
        public static final String COLUMN_direccion = "direccion";
        public static final String COLUMN_area = "area";
        public static final String COLUMN_cpf = "cpf";
        public static final String COLUMN_data_insercao = "data_insercao";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_CLIENTE =
            "CREATE TABLE "  +" IF NOT EXISTS " + ClienteEntry.TABLE_NAME + " (" +
                    ClienteEntry.COLUMN_id_cliente + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    ClienteEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_nombre + TEXT_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_organizacion + TEXT_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_numero + TEXT_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_direccion + TEXT_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_area + TEXT_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_cpf + TEXT_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_data_insercao + DATE_TYPE + COMMA_SEP +
                    ClienteEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + ClienteEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +
            " )";



    // *** COMPRA TABLE *** //

    public static class CompraEntry implements BaseColumns {
        public static final String TABLE_NAME = "compra";
        public static final String COLUMN_id_compra = "id_compra";
        public static final String COLUMN_fecha = "fecha";
        public static final String COLUMN_precio = "precio";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_COMPRA =
            "CREATE TABLE "  +" IF NOT EXISTS " + CompraEntry.TABLE_NAME + " (" +
                    CompraEntry.COLUMN_id_compra + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    CompraEntry.COLUMN_fecha + DATE_TYPE + COMMA_SEP +
                    CompraEntry.COLUMN_precio + INTEGER_TYPE + COMMA_SEP +
                    CompraEntry.COLUMN_estado + TEXT_TYPE +
            " )";


    // *** CONSULTA_SOLO TABLE *** //

    public static class Consulta_SoloEntry implements BaseColumns {
        public static final String TABLE_NAME = "consulta_solo";
        public static final String COLUMN_id_consulta_solo = "id_consulta_solo";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_id_sector = "id_sector";
        public static final String COLUMN_fosforo_status = "fosforo_status";
        public static final String COLUMN_fosforo_value = "fosforo_value";
        public static final String COLUMN_potasio_status = "potasio_status";
        public static final String COLUMN_potasio_value = "potasio_value";
        public static final String COLUMN_calcio_status = "calcio_status";
        public static final String COLUMN_calcio_value = "calcio_value";
        public static final String COLUMN_magnesio_status = "magnesio_status";
        public static final String COLUMN_magnesio_value = "magnesio_value";
        public static final String COLUMN_aluminio_status = "aluminio_status";
        public static final String COLUMN_aluminio_value = "aluminio_value";
        public static final String COLUMN_material_organico_status = "material_organico_status";
        public static final String COLUMN_material_organico_value = "material_organico_value";
        public static final String COLUMN_hidrogeno_status = "hidrogeno_status";
        public static final String COLUMN_hidrogeno_value = "hidrogeno_value";
        public static final String COLUMN_potencial_hidrogenionico_status = "potencial_hidrogenionico_status";
        public static final String COLUMN_potencial_hidrogenionico_value = "potencial_hidrogenionico_value";
        public static final String COLUMN_data_consulta = "data_consulta";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_CONSULTA_SOLO =
            "CREATE TABLE "  +" IF NOT EXISTS " + Consulta_SoloEntry.TABLE_NAME + " (" +
                    Consulta_SoloEntry.COLUMN_id_consulta_solo + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_id_sector + INTEGER_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_fosforo_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_fosforo_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_potasio_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_potasio_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_calcio_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_calcio_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_magnesio_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_magnesio_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_aluminio_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_aluminio_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_material_organico_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_material_organico_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_hidrogeno_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_hidrogeno_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_potencial_hidrogenionico_status + TEXT_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_potencial_hidrogenionico_value + NUMERIC_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_data_consulta + DATE_TYPE + COMMA_SEP +
                    Consulta_SoloEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + Consulta_SoloEntry.COLUMN_id_sector + ")" + " REFERENCES " + SectorEntry.TABLE_NAME + "(" + SectorEntry.COLUMN_Id_sector + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + Consulta_SoloEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

            " )";


    // *** COORDENADAS TABLE *** //

    public static class CoordenadasEntry implements BaseColumns {
        public static final String TABLE_NAME = "coordenadas";
        public static final String COLUMN_id_coordenada = "id_coordenada";
        public static final String COLUMN_latitude = "latitude";
        public static final String COLUMN_longitude = "longitude";
        public static final String COLUMN_id_sector = "id_sector";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_COORDENADAS =
            "CREATE TABLE " +" IF NOT EXISTS " + CoordenadasEntry.TABLE_NAME + " (" +
                    CoordenadasEntry.COLUMN_id_coordenada + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    CoordenadasEntry.COLUMN_latitude + TEXT_TYPE + COMMA_SEP +
                    CoordenadasEntry.COLUMN_longitude + TEXT_TYPE + COMMA_SEP +
                    CoordenadasEntry.COLUMN_id_sector + INTEGER_TYPE + COMMA_SEP +
                    CoordenadasEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + CoordenadasEntry.COLUMN_id_sector + ")" + " REFERENCES " + SectorEntry.TABLE_NAME + "(" + SectorEntry.COLUMN_Id_sector + ")" +

            " )";


    // *** COSECHA TABLE *** //

    public static class CosechaEntry implements BaseColumns {
        public static final String TABLE_NAME = "cosecha";
        public static final String COLUMN_Id_cosecha = "Id_cosecha";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_COSECHA =
            "CREATE TABLE " +" IF NOT EXISTS " + CosechaEntry.TABLE_NAME + " (" +
                    CosechaEntry.COLUMN_Id_cosecha + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    CosechaEntry.COLUMN_Nombre + TEXT_TYPE  + COMMA_SEP +
                    CosechaEntry.COLUMN_id_usuario + INTEGER_TYPE  + COMMA_SEP +
                    CosechaEntry.COLUMN_estado + TEXT_TYPE  + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + CosechaEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +
            " )";


    // *** COTACOES TABLE *** //

    public static class CotacoesEntry implements BaseColumns {
        public static final String TABLE_NAME = "cotacoes";
        public static final String COLUMN_id_cotacoes = "id_cotacoes";
        public static final String COLUMN_cosecha = "cosecha";
        public static final String COLUMN_periodo = "periodo";
        public static final String COLUMN_cotacao = "cotacao";
        public static final String COLUMN_diferenca = "diferenca";
        public static final String COLUMN_fechamento = "fechamento";
        public static final String COLUMN_data_atualizacao = "data_atualizacao";
    }

    public static final String CREATE_COTACOES =
            "CREATE TABLE "  +" IF NOT EXISTS " + CotacoesEntry.TABLE_NAME + " (" +
                    CotacoesEntry.COLUMN_id_cotacoes + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    CotacoesEntry.COLUMN_cosecha + INTEGER_TYPE  + COMMA_SEP +
                    CotacoesEntry.COLUMN_periodo + TEXT_TYPE  + COMMA_SEP +
                    CotacoesEntry.COLUMN_cotacao + NUMERIC_TYPE  + COMMA_SEP +
                    CotacoesEntry.COLUMN_diferenca + NUMERIC_TYPE  + COMMA_SEP +
                    CotacoesEntry.COLUMN_fechamento + NUMERIC_TYPE  + COMMA_SEP +
                    CotacoesEntry.COLUMN_data_atualizacao + DATE_TYPE  +
            " )";


    // *** CULTIVO TABLE *** //

    public static class CultivoEntry implements BaseColumns {
        public static final String TABLE_NAME = "cultivo";
        public static final String COLUMN_Id_cultivo = "Id_cultivo";
        public static final String COLUMN_Id_cosecha = "Id_cosecha";
        public static final String COLUMN_Inicio = "Inicio";
        public static final String COLUMN_Fim = "Fim";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_CULTIVO =
            "CREATE TABLE "  +" IF NOT EXISTS " + CultivoEntry.TABLE_NAME + " (" +
                    CultivoEntry.COLUMN_Id_cultivo + INTEGER_TYPE + COMMA_SEP +
                    CultivoEntry.COLUMN_Id_cosecha + INTEGER_TYPE  + COMMA_SEP +
                    CultivoEntry.COLUMN_Inicio + DATE_TYPE  + COMMA_SEP +
                    CultivoEntry.COLUMN_Fim + DATE_TYPE  + COMMA_SEP +
                    CultivoEntry.COLUMN_estado + TEXT_TYPE  + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + CultivoEntry.COLUMN_Id_cosecha + ")" + " REFERENCES " + CosechaEntry.TABLE_NAME + "(" + CosechaEntry.COLUMN_Id_cosecha + ")" +

            " )";


    // *** DESPESAS TABLE *** //

    public static class DespesasEntry implements BaseColumns {
        public static final String TABLE_NAME = "despesas";
        public static final String COLUMN_id_despesa = "id_despesa";
        public static final String COLUMN_usuario = "usuario";
        public static final String COLUMN_pim = "pim";
        public static final String COLUMN_imei = "imei";
        public static final String COLUMN_latitude = "latitude";
        public static final String COLUMN_longitude = "longitude";
        public static final String COLUMN_versao = "versao";
        public static final String COLUMN_valor = "valor";
        public static final String COLUMN_data_despesa = "data_despesa";
        public static final String COLUMN_tipo_despesa_tempo = "tipo_despesa_tempo";
        public static final String COLUMN_tipo_despesa = "tipo_despesa";
        public static final String COLUMN_estado = "estado";

    }

    public static final String CREATE_DESPESAS =
            "CREATE TABLE "  +" IF NOT EXISTS " + DespesasEntry.TABLE_NAME + " (" +
                    DespesasEntry.COLUMN_id_despesa + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    DespesasEntry.COLUMN_usuario + INTEGER_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_pim + TEXT_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_imei + TEXT_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_latitude + TEXT_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_longitude + TEXT_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_versao + TEXT_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_valor + INTEGER_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_data_despesa + DATE_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_tipo_despesa_tempo + INTEGER_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_tipo_despesa + INTEGER_TYPE + COMMA_SEP +
                    DespesasEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + DespesasEntry.COLUMN_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

            " )";


    // *** EMPLEADO TABLE *** //

    public static class EmpleadoEntry implements BaseColumns {
        public static final String TABLE_NAME = "empleado";
        public static final String COLUMN_Id_empleado = "Id_empleado";
        public static final String COLUMN_Id_usuario = "Id_usuario";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Fecha_contratacion = "Fecha_contratacion";
        public static final String COLUMN_Edad = "Edad";
        public static final String COLUMN_Rol = "Rol";
        public static final String COLUMN_contacto = "contacto";
        public static final String COLUMN_Photo = "Photo";
        public static final String COLUMN_salario = "salario";
        public static final String COLUMN_fin_de_contrato = "fin_de_contrato";
        public static final String COLUMN_tipo_contrato = "tipo_contrato";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_EMPLEADO =
            "CREATE TABLE "  + "IF NOT EXISTS " + EmpleadoEntry.TABLE_NAME + " (" +
                    EmpleadoEntry.COLUMN_Id_empleado + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    EmpleadoEntry.COLUMN_Id_usuario + INTEGER_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_Fecha_contratacion + DATE_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_Edad + INTEGER_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_Rol + TEXT_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_contacto + TEXT_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_Photo + TEXT_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_salario + INTEGER_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_fin_de_contrato + DATE_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_tipo_contrato + INTEGER_TYPE + COMMA_SEP +
                    EmpleadoEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + EmpleadoEntry.COLUMN_Id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

            " )";


    // *** HISTORIAL_CONSUMO TABLE *** //

    public static class Historial_ConsumoEntry implements BaseColumns {
        public static final String TABLE_NAME = "historial_consumo";
        public static final String COLUMN_id_historial_consumo = "id_historial_consumo";
        public static final String COLUMN_id_animal = "id_animal";
        public static final String COLUMN_id_producto = "id_producto";
        public static final String COLUMN_cantidad_consumida = "cantidad_consumida";
        public static final String COLUMN_fecha_consumo = "fecha_consumo";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_HISTORIAL_CONSUMO =
            "CREATE TABLE "  +" IF NOT EXISTS " + Historial_ConsumoEntry.TABLE_NAME + " (" +
                    Historial_ConsumoEntry.COLUMN_id_historial_consumo  +INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    Historial_ConsumoEntry.COLUMN_id_animal + INTEGER_TYPE + COMMA_SEP +
                    Historial_ConsumoEntry.COLUMN_id_producto + INTEGER_TYPE + COMMA_SEP +
                    Historial_ConsumoEntry.COLUMN_cantidad_consumida  +INTEGER_TYPE + COMMA_SEP +
                    Historial_ConsumoEntry.COLUMN_fecha_consumo  +DATE_TYPE + COMMA_SEP +
                    Historial_ConsumoEntry.COLUMN_estado  +TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + Historial_ConsumoEntry.COLUMN_id_producto + ")" + " REFERENCES " + ProductoEntry.TABLE_NAME + "(" + ProductoEntry.COLUMN_Id_producto + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + Historial_ConsumoEntry.COLUMN_id_animal + ")" + " REFERENCES " + AnimalEntry.TABLE_NAME + "(" + AnimalEntry.COLUMN_id_animal + ")" +

            " )";


    // *** HISTORIAL_ENGORDE TABLE *** //

    public static class Historial_EngordeEntry implements BaseColumns {
        public static final String TABLE_NAME = "historial_engorde";
        public static final String COLUMN_id_historial_engorde = "id_historial_engorde";
        public static final String COLUMN_id_animal = "id_animal";
        public static final String COLUMN_peso = "peso";
        public static final String COLUMN_fecha_medicion = "fecha_medicion";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_HISTORIAL_ENGORDE =
            "CREATE TABLE "  +" IF NOT EXISTS " + Historial_EngordeEntry.TABLE_NAME + " (" +
                    Historial_EngordeEntry.COLUMN_id_historial_engorde  +INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    Historial_EngordeEntry.COLUMN_id_animal + INTEGER_TYPE + COMMA_SEP +
                    Historial_EngordeEntry.COLUMN_peso + INTEGER_TYPE + COMMA_SEP +
                    Historial_EngordeEntry.COLUMN_fecha_medicion  +DATE_TYPE + COMMA_SEP +
                    Historial_EngordeEntry.COLUMN_estado  +TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + Historial_EngordeEntry.COLUMN_id_animal + ")" + " REFERENCES " + AnimalEntry.TABLE_NAME + "(" + AnimalEntry.COLUMN_id_animal + ")" +

                    " )";



    // *** HISTORIAL_LECHE TABLE *** //

    public static class Historial_LecheEntry implements BaseColumns {
        public static final String TABLE_NAME = "historial_leche";
        public static final String COLUMN_id_historial_leche = "id_historial_leche";
        public static final String COLUMN_id_animal = "id_animal";
        public static final String COLUMN_cantidad = "cantidad";
        public static final String COLUMN_fecha_obtencao = "fecha_obtencao";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_HISTORIAL_LECHE =
            "CREATE TABLE "  +" IF NOT EXISTS " + Historial_LecheEntry.TABLE_NAME + " (" +
                    Historial_LecheEntry.COLUMN_id_historial_leche  +INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    Historial_LecheEntry.COLUMN_id_animal + INTEGER_TYPE + COMMA_SEP +
                    Historial_LecheEntry.COLUMN_cantidad + INTEGER_TYPE + COMMA_SEP +
                    Historial_LecheEntry.COLUMN_fecha_obtencao  +DATE_TYPE + COMMA_SEP +
                    Historial_LecheEntry.COLUMN_estado  +DATE_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + Historial_LecheEntry.COLUMN_id_animal + ")" + " REFERENCES " + AnimalEntry.TABLE_NAME + "(" + AnimalEntry.COLUMN_id_animal + ")" +

                    " )";


    // *** LOTE GADO TABLE *** //

    public static class Lote_gadoEntry implements BaseColumns {
        public static final String TABLE_NAME = "lote_gado";
        public static final String COLUMN_id_lote_gado = "id_lote_gado";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_nombre = "nombre";
        public static final String COLUMN_descripcao = "descripcao";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_LOTE_GADO =
            "CREATE TABLE "  +" IF NOT EXISTS " + Lote_gadoEntry.TABLE_NAME + " (" +
                    Lote_gadoEntry.COLUMN_id_lote_gado + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    Lote_gadoEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    Lote_gadoEntry.COLUMN_nombre + TEXT_TYPE +COMMA_SEP +
                    Lote_gadoEntry.COLUMN_descripcao + TEXT_TYPE + COMMA_SEP +
                    Lote_gadoEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + Lote_gadoEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

                    " )";


    // *** MAPEAR (PONTO INTERESSE) TABLE *** //


    public static class MapearEntry implements BaseColumns {
        public static final String TABLE_NAME = "mapear";
        public static final String COLUMN_id_mapear = "id_mapear";
        public static final String COLUMN_usuario = "usuario";
        public static final String COLUMN_pim = "pim";
        public static final String COLUMN_imei = "imei";
        public static final String COLUMN_latitude = "latitude";
        public static final String COLUMN_longitude = "longitude";
        public static final String COLUMN_versao= "versao";
        public static final String COLUMN_endereco = "endereco";
        public static final String COLUMN_tipo = "tipo";
        public static final String COLUMN_obs = "obs";
        public static final String COLUMN_data_cadastro = "data_cadastro";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_MAPEAR =
            "CREATE TABLE "  +" IF NOT EXISTS " + MapearEntry.TABLE_NAME + " (" +
                    MapearEntry.COLUMN_id_mapear + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    MapearEntry.COLUMN_usuario + INTEGER_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_pim + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_imei + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_latitude + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_longitude + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_versao + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_endereco + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_tipo + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_obs + TEXT_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_data_cadastro + DATE_TYPE + COMMA_SEP +
                    MapearEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + MapearEntry.COLUMN_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +


                    ")";


    // *** MAQUINARIA TABLE *** //

    public static class MaquinariaEntry implements BaseColumns {
        public static final String TABLE_NAME = "maquinaria";
        public static final String COLUMN_Id_maquinaria = "Id_maquinaria";
        public static final String COLUMN_Id_usuario = "Id_usuario";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Registro = "Registro";
        public static final String COLUMN_Fecha_Adquisicion = "Fecha_Adquisicion";
        public static final String COLUMN_Precio = "Precio";
        public static final String COLUMN_Tipo = "Tipo";
        public static final String COLUMN_Descripcion = "Descripcion";
        public static final String COLUMN_Modelo = "Modelo";
        public static final String COLUMN_costo_mantenimiento = "costo_mantenimiento";
        public static final String COLUMN_vida_util_horas = "vida_util_horas";
        public static final String COLUMN_vida_util_ano = "vida_util_ano";
        public static final String COLUMN_potencia_maquinaria = "potencia_maquinaria";
        public static final String COLUMN_tipo_adquisicion = "tipo_adquisicion";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_MAQUINARIA =
            "CREATE TABLE "  +" IF NOT EXISTS " + MaquinariaEntry.TABLE_NAME + " (" +
                    MaquinariaEntry.COLUMN_Id_maquinaria + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Id_usuario + INTEGER_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Registro + TEXT_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Fecha_Adquisicion + DATE_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Precio + INTEGER_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Tipo + TEXT_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Descripcion + TEXT_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_Modelo + TEXT_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_costo_mantenimiento + INTEGER_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_vida_util_horas + INTEGER_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_vida_util_ano + INTEGER_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_potencia_maquinaria + INTEGER_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_tipo_adquisicion + TEXT_TYPE + COMMA_SEP +
                    MaquinariaEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + MaquinariaEntry.COLUMN_Id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

            " )";


    // *** MENU TABLE *** //

    public static class MenuEntry implements BaseColumns {
        public static final String TABLE_NAME = "menu";
        public static final String COLUMN_id_menu = "id_menu";
        public static final String COLUMN_nombre = "nombre";
        public static final String COLUMN_imagen = "imagen";
    }

    public static final String CREATE_MENU =
            "CREATE TABLE "  +" IF NOT EXISTS " + MenuEntry.TABLE_NAME + " (" +
                    MenuEntry.COLUMN_id_menu + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    MenuEntry.COLUMN_nombre + TEXT_TYPE + COMMA_SEP +
                    MenuEntry.COLUMN_imagen + TEXT_TYPE +

            ")";


    // *** NEGOCIACOES TABLE *** //

    public static class NegociacoesEntry implements BaseColumns {
        public static final String TABLE_NAME = "negociacoes";
        public static final String COLUMN_id_negociacoes = "id_negociacoes";
        public static final String COLUMN_usuario = "usuario";
        public static final String COLUMN_pim = "pim";
        public static final String COLUMN_imei = "imei";
        public static final String COLUMN_latitude = "latitude";
        public static final String COLUMN_longitude = "longitude";
        public static final String COLUMN_versao = "versao";
        public static final String COLUMN_cpf = "cpf";
        public static final String COLUMN_nome = "nome";
        public static final String COLUMN_tipo_local = "tipo_local";
        public static final String COLUMN_local = "local";
        public static final String COLUMN_produto = "produto";
        public static final String COLUMN_taxa = "taxa";
        public static final String COLUMN_valor_negociado = "valor_negociado";
        public static final String COLUMN_data_pagamento = "data_pagamento";
        public static final String COLUMN_data_cadastro = "data_cadastro";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_NEGOCIACOES =
            "CREATE TABLE "  +" IF NOT EXISTS " + NegociacoesEntry.TABLE_NAME + " (" +
                    NegociacoesEntry.COLUMN_id_negociacoes + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    NegociacoesEntry.COLUMN_usuario + INTEGER_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_pim  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_imei  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_latitude  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_longitude  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_versao  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_cpf  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_nome  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_tipo_local  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_local  +TEXT_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_produto  +INTEGER_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_taxa  +INTEGER_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_valor_negociado  +NUMERIC_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_data_pagamento  +DATE_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_data_cadastro  +DATE_TYPE + COMMA_SEP +
                    NegociacoesEntry.COLUMN_estado  +TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + NegociacoesEntry.COLUMN_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

                    " )";

    // *** PARTO TABLE *** //

    public static class PartoEntry implements BaseColumns {
        public static final String TABLE_NAME = "parto";
        public static final String COLUMN_id_parto = "id_parto";
        public static final String COLUMN_fecha = "fecha";
        public static final String COLUMN_id_animal = "id_animal";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_PARTO =
            "CREATE TABLE "  +" IF NOT EXISTS " + PartoEntry.TABLE_NAME + " (" +
                    PartoEntry.COLUMN_id_parto + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    PartoEntry.COLUMN_fecha + DATE_TYPE + COMMA_SEP +
                    PartoEntry.COLUMN_id_animal + INTEGER_TYPE + COMMA_SEP +
                    PartoEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + PartoEntry.COLUMN_id_animal + ")" + " REFERENCES " + AnimalEntry.TABLE_NAME + "(" + AnimalEntry.COLUMN_id_animal + ")" +

                    " )";


    // *** PLAGA TABLE *** //

    public static class PlagaEntry implements BaseColumns {
        public static final String TABLE_NAME = "plaga";
        public static final String COLUMN_Id_plaga = "Id_plaga";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Caracteristicas = "Caracteristicas";
        public static final String COLUMN_Sintomas = "Sintomas";
        public static final String COLUMN_Tratamiento = "Tratamiento";
        public static final String COLUMN_Clase = "Clase";
        public static final String COLUMN_Descripcion = "Descripcion";
        public static final String COLUMN_Prevencion = "Prevencion";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_PLAGA =
            "CREATE TABLE "  +" IF NOT EXISTS " + PlagaEntry.TABLE_NAME + " (" +
                    PlagaEntry.COLUMN_Id_plaga + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    PlagaEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_Caracteristicas + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_Sintomas + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_Tratamiento + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_Clase + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_Descripcion + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_Prevencion + TEXT_TYPE + COMMA_SEP +
                    PlagaEntry.COLUMN_estado + TEXT_TYPE +
            " )";


    // *** PRECOS TABLE *** //

    public static class PrecosEntry implements BaseColumns {
        public static final String TABLE_NAME = "precos";
        public static final String COLUMN_id_precos = "id_precos";
        public static final String COLUMN_produto = "produto";
        public static final String COLUMN_ano = "ano";
        public static final String COLUMN_mes = "mes";
        public static final String COLUMN_dia = "dia";
        public static final String COLUMN_estado = "estado";
        public static final String COLUMN_regiao = "regiao";
        public static final String COLUMN_preco_dolar = "preco_dolar";
        public static final String COLUMN_preco_real = "preco_real";
        public static final String COLUMN_taxa = "taxa";
        public static final String COLUMN_mes_descricao = "mes_descricao";
        public static final String COLUMN_data_atualizacao = "data_atualizacao";

    }

    public static final String CREATE_PRECOS =
            "CREATE TABLE "  +" IF NOT EXISTS " + PrecosEntry.TABLE_NAME + " (" +
                    PrecosEntry.COLUMN_id_precos + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    PrecosEntry.COLUMN_produto + INTEGER_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_ano + TEXT_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_mes + TEXT_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_dia + TEXT_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_regiao + INTEGER_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_preco_dolar + NUMERIC_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_preco_real + NUMERIC_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_taxa + INTEGER_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_mes_descricao + TEXT_TYPE + COMMA_SEP +
                    PrecosEntry.COLUMN_data_atualizacao + DATE_TYPE +

                    " )";


    // *** PREMIOS TABLE *** //

    public static class PremiosEntry implements BaseColumns {
        public static final String TABLE_NAME = "premios";
        public static final String COLUMN_id_premio = "id_premio";
        public static final String COLUMN_empleado = "empleado";
        public static final String COLUMN_premio = "premio";
        public static final String COLUMN_ano = "ano";
        public static final String COLUMN_mes = "mes";
        public static final String COLUMN_mes_descricao = "mes_descricao";
        public static final String COLUMN_data_atualizacao = "data_atualizacao";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_estado = "estado";


    }

    public static final String CREATE_PREMIOS =
            "CREATE TABLE "  +" IF NOT EXISTS " + PremiosEntry.TABLE_NAME + " (" +
                    PremiosEntry.COLUMN_id_premio + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    PremiosEntry.COLUMN_empleado + INTEGER_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_premio + NUMERIC_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_ano + INTEGER_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_mes + INTEGER_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_mes_descricao + TEXT_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_data_atualizacao + DATE_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    PremiosEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + PremiosEntry.COLUMN_empleado + ")" + " REFERENCES " + EmpleadoEntry.TABLE_NAME + "(" + EmpleadoEntry.COLUMN_Id_empleado + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + PremiosEntry.COLUMN_id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +


                    " )";


    // *** PRODUCTO TABLE *** //

    public static class ProductoEntry implements BaseColumns {
        public static final String TABLE_NAME = "producto";
        public static final String COLUMN_Id_producto = "Id_producto";
        public static final String COLUMN_Id_tipo_producto = "Id_tipo_producto";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Fecha_registro = "Fecha_registro";
        public static final String COLUMN_Fecha_expiracion = "Fecha_expiracion";
        public static final String COLUMN_Funcion = "Funcion";
        public static final String COLUMN_Descripcion = "Descripcion";
        public static final String COLUMN_Composicion = "Composicion";
        public static final String COLUMN_Objeto = "Objeto";
        public static final String COLUMN_Imagen = "Imagen";
        public static final String COLUMN_lote = "lote";
        public static final String COLUMN_custo = "custo";
        public static final String COLUMN_id_usuario = "id_usuario";
        public static final String COLUMN_kilos = "kilos";
        public static final String COLUMN_estado = "estado";

    }

    public static final String CREATE_PRODUCTO =
            "CREATE TABLE "  +" IF NOT EXISTS " + ProductoEntry.TABLE_NAME + " (" +
                    ProductoEntry.COLUMN_Id_producto + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    ProductoEntry.COLUMN_Id_tipo_producto + INTEGER_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Fecha_registro + DATE_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Fecha_expiracion + DATE_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Funcion + TEXT_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Descripcion + TEXT_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Composicion + TEXT_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Objeto + TEXT_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_Imagen + TEXT_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_lote + INTEGER_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_custo + INTEGER_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_kilos + INTEGER_TYPE + COMMA_SEP +
                    ProductoEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + ProductoEntry.COLUMN_Id_tipo_producto + ")" + " REFERENCES " + TipoProductoEntry.TABLE_NAME + "(" + TipoProductoEntry.COLUMN_Id_tipo_producto + ")" +


                    " )";


    // *** SECTOR TABLE *** //


    public static class SectorEntry implements BaseColumns {
        public static final String TABLE_NAME = "sector";
        public static final String COLUMN_Id_sector = "Id_sector";
        public static final String COLUMN_Id_usuario = "Id_usuario";
        public static final String COLUMN_Id_cultivo = "Id_cultivo";
        public static final String COLUMN_Status = "Status";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Hectareas = "Hectareas";
        public static final String COLUMN_estado = "estado";

    }

    public static final String CREATE_SECTOR =
            "CREATE TABLE "  +" IF NOT EXISTS " + SectorEntry.TABLE_NAME + " (" +
                    SectorEntry.COLUMN_Id_sector + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    SectorEntry.COLUMN_Id_usuario + INTEGER_TYPE + COMMA_SEP +
                    SectorEntry.COLUMN_Id_cultivo + INTEGER_TYPE + COMMA_SEP +
                    SectorEntry.COLUMN_Status + TEXT_TYPE + COMMA_SEP +
                    SectorEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    SectorEntry.COLUMN_Hectareas + INTEGER_TYPE + COMMA_SEP +
                    SectorEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + SectorEntry.COLUMN_Id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

                    " )";


    // *** TAREA TABLE *** //

    public static class TareaEntry implements BaseColumns {
        public static final String TABLE_NAME = "tarea";
        public static final String COLUMN_Id_tarea = "Id_tarea";
        public static final String COLUMN_Id_usuario = "Id_usuario";
        public static final String COLUMN_Id_producto = "Id_producto";
        public static final String COLUMN_Id_empleado = "Id_empleado";
        public static final String COLUMN_Id_tipo_producto = "Id_tipo_producto";
        public static final String COLUMN_Id_maquinaria = "Id_maquinaria";
        public static final String COLUMN_Id_tipo_tarea = "Id_tipo_tarea";
        public static final String COLUMN_Id_sector = "Id_sector";
        public static final String COLUMN_Nombre = "Nombre";
        public static final String COLUMN_Descripcion = "Descripcion";
        public static final String COLUMN_Fecha_trabajo = "Fecha_trabajo";
        public static final String COLUMN_horas_trabajadas = "horas_trabajadas";
        public static final String COLUMN_hectareas_trabajadas = "hectareas_trabajadas";
        public static final String COLUMN_cantidad_producto = "cantidad_producto";
        public static final String COLUMN_estado = "estado";

    }

    public static final String CREATE_TAREA =
            "CREATE TABLE "  +" IF NOT EXISTS " + TareaEntry.TABLE_NAME + " (" +
                    TareaEntry.COLUMN_Id_tarea + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    TareaEntry.COLUMN_Id_usuario + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Id_producto + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Id_empleado + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Id_tipo_producto + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Id_maquinaria + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Id_tipo_tarea + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Id_sector + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Nombre + TEXT_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Descripcion + TEXT_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_Fecha_trabajo + DATE_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_horas_trabajadas + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_hectareas_trabajadas + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_cantidad_producto + INTEGER_TYPE + COMMA_SEP +
                    TareaEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_producto + ")" + " REFERENCES " + ProductoEntry.TABLE_NAME + "(" + ProductoEntry.COLUMN_Id_producto + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_empleado + ")" + " REFERENCES " + EmpleadoEntry.TABLE_NAME + "(" + EmpleadoEntry.COLUMN_Id_empleado + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_tipo_producto + ")" + " REFERENCES " + TipoProductoEntry.TABLE_NAME + "(" + TipoProductoEntry.COLUMN_Id_tipo_producto + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_maquinaria + ")" + " REFERENCES " + MaquinariaEntry.TABLE_NAME + "(" + MaquinariaEntry.COLUMN_Id_maquinaria + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_tipo_tarea + ")" + " REFERENCES " + TipoTareaEntry.TABLE_NAME + "(" + TipoTareaEntry.COLUMN_Id_tipo_tarea + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + TareaEntry.COLUMN_Id_sector + ")" + " REFERENCES " + SectorEntry.TABLE_NAME + "(" + SectorEntry.COLUMN_Id_sector + ")" +


                    " )";


    // *** TAXAS TABLE *** //

    public static class TaxasEntry implements BaseColumns {
        public static final String TABLE_NAME = "taxas";
        public static final String COLUMN_id_taxa = "id_taxa";
        public static final String COLUMN_ano = "ano";
        public static final String COLUMN_mes = "mes";
        public static final String COLUMN_dia = "dia";
        public static final String COLUMN_mes_descricao = "mes_descricao";
        public static final String COLUMN_money = "money";
        public static final String COLUMN_data_atualizacao = "data_atualizacao";
    }

    public static final String CREATE_TAXAS =
            "CREATE TABLE "  +" IF NOT EXISTS " + TaxasEntry.TABLE_NAME + " (" +
                    TaxasEntry.COLUMN_id_taxa + INTEGER_TYPE  + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                    TaxasEntry.COLUMN_ano + INTEGER_TYPE + COMMA_SEP +
                    TaxasEntry.COLUMN_mes + INTEGER_TYPE + COMMA_SEP +
                    TaxasEntry.COLUMN_dia + INTEGER_TYPE + COMMA_SEP +
                    TaxasEntry.COLUMN_mes_descricao + TEXT_TYPE + COMMA_SEP +
                    TaxasEntry.COLUMN_money + NUMERIC_TYPE + COMMA_SEP +
                    TaxasEntry.COLUMN_data_atualizacao + DATE_TYPE +

            " )";

    // *** TIPO CONTRATO TABLE *** //

    public static class TipoContratoEntry implements BaseColumns {
        public static final String TABLE_NAME = "tipo_contrato";
        public static final String COLUMN_Id_tipo_contrato = "Id_tipo_contrato";
        public static final String COLUMN_Nombre = "Nombre";

    }

    public static final String CREATE_TIPO_CONTRATO =
            "CREATE TABLE "  +" IF NOT EXISTS " + TipoContratoEntry.TABLE_NAME + " (" +
                    TipoContratoEntry.COLUMN_Id_tipo_contrato + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    TipoContratoEntry.COLUMN_Nombre + TEXT_TYPE +

            " )";

    // *** TIPO CUSTO BALANCE TABLE *** //

    public static class TipoCustoBalanceEntry implements BaseColumns {
        public static final String TABLE_NAME = "tipo_custo_balance";
        public static final String COLUMN_Id_tipo_custo_balance = "Id_tipo_custo_balance";
        public static final String COLUMN_Nombre = "Nombre";
    }

    public static final String CREATE_TIPO_CUSTO_BALANCE =
        "CREATE TABLE "  +" IF NOT EXISTS " + TipoCustoBalanceEntry.TABLE_NAME + " (" +
                TipoCustoBalanceEntry.COLUMN_Id_tipo_custo_balance + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
                TipoCustoBalanceEntry.COLUMN_Nombre + TEXT_TYPE +

        " )";


    // *** TIPO DESPESA TABLE *** //

    public static class TipoDespesaEntry implements BaseColumns {
        public static final String TABLE_NAME = "tipo_despesa";
        public static final String COLUMN_Id_tipo_despesa = "Id_tipo_despesa";
        public static final String COLUMN_Nombre = "Nombre";
    }

    public static final String CREATE_TIPO_DESPESA =
            "CREATE TABLE "  +" IF NOT EXISTS " + TipoDespesaEntry.TABLE_NAME + " (" +
                    TipoDespesaEntry.COLUMN_Id_tipo_despesa + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    TipoDespesaEntry.COLUMN_Nombre + TEXT_TYPE +

                    " )";

    // *** TIPO DESPESA TEMPO TABLE *** //

    public static class TipoDespesaTempoEntry implements BaseColumns {
        public static final String TABLE_NAME = "tipo_despesa_tempo";
        public static final String COLUMN_Id_tipo_despesa_tempo = "Id_tipo_despesa_tempo";
        public static final String COLUMN_Nombre = "Nombre";
    }

    public static final String CREATE_TIPO_DESPESA_TEMPO =
            "CREATE TABLE "  +" IF NOT EXISTS " + TipoDespesaTempoEntry.TABLE_NAME + " (" +
                    TipoDespesaTempoEntry.COLUMN_Id_tipo_despesa_tempo + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    TipoDespesaTempoEntry.COLUMN_Nombre + TEXT_TYPE +

                    " )";


    // *** TIPO PRODUCTO TABLE *** //

    public static class TipoProductoEntry implements BaseColumns {
        public static final String TABLE_NAME = "tipo_producto";
        public static final String COLUMN_Id_tipo_producto = "Id_tipo_producto";
        public static final String COLUMN_Nombre = "Nombre";
    }

    public static final String CREATE_TIPO_PRODUCTO =
            "CREATE TABLE "  +" IF NOT EXISTS " + TipoProductoEntry.TABLE_NAME + " (" +
                    TipoProductoEntry.COLUMN_Id_tipo_producto + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    TipoProductoEntry.COLUMN_Nombre + TEXT_TYPE +

                    " )";



    // *** TIPO TAREA TABLE *** //

    public static class TipoTareaEntry implements BaseColumns {
        public static final String TABLE_NAME = "tipo_tarea";
        public static final String COLUMN_Id_tipo_tarea = "Id_tipo_tarea";
        public static final String COLUMN_Nombre = "Nombre";
    }

    public static final String CREATE_TIPO_TAREA =
            "CREATE TABLE "  +" IF NOT EXISTS " + TipoTareaEntry.TABLE_NAME + " (" +
                    TipoTareaEntry.COLUMN_Id_tipo_tarea + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    TipoTareaEntry.COLUMN_Nombre + TEXT_TYPE +

                    " )";



    // *** USUARIOS TABLE *** //

    public static class UsuariosEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";
        public static final String COLUMN_codigo = "codigo";
        public static final String COLUMN_nome = "nome";
        public static final String COLUMN_login = "login";
        public static final String COLUMN_senha = "senha";
        public static final String COLUMN_email = "email";
        public static final String COLUMN_status = "status";
        public static final String COLUMN_perfil = "perfil";
    }

    public static final String CREATE_USUARIOS =
            "CREATE TABLE "  + " IF NOT EXISTS " + UsuariosEntry.TABLE_NAME + " (" +
                    UsuariosEntry.COLUMN_codigo + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    UsuariosEntry.COLUMN_nome + TEXT_TYPE + COMMA_SEP +
                    UsuariosEntry.COLUMN_login + TEXT_TYPE + COMMA_SEP +
                    UsuariosEntry.COLUMN_senha + TEXT_TYPE + COMMA_SEP +
                    UsuariosEntry.COLUMN_email + TEXT_TYPE + COMMA_SEP +
                    UsuariosEntry.COLUMN_status + TEXT_TYPE + COMMA_SEP +
                    UsuariosEntry.COLUMN_perfil + TEXT_TYPE +

                    " )";

    // *** VENTA GADO TABLE *** //

    public static class VentaGadoEntry implements BaseColumns {
        public static final String TABLE_NAME = "venta_gado";
        public static final String COLUMN_id_venta_gado = "id_venta_gado";
        public static final String COLUMN_fecha_venta = "fecha_venta";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_VENTA_GADO =
            "CREATE TABLE "  +" IF NOT EXISTS " + VentaGadoEntry.TABLE_NAME + " (" +
                    VentaGadoEntry.COLUMN_id_venta_gado + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    VentaGadoEntry.COLUMN_fecha_venta + TEXT_TYPE + COMMA_SEP +
                    VentaGadoEntry.COLUMN_estado + TEXT_TYPE +
                    " )";



    // *** VENTA GADO DETALLE TABLE *** //

    public static class VentaGadoDetalleEntry implements BaseColumns {
        public static final String TABLE_NAME = "venta_gado_detalle";
        public static final String COLUMN_id_venta_gado_detalle = "id_venta_gado_detalle";
        public static final String COLUMN_id_venta_gado = "id_venta_gado";
        public static final String COLUMN_id_animal = "id_animal";
        public static final String COLUMN_precio = "precio";
        public static final String COLUMN_nome_gado = "nome_gado";
        public static final String COLUMN_id_usuario = "id_usuario";
    }

    public static final String CREATE_VENTA_GADO_DETALLE =
            "CREATE TABLE "  +" IF NOT EXISTS " + VentaGadoDetalleEntry.TABLE_NAME + " (" +
                    VentaGadoDetalleEntry.COLUMN_id_venta_gado_detalle + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    VentaGadoDetalleEntry.COLUMN_id_venta_gado + INTEGER_TYPE + COMMA_SEP +
                    VentaGadoDetalleEntry.COLUMN_id_animal + INTEGER_TYPE + COMMA_SEP +
                    VentaGadoDetalleEntry.COLUMN_precio + INTEGER_TYPE + COMMA_SEP +
                    VentaGadoDetalleEntry.COLUMN_nome_gado + TEXT_TYPE + COMMA_SEP +
                    VentaGadoDetalleEntry.COLUMN_id_usuario + INTEGER_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + VentaGadoDetalleEntry.COLUMN_id_venta_gado + ")" + " REFERENCES " + VentaGadoEntry.TABLE_NAME + "(" + VentaGadoEntry.COLUMN_id_venta_gado + ")" + COMMA_SEP +
                    "FOREIGN KEY"+ "(" + VentaGadoDetalleEntry.COLUMN_id_animal + ")" + " REFERENCES " + AnimalEntry.TABLE_NAME + "(" + AnimalEntry.COLUMN_id_animal + ")" +

                    " )";

    // *** VISITAS TABLE *** //

    public static class VisitasEntry implements BaseColumns {
        public static final String TABLE_NAME = "visitas";
        public static final String COLUMN_id_visitas = "id_visitas";
        public static final String COLUMN_usuario = "usuario";
        public static final String COLUMN_latitude = "latitude";
        public static final String COLUMN_longitude = "longitude";
        public static final String COLUMN_pim = "pim";
        public static final String COLUMN_imei = "imei";
        public static final String COLUMN_versao = "versao";
        public static final String COLUMN_cliente = "cliente";
        public static final String COLUMN_motivo = "motivo";
        public static final String COLUMN_data_agenda = "data_agenda";
        public static final String COLUMN_data_visita = "data_visita";
        public static final String COLUMN_resultado = "resultado";
        public static final String COLUMN_deslocamento = "deslocamento";
        public static final String COLUMN_situacao = "situacao";
        public static final String COLUMN_obs = "obs";
        public static final String COLUMN_cadastrante = "cadastrante";
        public static final String COLUMN_estado = "estado";
    }

    public static final String CREATE_VISITAS =
            "CREATE TABLE "  +" IF NOT EXISTS " + VisitasEntry.TABLE_NAME + " (" +
                    VisitasEntry.COLUMN_id_visitas + INTEGER_TYPE + " PRIMARY KEY " + COMMA_SEP +
                    VisitasEntry.COLUMN_usuario + INTEGER_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_latitude + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_longitude + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_pim + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_imei + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_versao + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_cliente + INTEGER_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_motivo + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_data_agenda + DATE_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_data_visita + DATE_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_resultado + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_deslocamento + INTEGER_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_situacao + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_obs + TEXT_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_cadastrante + INTEGER_TYPE + COMMA_SEP +
                    VisitasEntry.COLUMN_estado + TEXT_TYPE + COMMA_SEP +

                    "FOREIGN KEY"+ "(" + VisitasEntry.COLUMN_usuario + ")" + " REFERENCES " + UsuariosEntry.TABLE_NAME + "(" + UsuariosEntry.COLUMN_codigo + ")" +

                    " )";


    public static final String INSERT_MENU =
            "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Setores', 'field' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Safras', 'field' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Colheitas', 'sprout_cosecha' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Pragas', 'plague' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Cotacoes', 'cotacoes' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Clientes', 'cliente' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Maquinarias', 'tractor' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Empregados', 'farmer' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'AlertaPragas', 'no-mosquito' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Visitas', 'visita' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Negociacoes', 'money-bag' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Taxas', 'tax' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Preco', 'price' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Produtos', 'productos' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Premios', 'premios' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Despesas', 'despesa' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Mensagems', 'message' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'PontoInteresse', 'map-location' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Tarefas', 'trolley' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Balance', 'balance' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'AlterarSenha', 'password' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Gado', 'gado' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'VentaGado', 'gado' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Solo', 'sprout' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'Consulta', 'map-location' ) " +
                    "INSERT INTO " + MenuEntry.TABLE_NAME + "( nombre, imagen ) VALUES( 'AtivarUsuarios', 'cliente' ) ";



}