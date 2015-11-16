package rodrix.goa;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.epson.eposprint.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class MainActivity extends Activity implements OnClickListener {

    private TextView textView, textView2, textView3,scr1, scr2, scr3, scr4;
    int precio[][] = new int[20][2];
    int subtotal;
    int total = 0;
    int cont_array = 0;
    int cont_enabled = 0;

    TableLayout t1;
    TableRow tr1;

    String[][] multiArray = new String[10][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        //seteo valores de tabla
        t1 = (TableLayout) findViewById(R.id.t1);
        t1.setColumnStretchable(0,true);
        t1.setColumnStretchable(1,true);
        t1.setColumnStretchable(2,true);
        t1.setColumnStretchable(3,true);

        //botones
        int[] target ={
                //productos
                R.id.bfernet,
                R.id.bquilmes,
                R.id.bstella,
                R.id.bcorona,
                R.id.bmixxtail,
                R.id.bdrlemon,
                R.id.bspeed,
                R.id.bsmirnoff,
                R.id.bskyy,
                R.id.babsolut,
                R.id.btragos,
                R.id.bchandon,
                R.id.bmumm,
                R.id.bnewage,
                R.id.bfrizze,
                R.id.btequila,
                R.id.bagua,
                R.id.bgaseosa,
                R.id.bvidrio,
                R.id.bdada,
                //opciones
                R.id.bborrar,
                R.id.bcancelar,
                R.id.bconfirmar,
                //panel numerico
                R.id.buno,
                R.id.bdos,
                R.id.btres,
                R.id.bcuatro,
                R.id.bcinco,
                R.id.bseis,
                R.id.bsiete,
                R.id.bocho,
                R.id.bnueve,
        };
        for (int i = 0; i < target.length; i++){
            Button button = (Button)findViewById(target[i]);
            button.setOnClickListener(this);
        }

        readPrecio();
        updateButtonState();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bfernet:
                setProducto("FERNET", 0);
                break;
            case R.id.bquilmes:
                setProducto("QUILMES", 1);
                break;
            case R.id.bstella:
                setProducto("STELLA", 2);
                break;
            case R.id.bcorona:
                setProducto("CORONA", 3);
                break;
            case R.id.bmixxtail:
                setProducto("MIXXTAIL", 4);
                break;
            case R.id.bdrlemon:
                setProducto("DR LEMON", 5);
                break;
            case R.id.bspeed:
                setProducto("SPEED", 6);
                break;
            case R.id.bsmirnoff:
                setProducto("SMIRNOFF", 7);
                break;
            case R.id.bskyy:
                setProducto("SKYY", 8);
                break;
            case R.id.babsolut:
                setProducto("ABSOLUT", 9);
                break;
            case R.id.btragos:
                setProducto("TRAGOS", 10);
                break;
            case R.id.bchandon:
                setProducto("CHANDON", 11);
                break;
            case R.id.bmumm:
                setProducto("MUMM", 12);
                break;
            case R.id.bnewage:
                setProducto("NEW AGE", 13);
                break;
            case R.id.bfrizze:
                setProducto("FRIZZE", 14);
                break;
            case R.id.btequila:
                setProducto("TEQUILA", 15);
                break;
            case R.id.bagua:
                setProducto("AGUA", 16);
                break;
            case R.id.bgaseosa:
                setProducto("GASEOSA", 17);
                break;
            case R.id.bvidrio:
                setProducto("VASO VIDRIO", 18);
                break;
            case R.id.bdada:
                setProducto("DADA", 19);
                break;

            case R.id.bcancelar:
                t1.removeAllViews();
                cont_array = 0;
                cont_enabled = 0;
                total = 0;
                multiArray = new String[10][5];
                updateButtonState();
                break;
            case R.id.bconfirmar:
                Date date = new Date();
                //fecha
                DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = dateformat.format(date);
                //hora
                DateFormat hourformat = new SimpleDateFormat("HH:mm:ss");
                String hora = hourformat.format(date);

                for (int i = 0; i < cont_array; i++){
                    writeCSV(multiArray[i][0], multiArray[i][1], multiArray[i][2], multiArray[i][3], multiArray[i][4], fecha, hora);
                }

                printTicket();

                t1.removeAllViews();
                cont_array = 0;
                cont_enabled = 0;
                total = 0;
                multiArray = new String[10][5];
                updateButtonState();
                break;

            case R.id.buno:
                setNumero(1);
                break;
            case R.id.bdos:
                setNumero(2);
                break;
            case R.id.btres:
                setNumero(3);
                break;
            case R.id.bcuatro:
                setNumero(4);
                break;
            case R.id.bcinco:
                setNumero(5);
                break;
            case R.id.bseis:
                setNumero(6);
                break;
            case R.id.bsiete:
                setNumero(7);
                break;
            case R.id.bocho:
                setNumero(8);
            break;
            case R.id.bnueve:
                setNumero(9);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateButtonState(){
        int[] target1 = {
                R.id.buno,
                R.id.bdos,
                R.id.btres,
                R.id.bcuatro,
                R.id.bcinco,
                R.id.bseis,
                R.id.bsiete,
                R.id.bocho,
                R.id.bnueve,
        };
        int[] target2 = {
                R.id.bborrar,
                R.id.bcancelar,
                R.id.bconfirmar,
        };

        int[] enabledtarget = null;
        int[] enabledtargetopcion = null;
        enabledtarget = target1;
        enabledtargetopcion = target2;

        //update botones numericos
        if (cont_enabled == 0){
            for (int i = 0; i < enabledtarget.length; i++){
                Button button = (Button) findViewById(enabledtarget[i]);
                button.setEnabled(false);
            }
        }else{
            for (int i = 0; i < enabledtarget.length; i++){
                Button button = (Button) findViewById(enabledtarget[i]);
                button.setEnabled(true);
            }
        }

        //update botones opcion
        if (cont_array == 0){
            for (int i = 0; i < enabledtargetopcion.length; i++){
                Button button = (Button) findViewById(enabledtargetopcion[i]);
                button.setEnabled(false);
            }
        }else{
            for (int i = 0; i < enabledtargetopcion.length; i++){
                Button button = (Button) findViewById(enabledtargetopcion[i]);
                button.setEnabled(true);
            }
        }
    }

    private void updateTable(String desc, String precio, String cantidad, String subtotal){

        tr1 = new TableRow(this);

        scr1 = new TextView(this);
        scr2 = new TextView(this);
        scr3 = new TextView(this);
        scr4 = new TextView(this);

        scr1.setText(desc);
        scr1.setTextSize(20);
        scr1.setGravity(Gravity.LEFT);

        scr2.setText("$ " + precio);
        scr2.setTextSize(20);
        scr2.setGravity(Gravity.LEFT);

        scr3.setText(cantidad);
        scr3.setTextSize(20);
        scr3.setGravity(Gravity.CENTER);

        scr4.setText("$ " + subtotal);
        scr4.setTextSize(20);
        scr4.setGravity(Gravity.CENTER);

        tr1.addView(scr1);
        tr1.addView(scr2);
        tr1.addView(scr3);
        tr1.addView(scr4);

        t1.addView(tr1);
    }

    private void writeCSV(String id, String desc, String precio, String cantidad, String subtotal, String fecha, String hora){
        try{
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "caja.csv");

            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f, true));

            String linea = id + "," + desc + "," + precio + "," + cantidad + "," + subtotal + "," + fecha + "," + hora + "\n";

            fout.write(linea);
            fout.close();
        }catch (Exception ex){
            Log.e("Ficheros", "Error al escribir CSV");
        }
    }

    private void readPrecio(){
        Scanner scanIn = null;
        int Rowc = 0;
        String InputLine = "";
        try{
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "precio.txt");
            scanIn = new Scanner(new BufferedReader(new FileReader(f)));
            while (scanIn.hasNextLine()){
                InputLine = scanIn.next();
                String[] InArray = InputLine.split(";");
                for (int x = 0; x < InArray.length; x++){
                    precio[Rowc][x] = Integer.parseInt(InArray[x]);//Double.parseDouble(InArray[x]);
                }
                Rowc++;
            }
        }catch (Exception ex){
            Log.e("Ficheros", "Error al leer archivo en SD");
        }
    }

    private void setProducto(String desc, Integer indice_precio){
        multiArray[cont_array][0] = Integer.toString(precio[indice_precio][0]);
        multiArray[cont_array][1] = desc;
        multiArray[cont_array][2] = Integer.toString(precio[indice_precio][1]);

        String aux = Integer.toString(precio[indice_precio][0]);

        textView.setText(aux);

        cont_enabled = 1;

        updateButtonState();
    }

    private void setNumero(Integer valor){
        subtotal = Integer.parseInt(multiArray[cont_array][2]) * valor;
        multiArray[cont_array][3] = String.valueOf(valor);
        multiArray[cont_array][4] = Integer.toString(subtotal);

        total = total + subtotal;

        textView.setText(Integer.toString(subtotal));
        textView2.setText(Integer.toString(cont_array));
        textView3.setText(Integer.toString(total));

        //envio valores a tabla
        updateTable(multiArray[cont_array][1], multiArray[cont_array][2], multiArray[cont_array][3], multiArray[cont_array][4]);

        cont_array++;
        cont_enabled = 0;

        updateButtonState();
    }

    private void printTicket(){

        Date date = new Date();
        //fecha
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateformat.format(date);
        //hora
        DateFormat hourformat = new SimpleDateFormat("HH:mm:ss");
        String hora = hourformat.format(date);

        int subtotal2 = 0;

        int[] status = new int[1];
        int[] battery = new int[1];
        status[0] = 0;
        battery[0] = 0;
        Builder builder = null;
        Print printer = new Print(getApplicationContext());

        try{
            builder = new Builder("TM-T88V", Builder.MODEL_ANK, getApplicationContext());
            builder.addTextFont(Builder.FONT_A);
            builder.addTextAlign(Builder.ALIGN_LEFT);
            builder.addTextLineSpace(30);
            builder.addTextLang(Builder.LANG_EN);
            builder.addTextSize(1, 1);
            builder.addTextStyle(Builder.PARAM_UNSPECIFIED, Builder.PARAM_UNSPECIFIED, Builder.PARAM_UNSPECIFIED, Builder.PARAM_UNSPECIFIED);
            builder.addTextPosition(0);


            builder.addText(" TICKET CONSUMICION \n\n");
            builder.addText(" " + fecha + " - " + hora + " \n\n");

            for (int i = 0; i < cont_array; i++){
                //writeCSV(multiArray[i][0], multiArray[i][1], multiArray[i][2], multiArray[i][3], multiArray[i][4], fecha, hora);
                builder.addText(" " + multiArray[i][1] + "  " + multiArray[i][3] + "  X  $" + multiArray[i][2] + "  =  $" + multiArray[i][4] + "\n");
                subtotal2 = subtotal2 + Integer.parseInt(multiArray[i][4]);
            }

            builder.addText("\n _____________________________ \n");

            builder.addText(" TOTAL  =  $" + subtotal2 + "\n\n");

            builder.addFeedUnit(30);

            builder.addCut(Builder.CUT_FEED);


            printer.openPrinter(Print.DEVTYPE_USB, "/dev/bus/usb/001/002", Print.FALSE, Print.PARAM_DEFAULT);
            printer.sendData(builder, 10000, status, battery);


            if ((status[0] & Print.ST_PRINT_SUCCESS) == Print.ST_PRINT_SUCCESS){
                builder.clearCommandBuffer();
            }

            printer.closePrinter();

        } catch (EposException e) {
            int errStatus = e.getErrorStatus();
            status[0] = e.getPrinterStatus();
            battery[0] = e.getBatteryStatus();
            try {
                printer.closePrinter();
            } catch (EposException e1) {
                Log.e("Ficheros", "Error al IMPRIMIR");
                e1.printStackTrace();
            }
        }
    }

}
