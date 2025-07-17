document.getElementById('comentario-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const nombre = document.getElementById('nombre').value.trim();
    const texto = document.getElementById('texto').value.trim();
    const actividad_id = document.querySelector('input[name="actividad_id"]').value;
    const errorDiv = document.getElementById('comentario-error');
    const exitoDiv = document.getElementById('comentario-exito');

    errorDiv.textContent = '';
    exitoDiv.textContent = '';

    if (nombre.length < 3 || nombre.length > 80 || texto.length < 5) {
        errorDiv.textContent = "Debe ingresar un nombre valido (3-80) y un comentario de al menos 5 caracteres de largo";
        return;
    }

    try {
        const res = await fetch('/api/comentario', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombre, texto, actividad_id })
        });

        const data = await res.json();
        if (data.ok) {
            exitoDiv.textContent = "Comentario agregado con exito.";
            document.getElementById('comentario-form').reset();

            // Insertar nuevo comentario en el DOM
            const lista = document.querySelector('main ul:last-of-type'); // lista más cercana al final
            const nuevoComentario = document.createElement('li');
            const ahora = new Date().toISOString().slice(0, 16).replace('T', ' ');
            nuevoComentario.innerHTML = `<strong>${nombre}</strong> (${ahora}):<br>${texto}`;

            if (lista) {
                lista.insertBefore(nuevoComentario, lista.firstChild);
            } else {
                const nuevoListado = document.createElement('ul');
                nuevoListado.innerHTML = nuevoComentario.outerHTML;
                exitoDiv.insertAdjacentElement('afterend', nuevoListado);
            }

        } else {
            errorDiv.textContent = data.error || "Error al agregar comentario";
        }
    } catch (err) {
        errorDiv.textContent = "Error en la conexion con el servidor";
    }
});
