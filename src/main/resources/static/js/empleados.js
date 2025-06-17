const API_EMPLEADOS = "/api/empleados";
const API_ESPECIALIDADES = "/api/especialidades";
const API_ROL = "/auth/rol";

const form = document.getElementById("empleado-form");
const tbody = document.getElementById("empleados-tbody");
const especialidadesContainer = document.getElementById("especialidades-container");
const formCard = document.querySelector(".card.mb-5");

let editando = false;
let idEditando = null;
let esAdmin = false;

// Inicializar
window.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarEspecialidades();
  await cargarEmpleados();
});

// 🔐 Verificar rol del usuario
async function verificarRol() {
  try {
    const res = await fetch(API_ROL);
    if (!res.ok) throw new Error("No autenticado");
    const rol = await res.text();
    esAdmin = rol === "ADMIN";

    if (!esAdmin && formCard) {
      formCard.style.display = "none";
    }
  } catch (err) {
    console.error("No se pudo verificar el rol:", err);
    window.location.href = "/login";
  }
}

// 📤 Enviar formulario
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const pass1 = document.getElementById("password").value.trim();
  const pass2 = document.getElementById("confirmPassword").value.trim();

  if (pass1 || pass2) {
    if (pass1 !== pass2) {
      alert("Las contraseñas no coinciden.");
      return;
    }
  }

  const dni = parseInt(form.dni.value);
  const cuil = parseInt(form.cuil.value);
  if (isNaN(dni) || isNaN(cuil)) {
    alert("DNI y CUIL deben ser números válidos.");
    return;
  }

  const especialidadesSeleccionadas = Array.from(
    document.querySelectorAll("input[name='especialidades']:checked")
  ).map((input) => ({ id: parseInt(input.value) }));

  const empleado = {
    ...(editando && { id: idEditando }), // ✅ Se agrega el ID si estamos editando
    nombre: form.nombre.value,
    apellido: form.apellido.value,
    email: form.email.value,
    direccion: form.direccion.value,
    dni,
    cuil,
    matricula: form.matricula.value || null,
    estado: true,
    fechaAlta: new Date().toISOString(),
    lstEspecialidades: especialidadesSeleccionadas,
  };

  if (pass1) {
    empleado.password = pass1;
  }

  try {
    const url = editando
      ? `${API_EMPLEADOS}/${idEditando}`
      : `${API_EMPLEADOS}/con-especialidades`;
    const method = editando ? "PUT" : "POST";

    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(empleado),
    });

    if (!res.ok) {
      const text = await res.text();
      throw new Error(text || "Error en el servidor");
    }

    form.reset();
    editando = false;
    idEditando = null;
    await cargarEmpleados();
  } catch (err) {
    console.error("Error al guardar empleado:", err);
    alert("Error al guardar empleado. Detalle en consola.");
  }
});

// 📥 Cargar empleados
async function cargarEmpleados() {
  try {
    const res = await fetch(API_EMPLEADOS);
    const empleados = await res.json();
    tbody.innerHTML = "";

    empleados.forEach((e) => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${e.id}</td>
        <td>${e.nombre}</td>
        <td>${e.apellido}</td>
        <td>${e.email}</td>
        <td>${e.direccion}</td>
        <td>${e.dni}</td>
        <td>${e.cuil}</td>
        <td>${e.matricula ?? "-"}</td>
        <td>${e.lstEspecialidades?.map(es => es.nombre).join(", ") || "-"}</td>
        <td>${e.estado ? "Activo" : "Inactivo"}</td>
        <td>${e.fechaAlta ? e.fechaAlta.split("T")[0] : ""}</td>
        <td>
          ${esAdmin
            ? `
              <button class="btn btn-sm btn-warning" onclick="editarEmpleado(${e.id})">✏️</button>
              <button class="btn btn-sm btn-danger" onclick="darBajaEmpleado(${e.id})">🗑️</button>
            `
            : "🔒"}
        </td>
      `;
      tbody.appendChild(fila);
    });
  } catch (err) {
    console.error("Error al cargar empleados:", err);
  }
}

async function darBajaEmpleado(id) {
  if (!confirm("¿Estás seguro de eliminar este empleado? Esta acción no se puede deshacer.")) return;

  try {
    const res = await fetch(`${API_EMPLEADOS}/${id}`, {
      method: "DELETE",
    });

    if (!res.ok) throw new Error("Error al eliminar el empleado.");

    await cargarEmpleados();
  } catch (err) {
    console.error("Error al eliminar empleado:", err);
    alert("No se pudo eliminar el empleado. Ver consola para más detalles.");
  }
}

// ✏️ Cargar datos del empleado a editar
async function editarEmpleado(id) {
  try {
    const res = await fetch(`${API_EMPLEADOS}/${id}`);
    const e = await res.json();

    form.nombre.value = e.nombre;
    form.apellido.value = e.apellido;
    form.email.value = e.email;
    form.direccion.value = e.direccion;
    form.dni.value = e.dni;
    form.cuil.value = e.cuil;
    form.matricula.value = e.matricula ?? "";
    document.getElementById("password").value = "";
    document.getElementById("confirmPassword").value = "";

    document.querySelectorAll("input[name='especialidades']").forEach((checkbox) => {
      checkbox.checked = e.lstEspecialidades?.some(es => es.id === parseInt(checkbox.value)) || false;
    });

    editando = true;
    idEditando = id;
  } catch (err) {
    console.error("Error al cargar empleado:", err);
    alert("No se pudo cargar el empleado.");
  }
}

// 📋 Cargar especialidades
async function cargarEspecialidades() {
  try {
    const res = await fetch(API_ESPECIALIDADES);
    const especialidades = await res.json();

    especialidadesContainer.innerHTML = especialidades.map(es => `
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="esp-${es.id}" name="especialidades" value="${es.id}" />
        <label class="form-check-label" for="esp-${es.id}">${es.nombre}</label>
      </div>
    `).join("");
  } catch (err) {
    console.error("Error al cargar especialidades:", err);
  }
}
