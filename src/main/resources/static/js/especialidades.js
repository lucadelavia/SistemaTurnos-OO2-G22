const API_ESPECIALIDADES = "/api/especialidades";
<<<<<<< HEAD

const form = document.getElementById("especialidad-form");
const tbody = document.getElementById("especialidades-tbody");

let editando = false;
let idEditando = null;

// Cargar al iniciar
window.addEventListener("DOMContentLoaded", cargarEspecialidades);
=======
const API_USUARIO = "/auth/rol";

const form = document.getElementById("especialidad-form");
const tbody = document.getElementById("especialidades-tbody");
const formCard = document.getElementById("form-card");
const bienvenida = document.getElementById("bienvenida");
const thAcciones = document.getElementById("th-acciones"); // Encabezado Acciones

let editando = false;
let idEditando = null;
let esAdmin = false;

window.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarEspecialidades();
});

async function verificarRol() {
  try {
    const res = await fetch(API_USUARIO);
    const rol = await res.text();
    esAdmin = rol === "ADMIN";

    if (!esAdmin) {
      formCard.style.display = "none";
      thAcciones.style.display = "none"; // Oculta encabezado
    }

    const usuarioRes = await fetch("/api/usuario/me");
    if (usuarioRes.ok) {
      const user = await usuarioRes.json();
      bienvenida.textContent = `¡Hola, ${user.nombre} (${user.rol})!`;
    }

  } catch (e) {
    console.error("Error al verificar rol:", e);
    window.location.href = "/login";
  }
}
>>>>>>> 99f4d3c (Version Funcional Spring Security)

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
<<<<<<< HEAD
      <td>
        <button class="btn btn-sm btn-warning" onclick="editarEspecialidad(${esp.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="eliminarEspecialidad(${esp.id})">🗑️</button>
      </td>
=======
      ${esAdmin ? `
        <td>
          <button class="btn btn-sm btn-warning" onclick="editarEspecialidad(${esp.id})">✏️</button>
          <button class="btn btn-sm btn-danger" onclick="eliminarEspecialidad(${esp.id})">🗑️</button>
        </td>` : ``}
>>>>>>> 99f4d3c (Version Funcional Spring Security)
    `;
    tbody.appendChild(fila);
  });
}

async function editarEspecialidad(id) {
  const res = await fetch(`${API_ESPECIALIDADES}/${id}`);
  const esp = await res.json();
<<<<<<< HEAD

=======
>>>>>>> 99f4d3c (Version Funcional Spring Security)
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
