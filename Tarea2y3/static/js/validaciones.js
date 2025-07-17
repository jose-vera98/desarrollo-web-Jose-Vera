document.addEventListener('DOMContentLoaded', function () {
    // --- Región y comuna dinámicas ---
    const regionSelect = document.getElementById('region');
    const comunaSelect = document.getElementById('comuna');

    if (regionSelect && comunaSelect) {
        regionSelect.addEventListener('change', function () {
            const regionId = this.value;
            comunaSelect.innerHTML = '<option value="">Seleccione una comuna</option>';

            if (regionId) {
                fetch(`/comunas/${regionId}`)
                    .then(response => response.json())
                    .then(comunas => {
                        comunas.forEach(comuna => {
                            const option = document.createElement('option');
                            option.value = comuna.id;
                            option.textContent = comuna.nombre;
                            comunaSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error("Error al cargar comunas:", error);
                    });
            }
        });
    }

    // --- Tema "otro" ---
    const tema = document.getElementById('tema');
    const temaOtro = document.getElementById('tema_otro');

    if (tema && temaOtro) {
        tema.addEventListener('change', function () {
            temaOtro.innerHTML = '';
            if (this.value === 'otro') {
                const label = document.createElement('label');
                label.textContent = 'Describa el tema:';
                const input = document.createElement('input');
                input.type = 'text';
                input.name = 'tema_otro';
                input.minLength = 3;
                input.maxLength = 15;
                temaOtro.appendChild(label);
                temaOtro.appendChild(document.createElement('br'));
                temaOtro.appendChild(input);
            }
        });
    }

    // --- Agregar fotos dinámicamente (máx 5) ---
    const botonAgregarFoto = document.getElementById('agregar-foto');
    const contenedorFotos = document.getElementById('fotos');

    if (botonAgregarFoto && contenedorFotos) {
        botonAgregarFoto.addEventListener('click', function () {
            const actuales = contenedorFotos.querySelectorAll('input[type="file"]');
            if (actuales.length >= 5) return;

            const nuevoInput = document.createElement('input');
            nuevoInput.type = 'file';
            nuevoInput.name = 'foto[]';
            nuevoInput.accept = 'image/*';
            contenedorFotos.appendChild(document.createElement('br'));
            contenedorFotos.appendChild(nuevoInput);

            if (contenedorFotos.querySelectorAll('input[type="file"]').length >= 5) {
                botonAgregarFoto.style.display = 'none';
            }
        });
    }

    // --- Contactos dinámicos (máximo 5) ---
    const botonAgregarContacto = document.getElementById('agregar-contacto');
    const bloqueContactos = document.getElementById('bloque-contactos');

    const opcionesContacto = [
        'whatsapp', 'telegram', 'X', 'instagram', 'tiktok', 'otra'
    ];

    function crearBloqueContacto(index, usados = []) {
        const div = document.createElement('div');
        div.className = 'bloque-contacto';
        div.style.marginBottom = '8px';

        // Select
        const select = document.createElement('select');
        select.name = `medio_contacto_${index}`;

        const defaultOpt = document.createElement('option');
        defaultOpt.value = '';
        defaultOpt.textContent = 'Seleccione un medio';
        select.appendChild(defaultOpt);

        opcionesContacto.forEach(op => {
            if (!usados.includes(op)) {
                const opt = document.createElement('option');
                opt.value = op;
                opt.textContent = op;
                select.appendChild(opt);
            }
        });

        // Input
        const input = document.createElement('input');
        input.type = 'text';
        input.name = `id_contacto_${index}`;
        input.placeholder = 'ID o URL de contacto';
        input.minLength = 4;
        input.maxLength = 50;
        input.style.marginLeft = '10px';

        div.appendChild(select);
        div.appendChild(input);
        return div;
    }

    if (botonAgregarContacto && bloqueContactos) {
        botonAgregarContacto.addEventListener('click', function () {
            const existentes = bloqueContactos.querySelectorAll('.bloque-contacto');
            if (existentes.length >= 5) return;

            const usados = Array.from(bloqueContactos.querySelectorAll('select'))
                .map(s => s.value)
                .filter(Boolean);

            const nuevoBloque = crearBloqueContacto(existentes.length + 1, usados);
            bloqueContactos.appendChild(nuevoBloque);

            if (bloqueContactos.querySelectorAll('.bloque-contacto').length >= 5) {
                botonAgregarContacto.style.display = 'none';
            }
        });

        // Agrega el primer bloque por defecto
        botonAgregarContacto.click();
    }

    // --- Validación del formulario y confirmación ---
    const form = document.getElementById('form-actividad');
    const confirmacion = document.getElementById('confirmacion');
    const mensajeFinal = document.getElementById('mensaje-final');

    if (form && confirmacion && mensajeFinal) {
        form.addEventListener('submit', function (e) {
        e.preventDefault(); // Evita envío automático

        let errores = [];

        const region = regionSelect?.value || '';
        const comuna = comunaSelect?.value || '';
        const nombre = document.getElementById('nombre').value.trim();
        const email = document.getElementById('email').value.trim();
        const inicio = document.getElementById('inicio').value;
        const termino = document.getElementById('termino').value;
        const temaValue = tema?.value || '';
        const fotos = document.querySelectorAll('input[type="file"]');

        // Validaciones básicas
        if (!region) errores.push("Debe seleccionar una región.");
        if (!comuna) errores.push("Debe seleccionar una comuna.");
        if (!nombre || nombre.length > 200) errores.push("Nombre es obligatorio y máximo 200 caracteres.");
        if (!email || email.length > 100 || !/^\S+@\S+\.\S+$/.test(email)) errores.push("Email no válido.");
        if (!inicio) errores.push("Debe ingresar fecha y hora de inicio.");
        if (termino && (new Date(termino) <= new Date(inicio))) {
            errores.push("La fecha de término debe ser mayor a la de inicio.");
        }
        if (!temaValue) errores.push("Debe seleccionar un tema.");

        if (temaValue === 'otro') {
            const otroInput = document.querySelector('input[name="tema_otro"]');
            if (!otroInput || otroInput.value.length < 3 || otroInput.value.length > 15) {
                errores.push("Tema 'otro' debe tener entre 3 y 15 caracteres.");
            }
        }

        if (fotos.length < 1 || fotos.length > 5) {
            errores.push("Debe subir entre 1 y 5 fotos.");
        }

        // Validación de medios de contacto (opcional, pero si están deben estar bien)
        const bloques = document.querySelectorAll('.bloque-contacto');

        if (bloques.length > 0) {
            bloques.forEach((bloque, index) => {
                const medio = bloque.querySelector('select')?.value;
                const identificador = bloque.querySelector('input')?.value.trim();

                if (medio) {
                    if (identificador.length < 4 || identificador.length > 50) {
                        errores.push(`El identificador del contacto ${index + 1} debe tener entre 4 y 50 caracteres.`);
                    }
                }

                
            });
        }

        // Mostrar errores o continuar
        if (errores.length > 0) {
            alert("Errores:\n" + errores.join("\n"));
            return;
        }

        // Mostrar confirmación
        form.style.display = 'none';
        confirmacion.style.display = 'block';
        });


        document.getElementById('confirmar-si').addEventListener('click', function () {
            form.submit();
        });

        document.getElementById('confirmar-no').addEventListener('click', function () {
            confirmacion.style.display = 'none';
            form.style.display = 'block';
        });
    }
});
