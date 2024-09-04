package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class CategorizacionGastos extends AppCompatActivity {

    Button btnCategorizacionRegresar;
    Spinner spinnerCategoria;
    TextView tvDescripcion;

    private Map<String, String> mapaDescripciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categorizacion_gastos);

        btnCategorizacionRegresar = findViewById(R.id.btnCategorizacionRegresar);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        tvDescripcion = findViewById(R.id.tvDescripcion);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipoCategoria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);


        inicializarMapaDescripciones();

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = parent.getItemAtPosition(position).toString();
                String descripcion = mapaDescripciones.get(categoriaSeleccionada);
                tvDescripcion.setText(descripcion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnCategorizacionRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(CategorizacionGastos.this, Menu.class);
                startActivity(intentReg);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarMapaDescripciones() {
        mapaDescripciones = new HashMap<>();
        mapaDescripciones.put("Comestibles", "Gastos en alimentos y productos básicos para el hogar.\n\n" +
                "Gestion de gastos simplificada facilita el registro detallado y la categorización de todos los gastos relacionados con comestibles, permitiendo a los usuarios establecer presupuestos personalizados según sus necesidades y preferencias. Además, la app proporciona herramientas para analizar los hábitos de compra y consumo, identificando áreas donde se pueden realizar ajustes para optimizar el uso del presupuesto familiar, promoviendo así un manejo más eficiente de las finanzas personales y la reducción de gastos innecesarios en productos alimenticios.");
        mapaDescripciones.put("Restaurantes y comida rapida", "Gastos en comidas fuera del hogar, incluyendo restaurantes y comida rápida.\n\n" +
                "Gestión de gastos para restaurantes y comida rápida permite a los usuarios registrar detalladamente sus gastos en estos establecimientos. Ayuda a categorizar los gastos para una mejor organización financiera y facilita el establecimiento de presupuestos específicos para comidas fuera del hogar. Además, puede comparar precios y ofertas entre diferentes lugares, enviar notificaciones sobre promociones vigentes, y ofrecer análisis básicos de hábitos de consumo para que los usuarios puedan optimizar su presupuesto y tomar decisiones informadas sobre sus gastos en alimentación fuera del hogar");

        mapaDescripciones.put("Bebidas", "Gastos en bebidas alcohólicas y no alcohólicas compradas fuera del hogar.\n\n" +
                "Gestión de gastos para bebidas permite a los usuarios registrar y categorizar sus gastos específicamente en bebidas como café, té, jugos, refrescos y bebidas alcohólicas. Ayuda a establecer presupuestos personalizados para este tipo de compras, facilita el seguimiento detallado de los gastos y ofrece funciones para comparar precios entre diferentes productos y establecimientos.");
        mapaDescripciones.put("Matricula", "La matrícula se refiere a los costos asociados con la inscripción en instituciones educativas, ya sea en escuelas primarias, secundarias, universidades u otros programas educativos.\n\n" +
                "Puede ayudar a los usuarios a calcular y gestionar sus gastos de matrícula ofreciendo funciones como registro y seguimiento de gastos, establecimiento de presupuestos, notificaciones de fechas límite de inscripción y consejos simples para reducir costos, como buscar becas disponibles.");


        mapaDescripciones.put("Materiales escolares", "Gastos en libros, cuadernos y otros útiles escolares.\n\n" +
                "Gestión de gastos para materiales escolares permite a los usuarios registrar detalladamente sus gastos en libros, cuadernos, lápices, mochilas y otros suministros educativos. Facilita la categorización de estos gastos para una mejor organización financiera y ayuda a establecer presupuestos específicos para compras escolares. Además, puede enviar notificaciones sobre ofertas y descuentos en tiendas locales o en línea, permitiendo a los usuarios comparar precios y optimizar sus compras.");
        mapaDescripciones.put("Cursos y talleres", "Gastos en educación complementaria, como cursos extracurriculares y talleres especializados.\n\n" +
                "Sugerencias de cursos gratuitos en línea, alertas sobre descuentos en cursos pagados y seguimiento del presupuesto destinado a la educación continua.");
        mapaDescripciones.put("Cine", "Gastos en entradas de cine y concesiones.\n\n" +
                "Notificaciones sobre estrenos de películas y promociones de boletos, sugerencias para ahorrar en snacks y combos de cine, y comparación de precios entre diferentes cines.");
        mapaDescripciones.put("Conciertos", "Costos de entradas para eventos musicales en vivo.\n\n" +
                "Gestión de gastos para conciertos ayuda a los usuarios a registrar y categorizar los gastos relacionados con la compra de entradas, transporte, estacionamiento y cualquier otro gasto asociado. Permite establecer presupuestos específicos para eventos musicales, enviar notificaciones sobre la disponibilidad de entradas y ofertas especiales, comparar precios entre diferentes eventos y lugares, y proporcionar análisis de gastos para optimizar el uso del presupuesto destinado a entretenimiento.");
        mapaDescripciones.put("Recreativos", "Gastos en actividades recreativas como parques de diversiones y museos.\n\n" +
                "Recomendaciones de actividades recreativas gratuitas o de bajo costo, alertas sobre días de entrada gratuita en museos y parques locales, y sugerencias para maximizar el entretenimiento familiar dentro del presupuesto.");
        mapaDescripciones.put("Consultas médicas", "Costos asociados con visitas a médicos y especialistas.\n\n" +
                "Recordatorios de citas médicas, información sobre seguros de salud aceptados por diferentes proveedores médicos, y comparación de tarifas de consultas entre clínicas cercanas.");
        mapaDescripciones.put("Medicamentos y farmacia", "Gastos en medicamentos recetados y productos de farmacia.\n\n" +
                "Listado de precios de medicamentos en farmacias locales, alertas sobre ofertas en productos de cuidado personal y seguimiento del presupuesto destinado a gastos de salud.");
        mapaDescripciones.put("Seguros médicos", "Primas mensuales y copagos de seguros de salud.\n\n" +
                "Comparación de planes de seguro médico disponibles en el mercado, alertas sobre cambios en políticas de cobertura y notificaciones sobre fechas de renovación de seguros.");
        mapaDescripciones.put("Transporte público", "Gastos en servicios de transporte como autobuses, metro y trenes urbanos.\n\n" +
                "Información en tiempo real sobre horarios y tarifas de transporte público, sugerencias para maximizar el uso de pases mensuales y alertas sobre eventos que afecten el servicio de transporte.");
        mapaDescripciones.put("Gasolina y mantenimiento del vehículo", "Costos de combustible y servicios de mantenimiento para vehículos.\n\n" +
                "Seguimiento del gasto en combustible por kilometraje, alertas sobre fluctuaciones en los precios de la gasolina y recordatorios de mantenimiento preventivo para optimizar la eficiencia del combustible.");
        mapaDescripciones.put("Viajes en avión/tren/bus", "Gastos en boletos para viajes de larga distancia en avión, tren o autobús.\n\n" +
                "Comparación de precios de boletos entre diferentes aerolíneas y operadores de transporte, alertas sobre ofertas de vuelos y trenes, y seguimiento del presupuesto de viaje por destino.");
        mapaDescripciones.put("Alquiler o hipoteca", "Costos mensuales por vivienda, ya sea alquiler o pagos de hipoteca.\n\n" +
                "Permite a los usuarios gestionar sus pagos mensuales, comparar precios de alquiler en diferentes áreas, recibir alertas sobre nuevas oportunidades de alquiler y recordatorios de pagos.");
        mapaDescripciones.put("Servicios públicos", "Pagos mensuales por servicios como electricidad, agua, gas y recolección de basura.\n\n" +
                "Ayuda a los usuarios a monitorizar y reducir el consumo de servicios, recibir alertas sobre aumentos de tarifas, comparar proveedores para obtener mejores precios y optimizar el uso de servicios para reducir costos.");
        mapaDescripciones.put("Mantenimiento y reparaciones", "Gastos para mantener y reparar el hogar, vehículos u otros equipos.\n\n" +
                "Proporciona recordatorios para mantenimientos programados, alertas sobre servicios de reparación asequibles, seguimiento del historial de mantenimiento y presupuesto para reparaciones importantes.");
        mapaDescripciones.put("Ropa", "Gastos en prendas de vestir.\n\n" +
                "Ofrece sugerencias para comprar ropa de calidad a precios reducidos, alertas sobre ventas y descuentos en tiendas locales y en línea, y seguimiento del presupuesto destinado a compras de ropa.");
        mapaDescripciones.put("Calzado", "Gastos en zapatos y otros tipos de calzado.\n\n" +
                "Proporciona recomendaciones para comprar calzado duradero a precios accesibles, alertas sobre ofertas en tiendas de calzado y seguimiento del presupuesto destinado a la compra de calzado.");
        mapaDescripciones.put("Accesorios de moda", "Gastos en accesorios como bolsos, joyería y bufandas.\n\n" +
                "Sugerencias para comprar accesorios versátiles a precios económicos, alertas sobre ventas y descuentos en tiendas de moda, y seguimiento del presupuesto para accesorios.");
        mapaDescripciones.put("Alojamiento", "Gastos relacionados con el hospedaje en hoteles, hostales o alquileres vacacionales.\n\n" +
                "Permite la búsqueda y comparación de precios de alojamientos, alertas sobre ofertas y promociones de alojamiento, y seguimiento del presupuesto para viajes y estancias.");
        mapaDescripciones.put("Transporte (vuelos, trenes, alquiler de coches)", "Gastos en transporte de larga distancia, incluyendo vuelos, trenes y alquiler de coches.\n\n" +
                "Comparación de precios de boletos y tarifas de alquiler de coches, alertas sobre ofertas de vuelos y trenes, seguimiento del presupuesto de transporte y opciones para maximizar el ahorro en viajes.");
        mapaDescripciones.put("Actividades turísticas y excursiones", "Costos asociados con actividades recreativas durante viajes, como tours y entradas a atracciones.\n\n" +
                "Recomendaciones de actividades turísticas económicas en destinos populares, alertas sobre descuentos en tours y atracciones turísticas, y seguimiento del presupuesto de entretenimiento durante el viaje.");
    }
}
