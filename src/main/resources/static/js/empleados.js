const API_EMPLEADOS = "/api/empleados";
const API_ESPECIALIDADES = "/api/especialidades";

const form = document.getElementById("empleado-form");
const tbody = document.getElementById("empleados-tbody");
const especialidadesContainer = document.getElementById("especialidades-container");

let editando = false;
let idEditando = null;

// Cargar empleados y especialidades al iniciar
window.addEventListener("DOMContentLoaded", async () => {
  await cargarEspecialidades();
  await cargarEmpleados();
});

// Envío del formulario
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const especialidadesSeleccionadas = Array.from(
    document.querySelectorAll("input[name='especialidades']:checked")
  ).map((input) => ({ id: parseInt(input.value) }));

  const empleado = {
    nombre: form.nombre.value,
    apellido: form.apellido.value,
    email: form.email.value,
    direccion: form.direccion.value,
    dni: parseInt(form.dni.value),
    cuil: parseInt(form.cuil.value),
    matricula: form.matricula.value || null,
    estado: true,
    fechaAlta: new Date().toISOString(),
    lstEspecialidades: especialidadesSeleccionadas
  };

  try {
    const url = editando
      ? `${API_EMPLEADOS}/${idEditando}`
      : `${API_EMPLEADOS}/con-especialidades`;
    const method = editando ? "PUT" : "POST";

    await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(empleado),
    });

    form.reset();
    editando = false;
    idEditando = null;
    await cargarEmpleados();
  } catch (err) {
    console.error("Error al guardar:", err);
  }
});

// Cargar empleados
async function cargarEmpleados() {
  tbody.innerHTML = "";
  const res = await fetch(API_EMPLEADOS);
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
      <td>${e.matricula ?? "-"}</td>
      <td>${e.lstEspecialidades?.map(es => es.nombre).join(", ") || "-"}</td>
      <td>${e.estado ? "Activo" : "Inactivo"}</td>
      <td>${e.fechaAlta ? e.fechaAlta.split("T")[0] : ""}</td>
      <td>
        <button class="btn btn-sm btn-warning" onclick="editarEmpleado(${e.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="darBajaEmpleado(${e.id})">🗑️</button>
      </td>
    `;
    tbody.appendChild(fila);
  });
}

// Dar de baja lógico
async function darBajaEmpleado(id) {
  if (confirm("¿Estás seguro de dar de baja este empleado?")) {
    const res = await fetch(`${API_EMPLEADOS}/${id}`);
    const empleado = await res.json();
    empleado.estado = false;

    await fetch(`${API_EMPLEADOS}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(empleado),
    });

    await cargarEmpleados();
  }
}

// Editar
async function editarEmpleado(id) {
  const res = await fetch(`${API_EMPLEADOS}/${id}`);
  const e = await res.json();

  form.nombre.value = e.nombre;
  form.apellido.value = e.apellido;
  form.email.value = e.email;
  form.direccion.value = e.direccion;
  form.dni.value = e.dni;
  form.cuil.value = e.cuil;
  form.matricula.value = e.matricula ?? "";

  document.querySelectorAll("input[name='especialidades']").forEach((checkbox) => {
    checkbox.checked = e.lstEspecialidades?.some(es => es.id === parseInt(checkbox.value)) || false;
  });

  editando = true;
  idEditando = id;
}

// Cargar especialidades
async function cargarEspecialidades() {
  const res = await fetch(API_ESPECIALIDADES);
  const especialidades = await res.json();

  especialidadesContainer.innerHTML = especialidades.map(es => `
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="checkbox" id="esp-${es.id}" name="especialidades" value="${es.id}" />
      <label class="form-check-label" for="esp-${es.id}">${es.nombre}</label>
    </div>
  `).join("");
}
