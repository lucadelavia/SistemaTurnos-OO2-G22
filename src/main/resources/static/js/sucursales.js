const API_SUCURSALES = "/api/sucursales";
const API_ESPECIALIDADES = "/api/especialidades";
const API_DIAS = "/api/dias-atencion";
const API_ESTABLECIMIENTOS = "/api/establecimientos";
const API_ROL = "/auth/rol";

const form = document.getElementById("sucursal-form");
const tbody = document.getElementById("sucursales-tbody");
const especialidadesContainer = document.getElementById("especialidades-container");
const diasContainer = document.getElementById("dias-container");
const formCard = document.getElementById("form-card");
const thAcciones = document.getElementById("th-acciones");

let esAdmin = false;
let editando = false;
let idEditando = null;

document.addEventListener("DOMContentLoaded", async () => {
  await verificarRol();
  await cargarEspecialidades();
  await cargarDias();
  await cargarEstablecimientos();
  await cargarSucursales();
});

async function verificarRol() {
  try {
    const res = await fetch(API_ROL);
    const rol = await res.text();
    esAdmin = rol === "ADMIN";

    if (!esAdmin) {
      formCard.classList.add("d-none");
      if (thAcciones) thAcciones.classList.add("d-none");
    }
  } catch (err) {
    console.error("Error al verificar rol", err);
  }
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const establecimientoId = parseInt(document.getElementById("establecimiento").value);
  if (isNaN(establecimientoId)) {
    alert("Debe seleccionar un establecimiento.");
    return;
  }

  const sucursal = {
    direccion: form.direccion.value,
    telefono: form.telefono.value,
    horaApertura: form.horaApertura.value,
    horaCierre: form.horaCierre.value,
    espacio: parseInt(form.espacio.value),
    estado: true,
    idEstablecimiento: establecimientoId,
    idEspecialidades: Array.from(document.querySelectorAll("input[name='especialidades']:checked")).map(chk => parseInt(chk.value)),
    idDiasDeAtencion: Array.from(document.querySelectorAll("input[name='dias']:checked")).map(chk => parseInt(chk.value))
  };

  const method = editando ? "PUT" : "POST";
  const url = editando ? `${API_SUCURSALES}/${idEditando}` : API_SUCURSALES;

  try {
    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(sucursal)
    });

    if (!res.ok) throw new Error("Error al guardar sucursal");

    form.reset();
    editando = false;
    idEditando = null;
    await cargarSucursales();
  } catch (err) {
    console.error("Error al guardar sucursal", err);
    alert("No se pudo guardar la sucursal.");
  }
});

async function cargarSucursales() {
  try {
    const res = await fetch(API_SUCURSALES);
    const sucursales = await res.json();

    tbody.innerHTML = "";

    sucursales.forEach(s => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${s.id}</td>
        <td>${s.direccion}</td>
        <td>${s.telefono}</td>
        <td>${s.horaApertura} - ${s.horaCierre}</td>
        <td>${s.espacio}</td>
        <td>${s.lstEspecialidad?.map(e => e.nombre).join(", ") || "-"}</td>
        <td>${s.lstDiasDeAtencion?.map(d => d.nombre).join(", ") || "-"}</td>
        <td>${s.nombreEstablecimiento || "-"}</td>
        <td>
          ${esAdmin
            ? `
              <button class="btn btn-sm btn-warning" onclick="editarSucursal(${s.id})">✏️</button>
              <button class="btn btn-sm btn-danger" onclick="eliminarSucursal(${s.id})">🗑️</button>
            `
            : ""}
        </td>
      `;
      tbody.appendChild(row);
    });
  } catch (err) {
    console.error("Error al cargar sucursales", err);
  }
}

async function editarSucursal(id) {
  try {
    const res = await fetch(`${API_SUCURSALES}/${id}`);
    const s = await res.json();

    form.direccion.value = s.direccion;
    form.telefono.value = s.telefono;
    form.horaApertura.value = s.horaApertura;
    form.horaCierre.value = s.horaCierre;
    form.espacio.value = s.espacio;
    document.getElementById("establecimiento").value = s.establecimiento?.id || "";

    document.querySelectorAll("input[name='especialidades']").forEach(input => {
      input.checked = s.lstEspecialidad?.some(e => e.id === parseInt(input.value)) || false;
    });

    document.querySelectorAll("input[name='dias']").forEach(input => {
      input.checked = s.lstDiasDeAtencion?.some(d => d.id === parseInt(input.value)) || false;
    });

    editando = true;
    idEditando = id;
  } catch (err) {
    console.error("Error al editar sucursal", err);
  }
}

async function eliminarSucursal(id) {
  if (!confirm("¿Estás seguro de eliminar esta sucursal?")) return;

  try {
    const res = await fetch(`${API_SUCURSALES}/${id}`, {
      method: "DELETE"
    });

    if (!res.ok) throw new Error("Error al eliminar sucursal");

    await cargarSucursales();
  } catch (err) {
    console.error("Error al eliminar sucursal", err);
    alert("No se pudo eliminar la sucursal.");
  }
}

async function cargarEspecialidades() {
  try {
    const res = await fetch(API_ESPECIALIDADES);
    const data = await res.json();

    especialidadesContainer.innerHTML = data
      .map(e => `
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" name="especialidades" id="esp-${e.id}" value="${e.id}">
          <label class="form-check-label" for="esp-${e.id}">${e.nombre}</label>
        </div>
      `).join("");
  } catch (err) {
    console.error("Error al cargar especialidades", err);
  }
}

async function cargarDias() {
  try {
    const res = await fetch(API_DIAS);
    const data = await res.json();

    diasContainer.innerHTML = data
      .map(d => `
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" name="dias" id="dia-${d.id}" value="${d.id}">
          <label class="form-check-label" for="dia-${d.id}">${d.nombre}</label>
        </div>
      `).join("");
  } catch (err) {
    console.error("Error al cargar días de atención", err);
  }
}

async function cargarEstablecimientos() {
  try {
    const res = await fetch(API_ESTABLECIMIENTOS);
    const data = await res.json();

    const select = document.getElementById("establecimiento");
    select.innerHTML = `<option value="">Seleccionar establecimiento</option>`;

    data.forEach(est => {
      const option = document.createElement("option");
      option.value = est.id;
      option.textContent = est.nombre;
      select.appendChild(option);
    });
  } catch (err) {
    console.error("Error al cargar establecimientos", err);
  }
}
