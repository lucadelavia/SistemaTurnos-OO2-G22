const API_SUCURSALES = "/api/sucursales";
const API_ESPECIALIDADES = "/api/especialidades";
const API_DIAS = "/api/dias-atencion";
<<<<<<< HEAD
=======
const API_ROL = "/auth/rol";
>>>>>>> 99f4d3c (Version Funcional Spring Security)

const form = document.getElementById("sucursal-form");
const tbody = document.getElementById("sucursales-tbody");
const especialidadesContainer = document.getElementById("especialidades-container");
const diasContainer = document.getElementById("dias-container");
<<<<<<< HEAD

=======
const formCard = document.getElementById("form-card");
const thAcciones = document.getElementById("th-acciones");

let esAdmin = false;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
let editando = false;
let idEditando = null;

document.addEventListener("DOMContentLoaded", async () => {
<<<<<<< HEAD
=======
  await verificarRol();
>>>>>>> 99f4d3c (Version Funcional Spring Security)
  await cargarEspecialidades();
  await cargarDias();
  await cargarSucursales();
});

<<<<<<< HEAD
=======
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

>>>>>>> 99f4d3c (Version Funcional Spring Security)
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const especialidades = Array.from(document.querySelectorAll("input[name='especialidades']:checked"))
    .map(chk => ({ id: parseInt(chk.value) }));

  const dias = Array.from(document.querySelectorAll("input[name='dias']:checked"))
    .map(chk => ({ id: parseInt(chk.value) }));

  const sucursal = {
    direccion: form.direccion.value,
    telefono: form.telefono.value,
    horaApertura: form.horaApertura.value,
    horaCierre: form.horaCierre.value,
    espacio: parseInt(form.espacio.value),
    lstEspecialidad: especialidades,
    lstDiasDeAtencion: dias
  };

  const method = editando ? "PUT" : "POST";
  const url = editando ? `${API_SUCURSALES}/${idEditando}` : API_SUCURSALES;

  await fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(sucursal)
  });

  form.reset();
  editando = false;
  idEditando = null;
  await cargarSucursales();
});

async function cargarSucursales() {
<<<<<<< HEAD
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
      <td>
        <button class="btn btn-sm btn-warning" onclick="editarSucursal(${s.id})">✏️</button>
      </td>
    `;
    tbody.appendChild(row);
  });
}

async function editarSucursal(id) {
  const res = await fetch(`${API_SUCURSALES}/${id}`);
  const s = await res.json();

  form.direccion.value = s.direccion;
  form.telefono.value = s.telefono;
  form.horaApertura.value = s.horaApertura;
  form.horaCierre.value = s.horaCierre;
  form.espacio.value = s.espacio;

  document.querySelectorAll("input[name='especialidades']").forEach(input => {
    input.checked = s.lstEspecialidad?.some(e => e.id === parseInt(input.value)) || false;
  });

  document.querySelectorAll("input[name='dias']").forEach(input => {
    input.checked = s.lstDiasDeAtencion?.some(d => d.id === parseInt(input.value)) || false;
  });

  editando = true;
  idEditando = id;
}

async function cargarEspecialidades() {
  const res = await fetch(API_ESPECIALIDADES);
  const data = await res.json();

  especialidadesContainer.innerHTML = data.map(e => `
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="checkbox" name="especialidades" id="esp-${e.id}" value="${e.id}">
      <label class="form-check-label" for="esp-${e.id}">${e.nombre}</label>
    </div>
  `).join("");
}

async function cargarDias() {
  const res = await fetch(API_DIAS);
  const data = await res.json();

  diasContainer.innerHTML = data.map(d => `
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="checkbox" name="dias" id="dia-${d.id}" value="${d.id}">
      <label class="form-check-label" for="dia-${d.id}">${d.nombre}</label>
    </div>
  `).join("");
=======
  try {
    const res = await fetch(API_SUCURSALES);
    const sucursales = await res.json();

    tbody.innerHTML = "";

    sucursales
      .filter(s => s.activo !== false) // excluir inactivos si hay
      .forEach(s => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${s.id}</td>
          <td>${s.direccion}</td>
          <td>${s.telefono}</td>
          <td>${s.horaApertura} - ${s.horaCierre}</td>
          <td>${s.espacio}</td>
          <td>${s.lstEspecialidad?.map(e => e.nombre).join(", ") || "-"}</td>
          <td>${s.lstDiasDeAtencion?.map(d => d.nombre).join(", ") || "-"}</td>
          <td>
            ${esAdmin
              ? `<button class="btn btn-sm btn-warning" onclick="editarSucursal(${s.id})">✏️</button>`
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
    console.error("Error al cargar días", err);
  }
>>>>>>> 99f4d3c (Version Funcional Spring Security)
}
