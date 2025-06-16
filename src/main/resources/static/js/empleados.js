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

window.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarEspecialidades();
  await cargarEmpleados();
});

async function verificarRol() {
  try {
    const res = await fetch(API_ROL);
    if (!res.ok) throw new Error("No autenticado");
    const rol = await res.text();
    esAdmin = rol === "ADMIN";
    if (!esAdmin && formCard) formCard.style.display = "none";
  } catch (err) {
    console.error("No se pudo verificar el rol:", err);
    window.location.href = "/login";
  }
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const especialidadesSeleccionadas = Array.from(
    document.querySelectorAll("input[name='especialidades']:checked")
  ).map((input) => parseInt(input.value));

  const empleado = {
    nombre: form.nombre.value,
    apellido: form.apellido.value,
    email: form.email.value,
    direccion: form.direccion.value,
    dni: parseInt(form.dni.value),
    cuil: parseInt(form.cuil.value),
    password: form.password.value,
    matricula: form.matricula.value || null,
    especialidadesIds: especialidadesSeleccionadas
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
  if (!confirm("¿Estás seguro de dar de baja este empleado?")) return;

  try {
    const res = await fetch(`${API_EMPLEADOS}/${id}`);
    const empleado = await res.json();
    empleado.estado = false;

    await fetch(`${API_EMPLEADOS}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(empleado),
    });

    await cargarEmpleados();
  } catch (err) {
    console.error("Error al dar de baja:", err);
  }
}

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
    form.password.value = ""; // No prellenar contraseña por seguridad

    document.querySelectorAll("input[name='especialidades']").forEach((checkbox) => {
      checkbox.checked = e.lstEspecialidades?.some(es => es.id === parseInt(checkbox.value)) || false;
    });

    editando = true;
    idEditando = id;
  } catch (err) {
    console.error("Error al cargar empleado:", err);
  }
}

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
