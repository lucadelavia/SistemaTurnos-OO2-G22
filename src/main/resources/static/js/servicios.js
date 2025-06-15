const API_SERVICIOS = "/api/servicios";
const API_ESPECIALIDADES = "/api/especialidades";

const form = document.getElementById("servicio-form");
const tbody = document.getElementById("servicios-tbody");
const formCard = document.querySelector(".card.mb-5");
const thAcciones = document.getElementById("th-acciones");
const selectEspecialidad = document.getElementById("especialidad");

let editando = false;
let idEditando = null;
let esAdmin = false;

// Al iniciar
window.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarEspecialidades();
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

// Cargar especialidades
async function cargarEspecialidades() {
  try {
    const res = await fetch(API_ESPECIALIDADES);
    const especialidades = await res.json();

    selectEspecialidad.innerHTML = '<option value="" disabled selected>Seleccione una especialidad</option>';

    especialidades.forEach(e => {
      const option = document.createElement("option");
      option.value = e.id;
      option.textContent = e.nombre;
      selectEspecialidad.appendChild(option);
    });
  } catch (err) {
    console.error("Error al cargar especialidades:", err);
  }
}

// Enviar formulario
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const servicio = {
    nombreServicio: form.nombreServicio.value.trim(),
    duracion: parseInt(form.duracion.value),
    estado: true,
    especialidad: { id: parseInt(selectEspecialidad.value) }
  };

  const method = editando ? "PUT" : "POST";
  const url = editando ? `${API_SERVICIOS}/${idEditando}` : API_SERVICIOS;

  try {
    await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(servicio)
    });

    form.reset();
    selectEspecialidad.selectedIndex = 0;
    editando = false;
    idEditando = null;
    await cargarServicios();
  } catch (err) {
    console.error("Error al guardar servicio:", err);
  }
});

// Cargar servicios activos
async function cargarServicios() {
  try {
    const res = await fetch(API_SERVICIOS);
    const servicios = await res.json();

    tbody.innerHTML = "";

    servicios
      .filter(s => s.estado)
      .forEach(s => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${s.id}</td>
          <td>${s.nombreServicio}</td>
          <td>${s.duracion} min</td>
          <td>${s.especialidad?.nombre || "-"}</td>
          <td>${s.estado ? "Activo" : "Inactivo"}</td>
          <td ${!esAdmin ? 'style="display:none;"' : ''}>
            <button class="btn btn-sm btn-warning me-1" onclick="editarServicio(${s.id})">✏️</button>
            <button class="btn btn-sm btn-danger" onclick="darBajaServicio(${s.id})">🗑️</button>
          </td>
        `;
        tbody.appendChild(row);
      });
  } catch (err) {
    console.error("Error al cargar servicios:", err);
  }
}

// Editar
async function editarServicio(id) {
  try {
    const res = await fetch(`${API_SERVICIOS}/${id}`);
    const s = await res.json();

    form.nombreServicio.value = s.nombreServicio;
    form.duracion.value = s.duracion;
    selectEspecialidad.value = s.especialidad?.id || "";

    editando = true;
    idEditando = id;
  } catch (err) {
    console.error("Error al editar servicio:", err);
  }
}

// Baja lógica
async function darBajaServicio(id) {
  if (!confirm("¿Estás seguro de que querés dar de baja este servicio?")) return;

  try {
    const res = await fetch(`${API_SERVICIOS}/${id}`);
    const servicio = await res.json();
    servicio.estado = false;

    await fetch(`${API_SERVICIOS}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(servicio)
    });

    await cargarServicios();
  } catch (err) {
    console.error("Error al dar de baja el servicio:", err);
  }
}
