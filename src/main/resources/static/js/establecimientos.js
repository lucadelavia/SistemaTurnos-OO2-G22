const API_ESTABLECIMIENTOS = "/api/establecimientos";
const form = document.getElementById("establecimiento-form");
const tbody = document.getElementById("establecimientos-tbody");

let editando = false;
let idEditando = null;

// Cargar al iniciar
window.addEventListener("DOMContentLoaded", cargarEstablecimientos);

// Enviar formulario
form.addEventListener("submit", async (e) => {
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
        <button class="btn btn-sm btn-warning" onclick="editarEstablecimiento(${e.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="eliminarEstablecimiento(${e.id})">🗑️</button>
      </td>
    `;
    tbody.appendChild(fila);
  });
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

// Eliminar (baja lógica o hard según backend)
async function eliminarEstablecimiento(id) {
  if (confirm("¿Seguro que querés eliminar este establecimiento?")) {
    await fetch(`${API_ESTABLECIMIENTOS}/${id}`, { method: "DELETE" });
    await cargarEstablecimientos();
  }
}
