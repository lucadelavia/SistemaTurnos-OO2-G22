const API_SERVICIOS = "/api/servicios";

const form = document.getElementById("servicio-form");
const tbody = document.getElementById("servicios-tbody");
<<<<<<< HEAD

let editando = false;
let idEditando = null;

document.addEventListener("DOMContentLoaded", cargarServicios);

=======
const formCard = document.querySelector(".card.mb-5");
const thAcciones = document.getElementById("th-acciones");

let editando = false;
let idEditando = null;
let esAdmin = false;

// Al iniciar
window.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarServicios();
});

// Verifica si el usuario es ADMIN
async function verificarRol() {
  try {
    const res = await fetch("/auth/rol");
    const rol = await res.text();
    esAdmin = rol === "ADMIN";

    if (!esAdmin) {
      formCard.style.display = "none";
      thAcciones.style.display = "none";
    }
  } catch (err) {
    console.error("Error al verificar el rol", err);
    location.href = "/login";
  }
}

// Enviar formulario
>>>>>>> 99f4d3c (Version Funcional Spring Security)
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const servicio = {
    nombreServicio: form.nombreServicio.value.trim(),
<<<<<<< HEAD
    duracion: parseInt(form.duracion.value)
=======
    duracion: parseInt(form.duracion.value),
    estado: true
>>>>>>> 99f4d3c (Version Funcional Spring Security)
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

<<<<<<< HEAD
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

=======
// Cargar servicios activos
async function cargarServicios() {
  tbody.innerHTML = "";
  const res = await fetch(API_SERVICIOS);
  const servicios = await res.json();

  servicios
    .filter(s => s.estado)
    .forEach(s => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${s.id}</td>
        <td>${s.nombreServicio}</td>
        <td>${s.duracion} min</td>
        <td>${s.estado ? "Activo" : "Inactivo"}</td>
        <td ${!esAdmin ? 'style="display:none;"' : ''}>
          <button class="btn btn-sm btn-warning me-1" onclick="editarServicio(${s.id})">✏️</button>
          <button class="btn btn-sm btn-danger" onclick="darBajaServicio(${s.id})">🗑️</button>
        </td>
      `;
      tbody.appendChild(row);
    });
}

// Editar
>>>>>>> 99f4d3c (Version Funcional Spring Security)
async function editarServicio(id) {
  const res = await fetch(`${API_SERVICIOS}/${id}`);
  const s = await res.json();

  form.nombreServicio.value = s.nombreServicio;
  form.duracion.value = s.duracion;

  editando = true;
  idEditando = id;
}

<<<<<<< HEAD
async function eliminarServicio(id) {
  if (confirm("¿Estás seguro de que querés eliminar este servicio?")) {
    await fetch(`${API_SERVICIOS}/${id}`, {
      method: "DELETE"
    });
    await cargarServicios();
  }
=======
// Baja lógica
async function darBajaServicio(id) {
  if (!confirm("¿Estás seguro de que querés dar de baja este servicio?")) return;

  const res = await fetch(`${API_SERVICIOS}/${id}`);
  const servicio = await res.json();
  servicio.estado = false;

  await fetch(`${API_SERVICIOS}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(servicio)
  });

  await cargarServicios();
>>>>>>> 99f4d3c (Version Funcional Spring Security)
}
