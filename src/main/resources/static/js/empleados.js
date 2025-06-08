const API_URL = "/api/empleados";
const form = document.getElementById("empleado-form");
const tbody = document.getElementById("empleados-tbody");

let editando = false;
let idEditando = null;

// Cargar empleados al iniciar
window.addEventListener("DOMContentLoaded", cargarEmpleados);

// Envío de formulario
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const empleado = {
    nombre: form.nombre.value,
    apellido: form.apellido.value,
    email: form.email.value,
    direccion: form.direccion.value,
    dni: parseInt(form.dni.value),
    cuil: parseInt(form.cuil.value),
    matricula: form.matricula.value || null,
    estado: true
    // fechaAlta la genera el backend
  };

  try {
    if (editando) {
      await fetch(`${API_URL}/${idEditando}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado),
      });
    } else {
      await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empleado),
      });
    }

    form.reset();
    editando = false;
    idEditando = null;
    cargarEmpleados();
  } catch (err) {
    console.error("Error al guardar:", err);
  }
});

// Cargar empleados
async function cargarEmpleados() {
  tbody.innerHTML = "";
  try {
    const res = await fetch(API_URL);
    const empleados = await res.json();

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
        <td>${e.matricula || "-"}</td>
        <td>${e.estado ? "Activo" : "Inactivo"}</td>
        <td>${e.fechaAlta ? e.fechaAlta.split("T")[0] : ""}</td>
        <td>
          <button class="btn btn-sm btn-warning" onclick="editarEmpleado(${e.id})">✏️</button>
          <button class="btn btn-sm btn-danger" onclick="eliminarEmpleado(${e.id})">🗑️</button>
        </td>
      `;
      tbody.appendChild(fila);
    });
  } catch (err) {
    console.error("Error al cargar empleados:", err);
  }
}

// Eliminar empleado
async function eliminarEmpleado(id) {
  if (confirm("¿Estás seguro de dar de baja este empleado?")) {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    cargarEmpleados();
  }
}

// Editar empleado
async function editarEmpleado(id) {
  try {
    const res = await fetch(`${API_URL}/${id}`);
    const e = await res.json();

    form.nombre.value = e.nombre;
    form.apellido.value = e.apellido;
    form.email.value = e.email;
    form.direccion.value = e.direccion;
    form.dni.value = e.dni;
    form.cuil.value = e.cuil;
    form.matricula.value = e.matricula || "";

    editando = true;
    idEditando = id;
  } catch (err) {
    console.error("Error al obtener empleado:", err);
  }
}
