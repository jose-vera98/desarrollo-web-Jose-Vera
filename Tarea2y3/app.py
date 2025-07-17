from flask import Flask, render_template, request, redirect, url_for, jsonify
from flask_sqlalchemy import SQLAlchemy
from models import Actividad, db, Region, ActividadTema, ContactarPor, Comuna, Foto
from datetime import datetime
import os
from werkzeug.utils import secure_filename
from sqlalchemy import text


app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://cc5002:programacionweb@localhost:3306/tarea2'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

UPLOAD_FOLDER = os.path.join('static', 'fotos')
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

db.init_app(app)


## Rutas
## Ruta para la portada
@app.route('/')
def index():
    # Ordena por fecha de inicio descendente (las más nuevas primero)
    actividades = Actividad.query.order_by(Actividad.dia_hora_inicio.desc()).limit(5).all()
    return render_template("index.html", actividades=actividades)

## Ruta para el formulario


@app.route('/formulario', methods=['GET', 'POST'])
def mostrar_formulario():
    if request.method == 'POST':
        try:
            # --- Obtener datos base ---
            comuna_id = request.form['comuna']
            sector = request.form['sector']
            nombre = request.form['nombre']
            email = request.form['email']
            celular = request.form['celular']
            descripcion = request.form['descripcion']
            inicio = datetime.strptime(request.form['inicio'], "%Y-%m-%dT%H:%M")

            termino_raw = request.form.get('termino')
            termino = datetime.strptime(termino_raw, "%Y-%m-%dT%H:%M") if termino_raw else None

            # --- Crear Actividad principal ---
            actividad = Actividad(
                comuna_id=comuna_id,
                sector=sector,
                nombre=nombre,
                email=email,
                celular=celular,
                dia_hora_inicio=inicio,
                dia_hora_termino=termino,
                descripcion=descripcion
            )
            db.session.add(actividad)
            db.session.flush()  # Para obtener el ID sin hacer commit aún

            # --- Guardar tema ---
            tema = request.form['tema']
            glosa_otro = request.form.get('tema_otro') if tema == 'otro' else None

            db.session.add(ActividadTema(
                actividad_id=actividad.id,
                tema=tema,
                glosa_otro=glosa_otro
            ))

            # --- Guardar todos los medios de contacto (si existen) ---
            medios = []
            for key in request.form:
                if key.startswith('medio_contacto_'):
                    index = key.split('_')[-1]
                    medio = request.form.get(f'medio_contacto_{index}')
                    identificador = request.form.get(f'id_contacto_{index}')
                    if medio and identificador:
                        medios.append((medio, identificador))

            for medio, identificador in medios:
                db.session.add(ContactarPor(
                    actividad_id=actividad.id,
                    nombre=medio,
                    identificador=identificador
                ))

            fotos = request.files.getlist('foto[]')

            for foto in fotos:
                if foto and allowed_file(foto.filename):
                    filename = secure_filename(foto.filename)
                    ruta = os.path.join(app.config['UPLOAD_FOLDER'], filename)

                    # Evitar sobreescritura si ya existe un archivo con el mismo nombre
                    base, ext = os.path.splitext(filename)
                    contador = 1
                    while os.path.exists(ruta):
                        filename = f"{base}_{contador}{ext}"
                        ruta = os.path.join(app.config['UPLOAD_FOLDER'], filename)
                        contador += 1

                    foto.save(ruta)

                    # Guardar referencia en la base de datos
                    db.session.add(Foto(
                        actividad_id=actividad.id,
                        nombre_archivo=filename,
                        ruta_archivo=f"fotos/{filename}"
                    ))

            # --- Commit final ---
            db.session.commit()
            return redirect(url_for('index'))

        except Exception as e:
            db.session.rollback()
            return f"Error al guardar la actividad: {e}", 500

    # GET: renderizar formulario con regiones
    regiones = Region.query.order_by(Region.nombre).all()
    return render_template('formulario.html', regiones=regiones)




@app.route('/comunas/<int:region_id>')
def comunas_por_region(region_id):
    comunas = Comuna.query.filter_by(region_id=region_id).order_by(Comuna.nombre).all()
    return jsonify([{'id': c.id, 'nombre': c.nombre} for c in comunas])


@app.route('/actividades')
def listado_actividades():
    actividades = Actividad.query.order_by(Actividad.dia_hora_inicio.desc()).all()
    return render_template('listado.html', actividades=actividades)

@app.route('/actividad/<int:id>')
def detalle_actividad(id):
    actividad = Actividad.query.get_or_404(id)

    comentarios = db.session.execute(text("""
        SELECT nombre, texto, fecha
        FROM comentario
        WHERE actividad_id = :id
        ORDER BY fecha DESC
    """), {"id": id}).fetchall()

    return render_template('detalle.html', actividad=actividad, comentarios=comentarios)


#Tarea 3

##Ruta de la pagina de estadisticas
@app.route('/estadisticas')
def estadisticas():
    return render_template("estadisticas.html")


#Rutas de apis para alimentar los jsons para las estadisticas

#Actividades por dia
@app.route('/api/actividades_por_dia')
def actividades_por_dia():
    resultados = db.session.execute(text("""
        SELECT DATE(dia_hora_inicio) AS fecha, COUNT(*) as cantidad
        FROM actividad
        GROUP BY fecha
        ORDER BY fecha
    """)).fetchall()

    datos = [{"fecha": str(f[0]), "cantidad": f[1]} for f in resultados]
    return jsonify(datos)



#Actividades por tipo
@app.route('/api/actividades_por_tipo')
def actividades_por_tipo():
    resultados = db.session.execute(text("""
        SELECT at.tema, COUNT(*) as cantidad
        FROM actividad a
        JOIN actividad_tema at ON a.id = at.actividad_id
        GROUP BY at.tema
        ORDER BY cantidad DESC
    """)).fetchall()

    datos = [{"tipo": r[0], "cantidad": r[1]} for r in resultados]
    return jsonify(datos)



#Actividades por momento del dia
@app.route('/api/actividades_por_momento_del_dia')
def actividades_por_momento_del_dia():
    resultados = db.session.execute(text("""
        SELECT 
            DATE_FORMAT(dia_hora_inicio, '%Y-%m') AS mes,
            CASE 
                WHEN HOUR(dia_hora_inicio) < 12 THEN 'mañana'
                WHEN HOUR(dia_hora_inicio) < 18 THEN 'mediodía'
                ELSE 'tarde'
            END AS momento,
            COUNT(*) AS cantidad
        FROM actividad
        GROUP BY mes, momento
        ORDER BY mes
    """)).fetchall()

    datos = {}
    for mes, momento, cantidad in resultados:
        if mes not in datos:
            datos[mes] = {"mañana": 0, "mediodía": 0, "tarde": 0}
        datos[mes][momento] = cantidad
    datos_final = [{"mes": k, **v} for k, v in datos.items()]
    return jsonify(datos_final)


##Implementacion de comentarios:
@app.route('/api/comentario', methods=['POST'])
def agregar_comentario():
    data = request.get_json()
    nombre = data.get('nombre', '').strip()
    texto = data.get('texto', '').strip()
    actividad_id = data.get('actividad_id')

    if not nombre or not texto or len(nombre) < 3 or len(nombre) > 80 or len(texto) < 5:
        return jsonify({"ok": False, "error": "Datos inválidos"}), 400

    try:
        db.session.execute(text("""
            INSERT INTO comentario (nombre, texto, fecha, actividad_id)
            VALUES (:nombre, :texto, NOW(), :actividad_id)
        """), {
            "nombre": nombre,
            "texto": texto,
            "actividad_id": actividad_id
        })
        db.session.commit()
        return jsonify({"ok": True})
    except Exception as e:
        return jsonify({"ok": False, "error": str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)
