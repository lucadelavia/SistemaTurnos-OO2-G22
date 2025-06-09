const API_ESPECIALIDADES = "/api/especialidades";

const form = document.getElementById("especialidad-form");
const tbody = document.getElementById("especialidades-tbody");

let editando = false;
let idEditando = null;

// Cargar al iniciar
window.addEventListener("DOMContentLoaded", cargarEspecialidades);

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const especialidad = {
    nombre: form.nombre.value.trim()
  };

  try {
    const url = editando ? `${API_ESPECIALIDADES}/${idEditando}` : API_ESPECIALIDADES;
    const method = editando ? "PUT" : "POST";

    await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(especialidad),
    });

    form.reset();
    editando = false;
    idEditando = null;
    cargarEspecialidades();
  } catch (err) {
    console.error("Error al guardar:", err);
  }
});

async function cargarEspecialidades() {
  tbody.innerHTML = "";
  const res = await fetch(API_ESPECIALIDADES);
  const data = await res.json();

  data.forEach((esp) => {
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td>${esp.id}</td>
      <td>${esp.nombre}</td>
      <td>
        <button class="btn btn-sm btn-warning" onclick="editarEspecialidad(${esp.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="eliminarEspecialidad(${esp.id})">🗑️</button>
      </td>
    `;
    tbody.appendChild(fila);
  });
}

async function editarEspecialidad(id) {
  const res = await fetch(`${API_ESPECIALIDADES}/${id}`);
  const esp = await res.json();

  form.nombre.value = esp.nombre;
  editando = true;
  idEditando = id;
}

async function eliminarEspecialidad(id) {
  if (confirm("¿Eliminar esta especialidad?")) {
    await fetch(`${API_ESPECIALIDADES}/${id}`, { method: "DELETE" });
    cargarEspecialidades();
  }
}
