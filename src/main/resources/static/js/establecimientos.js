const API_ESTABLECIMIENTOS = "/api/establecimientos";
<<<<<<< HEAD
const form = document.getElementById("establecimiento-form");
const tbody = document.getElementById("establecimientos-tbody");

let editando = false;
let idEditando = null;

// Cargar al iniciar
window.addEventListener("DOMContentLoaded", cargarEstablecimientos);

// Enviar formulario
form.addEventListener("submit", async (e) => {
=======
const API_ROL = "/auth/rol";

const form = document.getElementById("establecimiento-form");
const tbody = document.getElementById("establecimientos-tbody");
const thAcciones = document.getElementById("th-acciones");
const formCard = document.getElementById("form-card");
const bienvenida = document.getElementById("bienvenida");

let editando = false;
let idEditando = null;
let esAdmin = false;

// Inicialización
window.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarEstablecimientos();
});

// Verificar rol del usuario
async function verificarRol() {
  try {
    const res = await fetch(API_ROL);
    if (!res.ok) throw new Error("No autenticado");

    const rol = await res.text();
    esAdmin = rol === "ADMIN";

    if (!esAdmin) {
      formCard?.classList.add("d-none");
      thAcciones?.classList.add("d-none");
    }

    // Mostrar bienvenida
    bienvenida.textContent = `¡Hola! (${rol})`;

  } catch (error) {
    console.warn("🔒 Redirigiendo a login...");
    window.location.href = "/login";
  }
}

// Enviar formulario
form?.addEventListener("submit", async (e) => {
>>>>>>> 99f4d3c (Version Funcional Spring Security)
  e.preventDefault();

  const establecimiento = {
    nombre: form.nombre.value,
    cuit: form.cuit.value,
    direccion: form.direccion.value,
    descripcion: form.descripcion.value,
  };

  try {
    const url = editando ? `${API_ESTABLECIMIENTOS}/${idEditando}` : API_ESTABLECIMIENTOS;
    const method = editando ? "PUT" : "POST";

    await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(establecimiento),
    });

    form.reset();
    editando = false;
    idEditando = null;
    await cargarEstablecimientos();
  } catch (err) {
    console.error("Error al guardar:", err);
  }
});

// Cargar tabla
async function cargarEstablecimientos() {
  tbody.innerHTML = "";
  const res = await fetch(API_ESTABLECIMIENTOS);
  const establecimientos = await res.json();

  establecimientos.forEach((e) => {
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td>${e.id}</td>
      <td>${e.nombre}</td>
      <td>${e.cuit}</td>
      <td>${e.direccion}</td>
      <td>${e.descripcion || "-"}</td>
      <td>
<<<<<<< HEAD
        <button class="btn btn-sm btn-warning" onclick="editarEstablecimiento(${e.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="eliminarEstablecimiento(${e.id})">🗑️</button>
=======
        ${esAdmin
          ? `
          <button class="btn btn-sm btn-warning" onclick="editarEstablecimiento(${e.id})">✏️</button>
          <button class="btn btn-sm btn-danger" onclick="eliminarEstablecimiento(${e.id})">🗑️</button>
        `
          : ""}
>>>>>>> 99f4d3c (Version Funcional Spring Security)
      </td>
    `;
    tbody.appendChild(fila);
  });
<<<<<<< HEAD
=======

  // Si no es admin, ocultar columna "Acciones" visualmente también en tbody
  if (!esAdmin) {
    document.querySelectorAll("#establecimientos-tbody td:last-child").forEach(td => td.classList.add("d-none"));
  }
>>>>>>> 99f4d3c (Version Funcional Spring Security)
}

// Editar
async function editarEstablecimiento(id) {
  const res = await fetch(`${API_ESTABLECIMIENTOS}/${id}`);
  const e = await res.json();

  form.nombre.value = e.nombre;
  form.cuit.value = e.cuit;
  form.direccion.value = e.direccion;
  form.descripcion.value = e.descripcion ?? "";

  editando = true;
  idEditando = id;
}

<<<<<<< HEAD
// Eliminar (baja lógica o hard según backend)
=======
// Eliminar
>>>>>>> 99f4d3c (Version Funcional Spring Security)
async function eliminarEstablecimiento(id) {
  if (confirm("¿Seguro que querés eliminar este establecimiento?")) {
    await fetch(`${API_ESTABLECIMIENTOS}/${id}`, { method: "DELETE" });
    await cargarEstablecimientos();
  }
}
