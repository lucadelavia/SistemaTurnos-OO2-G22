const API_SERVICIOS = "/api/servicios";

const form = document.getElementById("servicio-form");
const tbody = document.getElementById("servicios-tbody");

let editando = false;
let idEditando = null;

document.addEventListener("DOMContentLoaded", cargarServicios);

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const servicio = {
    nombreServicio: form.nombreServicio.value.trim(),
    duracion: parseInt(form.duracion.value)
  };

  const method = editando ? "PUT" : "POST";
  const url = editando ? `${API_SERVICIOS}/${idEditando}` : API_SERVICIOS;

  await fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(servicio)
  });

  form.reset();
  editando = false;
  idEditando = null;
  await cargarServicios();
});

async function cargarServicios() {
  const res = await fetch(API_SERVICIOS);
  const servicios = await res.json();
  tbody.innerHTML = "";

  servicios.forEach(s => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${s.id}</td>
      <td>${s.nombreServicio}</td>
      <td>${s.duracion} min</td>
      <td>
        <button class="btn btn-sm btn-warning me-1" onclick="editarServicio(${s.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="eliminarServicio(${s.id})">🗑️</button>
      </td>
    `;
    tbody.appendChild(row);
  });
}

async function editarServicio(id) {
  const res = await fetch(`${API_SERVICIOS}/${id}`);
  const s = await res.json();

  form.nombreServicio.value = s.nombreServicio;
  form.duracion.value = s.duracion;

  editando = true;
  idEditando = id;
}

async function eliminarServicio(id) {
  if (confirm("¿Estás seguro de que querés eliminar este servicio?")) {
    await fetch(`${API_SERVICIOS}/${id}`, {
      method: "DELETE"
    });
    await cargarServicios();
  }
}
