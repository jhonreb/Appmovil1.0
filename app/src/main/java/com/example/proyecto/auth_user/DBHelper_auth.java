package com.example.proyecto.auth_user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.proyecto.Metodos.Gasto;
import com.example.proyecto.Metodos.Ingreso;
import com.example.proyecto.Metodos.Perfil;

import java.util.ArrayList;
import java.util.List;

public class DBHelper_auth extends SQLiteOpenHelper {
    public static final String DBName = "contafacil.db";
    private static final int DB_VERSION = 2;

    public DBHelper_auth(@Nullable Context context) {
        super(context, DBName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper_auth", "Creating database tables");
        db.execSQL("CREATE TABLE users(userid INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE NOT NULL, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE categoria(id_categoria INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, tipo TEXT NOT NULL)");
        db.execSQL("CREATE TABLE ingresos(id_ingresos INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, fecha DATE, cantidad_ingresos DECIMAL, id_categoria INTEGER, descripcion TEXT NOT NULL, FOREIGN KEY(id_categoria) REFERENCES categoria(id_categoria), FOREIGN KEY(userid) REFERENCES users(userid))");
        db.execSQL("CREATE TABLE gastos(id_gastos INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, fecha DATE, cantidad_gasto DECIMAL, id_categoria INTEGER, descripcion TEXT NOT NULL, FOREIGN KEY(id_categoria) REFERENCES categoria(id_categoria), FOREIGN KEY(userid) REFERENCES users(userid))");
        db.execSQL("CREATE TABLE profile(profile_id INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, nombre TEXT, apellido TEXT, fecha_nacimiento DATE, direccion TEXT, telefono TEXT, FOREIGN KEY(userid) REFERENCES users(userid))");
        Log.d("DBHelper_auth", "Table profile created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBHelper_auth", "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS ingresos");
        db.execSQL("DROP TABLE IF EXISTS gastos");
        db.execSQL("DROP TABLE IF EXISTS profile");
        onCreate(db);
    }

    public boolean inserData(String email, String password){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password",password);
        long result = db.insert("users", null,contentValues);
        return result != -1; // Simplified return statement
    }

    public boolean verificaremail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Integer verificarUsuario(String email, String pwd){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT userid FROM users WHERE email = ? AND password = ?", new String[]{email, pwd});
        Integer userId = null;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }

    public boolean insertarGasto(int id, String fecha, double costo, int id_categoria, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userid", id);
        values.put("fecha", fecha);
        values.put("cantidad_gasto", costo);
        values.put("id_categoria", id_categoria);
        values.put("descripcion", descripcion);
        long result = db.insert("gastos", null, values);
        return result != -1;
    }

    public int obtenerIdCategoria(String nombreCategoria, String tipoCategoria) {
        SQLiteDatabase db = this.getReadableDatabase();
        int idCategoria = -1;

        String query = "SELECT id_categoria FROM categoria WHERE nombre = ? AND tipo = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombreCategoria, tipoCategoria});

        if (cursor.moveToFirst()) {
            idCategoria = cursor.getInt(0);
        }

        cursor.close();
        return idCategoria;
    }

    public List<Gasto> obtenerGastos(int userId) {
        List<Gasto> listaGastos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gastos WHERE userid = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id_gastos"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                double cantidadGasto = cursor.getDouble(cursor.getColumnIndex("cantidad_gasto"));
                int idCategoria = cursor.getInt(cursor.getColumnIndex("id_categoria"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));

                Gasto gasto = new Gasto(id, userId, fecha, cantidadGasto, idCategoria, descripcion);
                listaGastos.add(gasto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaGastos;
    }
    public Gasto obtenerGastoPorId(int gastoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Gasto gasto = null;

        Cursor cursor = db.rawQuery("SELECT * FROM gastos WHERE id_gastos = ?", new String[]{String.valueOf(gastoId)});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex("userid"));
            String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
            double cantidadGasto = cursor.getDouble(cursor.getColumnIndex("cantidad_gasto"));
            int idCategoria = cursor.getInt(cursor.getColumnIndex("id_categoria"));
            String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));

            gasto = new Gasto(gastoId, userId, fecha, cantidadGasto, idCategoria, descripcion);
        }

        cursor.close();
        return gasto;
    }

    public boolean eliminarGasto(int gastoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("gastos", "id_gastos = ?", new String[]{String.valueOf(gastoId)});
        return result > 0; // Devuelve true si se eliminó al menos una fila
    }

    public boolean actualizarGasto(int id, String nuevaFecha, String nuevaDescripcion, double nuevaCantidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", nuevaFecha);
        values.put("descripcion", nuevaDescripcion);
        values.put("cantidad_gasto", nuevaCantidad);

        int filasAfectadas = db.update("gastos", values, "id_gastos = ?", new String[]{String.valueOf(id)});

        return filasAfectadas > 0; // Devuelve true si se actualizó al menos una fila
    }

    public double obtenerTotalGastos(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalGastos = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(cantidad_gasto) as total FROM gastos WHERE userid = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            totalGastos = cursor.getDouble(cursor.getColumnIndex("total"));
        }

        cursor.close();
        return totalGastos;
    }


    public boolean insertarIngreso(int id, String fecha, double ingreso, int id_categoria, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userid", id);
        values.put("fecha", fecha);
        values.put("cantidad_ingresos", ingreso);
        values.put("id_categoria", id_categoria);
        values.put("descripcion", descripcion);
        long result = db.insert("ingresos", null, values);
        return result != -1; // Simplified return statement
    }
    public List<Ingreso> obtenerIngreso(int userId) {
        List<Ingreso> listaIngreso = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ingresos WHERE userid = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id_ingresos"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                double cantidadGasto = cursor.getDouble(cursor.getColumnIndex("cantidad_ingresos"));
                int idCategoria = cursor.getInt(cursor.getColumnIndex("id_categoria"));
                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));

                Ingreso ingreso = new Ingreso(id, userId, fecha, cantidadGasto, idCategoria, descripcion);
                listaIngreso.add(ingreso);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaIngreso;
    }
    public Ingreso obtenerIngresoPorId(int ingresoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Ingreso ingreso = null;

        Cursor cursor = db.rawQuery("SELECT * FROM ingresos WHERE id_ingresos = ?", new String[]{String.valueOf(ingresoId)});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex("userid"));
            String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
            double cantidadGasto = cursor.getDouble(cursor.getColumnIndex("cantidad_gasto"));
            int idCategoria = cursor.getInt(cursor.getColumnIndex("id_categoria"));
            String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));

            ingreso = new Ingreso(ingresoId, userId, fecha, cantidadGasto, idCategoria, descripcion);
        }

        cursor.close();
        return ingreso;
    }

    public boolean eliminarIngreso(int ingresoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("ingresos", "id_ingresos = ?", new String[]{String.valueOf(ingresoId)});
        return result > 0; // Devuelve true si se eliminó al menos una fila
    }

    public boolean actualizarIngreso(int id, String nuevaFecha, String nuevaDescripcion, double nuevaCantidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", nuevaFecha);
        values.put("descripcion", nuevaDescripcion);
        values.put("cantidad_gasto", nuevaCantidad);

        int filasAfectadas = db.update("gastos", values, "id_ingresos = ?", new String[]{String.valueOf(id)});

        return filasAfectadas > 0; // Devuelve true si se actualizó al menos una fila
    }

    // Insertar perfil
    public boolean insertarPerfil(int userId, String nombre, String apellido, String fechaNacimiento, String direccion, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userid", userId);
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("fecha_nacimiento", fechaNacimiento);
        values.put("direccion", direccion);
        values.put("telefono", telefono);

        long result = db.insertWithOnConflict("profile", null, values, SQLiteDatabase.CONFLICT_REPLACE);

        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    // Actualizar perfil
    public boolean actualizarPerfil(int userId, String nombre, String apellido, String fechaNacimiento, String direccion, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido", apellido);
        values.put("fecha_nacimiento", fechaNacimiento);
        values.put("direccion", direccion);
        values.put("telefono", telefono);

        int filasAfectadas = db.update("profile", values, "userid = ?", new String[]{String.valueOf(userId)});

        return filasAfectadas > 0; // Devuelve true si se actualizó al menos una fila
    }

    // Obtener perfil
    public Perfil obtenerPerfil(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Perfil perfil = null;
        Cursor cursor = db.query("profile", null, "userid = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor.moveToFirst()) {
            // Obtener datos del cursor
            int id = cursor.getInt(cursor.getColumnIndex("userid"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String apellido = cursor.getString(cursor.getColumnIndex("apellido"));
            String fechaNacimiento = cursor.getString(cursor.getColumnIndex("fecha_nacimiento"));
            String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
            String telefono = cursor.getString(cursor.getColumnIndex("telefono"));

            // Crear instancia de Perfil con los datos obtenidos
            perfil = new Perfil(id, nombre, apellido, fechaNacimiento, direccion, telefono);
        }
        cursor.close();
        return perfil;
    }
    public void printDatabaseSchema() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("DBHelper_auth", "Table: " + cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}

