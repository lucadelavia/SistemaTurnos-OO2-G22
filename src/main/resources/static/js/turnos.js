const API_BASE = "/api";
const API_USUARIO = `${API_BASE}/usuario/me`;
const API_TURNOS = `${API_BASE}/turnos`;
const API_SUCURSALES = `${API_BASE}/sucursales`;
const API_SERVICIOS = `${API_BASE}/servicios`;
const API_EMPLEADOS = `${API_BASE}/empleados`;
const API_ESPECIALIDADES = `${API_BASE}/especialidades`;

let usuarioActual = null;

window.addEventListener("DOMContentLoaded", async () => {
  await cargarUsuario();

  if (usuarioActual?.rol === "CLIENTE") {
    document.getElementById("reserva-form").classList.remove("d-none");
    document.getElementById("cliente").value = usuarioActual.id;
  } else if (usuarioActual?.rol === "ADMIN") {
    document.getElementById("reserva-form").classList.remove("d-none");
    document.getElementById("cliente-group").classList.remove("d-none");
  }

  await cargarEspecialidades();
  cargarSucursales();
  cargarTurnos();

  document.getElementById("fecha").addEventListener("change", cargarHorarios);
  document.getElementById("sucursal").addEventListener("change", cargarHorarios);
  document.getElementById("servicio").addEventListener("change", async () => {
    const idServicio = document.getElementById("servicio").value;
    if (!idServicio) return;
    await cargarEmpleadosPorServicio(idServicio);
    await cargarHorarios();
  });
  document.getElementById("empleado").addEventListener("change", cargarHorarios);
  document.getElementById("especialidad").addEventListener("change", async () => {
    const id = document.getElementById("especialidad").value;
    if (!id) return;
    await cargarServiciosPorEspecialidad(id);
    await cargarEmpleadosPorEspecialidad(id);
  });

  document.getElementById("form-turno").addEventListener("submit", reservarTurno);
});

async function cargarUsuario() {
  try {
    const res = await fetch(API_USUARIO);
    usuarioActual = await res.json();
  } catch (err) {
    console.error("Error al cargar usuario", err);
  }
}

async function cargarEspecialidades() {
  try {
    const res = await fetch(API_ESPECIALIDADES);
    const data = await res.json();
    cargarOptions("especialidad", data, "nombre");
  } catch (err) {
    console.error("Error al cargar especialidades", err);
  }
}

function cargarSucursales() {
  fetch(API_SUCURSALES)
    .then(res => res.json())
    .then(data => cargarOptions("sucursal", data, "direccion"))
    .catch(err => console.error("Error al cargar sucursales", err));
}

async function cargarServiciosPorEspecialidad(idEspecialidad) {
  try {
    const res = await fetch(`${API_SERVICIOS}/especialidad/${idEspecialidad}`);
    const data = await res.json();
    cargarOptions("servicio", data, "nombreServicio");
  } catch (err) {
    console.error("Error al cargar servicios por especialidad", err);
  }
}

async function cargarEmpleadosPorEspecialidad(idEspecialidad) {
  try {
    const res = await fetch(`${API_EMPLEADOS}/especialidad/${idEspecialidad}`);
    const data = await res.json();
    cargarOptions("empleado", data, "nombre");
  } catch (err) {
    console.error("Error al cargar empleados por especialidad", err);
  }
}

async function cargarEmpleadosPorServicio(idServicio) {
  try {
    const res = await fetch(`${API_EMPLEADOS}/servicio/${idServicio}`);
    const data = await res.json();
    cargarOptions("empleado", data, "nombre");
  } catch (err) {
    console.error("Error al cargar empleados por servicio", err);
  }
}

function cargarOptions(selectId, lista, fieldName) {
  const select = document.getElementById(selectId);
  select.innerHTML = '<option value="">Seleccione</option>';
  if (!Array.isArray(lista)) return;
  lista.forEach(item => {
    const option = document.createElement("option");
    option.value = item.id;
    option.textContent = item[fieldName] || `#${item.id}`;
    select.appendChild(option);
  });
}

function cargarHorarios() {
  const idSucursal = document.getElementById("sucursal").value;
  const idServicio = document.getElementById("servicio").value;
  const idEmpleado = document.getElementById("empleado").value;
  const fecha = document.getElementById("fecha").value;

  if (!idSucursal || !idServicio || !idEmpleado || !fecha) return;

  const url = `${API_TURNOS}/disponibilidad?idSucursal=${idSucursal}&idServicio=${idServicio}&idEmpleado=${idEmpleado}&fecha=${fecha}`;

  fetch(url)
    .then(res => res.json())
    .then(data => {
      const select = document.getElementById("horario");
      select.innerHTML = '<option value="">Seleccione</option>';

      const mensaje = document.getElementById("mensaje-horarios");
      if (mensaje) mensaje.remove();

      if (data.length === 0) {
        const aviso = document.createElement("div");
        aviso.id = "mensaje-horarios";
        aviso.className = "alert alert-warning mt-3";
        aviso.textContent = "La sucursal no atiende el día seleccionado.";
        select.parentElement.appendChild(aviso);
        return;
      }

      data.forEach(hora => {
        const dt = new Date(hora);
        const option = document.createElement("option");
        option.value = dt.toISOString();
        option.textContent = dt.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
        select.appendChild(option);
      });
    })
    .catch(err => console.error("Error al cargar horarios:", err));
}

function reservarTurno(e) {
  e.preventDefault();

  const horario = document.getElementById("horario").value;
  const turno = {
    fechaHora: new Date(horario).toISOString(),
    estado: true,
    codigo: generarCodigo(),
    idCliente: parseInt(document.getElementById("cliente").value),
    idEmpleado: parseInt(document.getElementById("empleado").value),
    idSucursal: parseInt(document.getElementById("sucursal").value),
    idServicio: parseInt(document.getElementById("servicio").value)
  };

  fetch(API_TURNOS, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(turno)
  })
    .then(res => {
      if (!res.ok) throw new Error("Error al reservar el turno");
      return res.json();
    })
    .then(() => {
      alert("Turno reservado con éxito");
      document.getElementById("form-turno").reset();
      document.getElementById("horario").innerHTML = "";
      const mensaje = document.getElementById("mensaje-horarios");
      if (mensaje) mensaje.remove();
      cargarTurnos();
    })
    .catch(err => alert(err.message));
}

function cargarTurnos() {
  fetch(API_TURNOS)
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById("turnos-tbody");
      tbody.innerHTML = "";

      const turnosFiltrados =
        usuarioActual?.rol === "EMPLEADO"
          ? data.filter(t => t.empleadoId === usuarioActual.id)
          : usuarioActual?.rol === "CLIENTE"
          ? data.filter(t => t.clienteId === usuarioActual.id)
          : data;

      turnosFiltrados.forEach(t => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${t.id}</td>
          <td>${new Date(t.fechaHora).toLocaleString()}</td>
          <td>${t.codigo}</td>
          <td>${t.clienteNombre || t.clienteId || "-"}</td>
          <td>${t.empleadoNombre || t.empleadoId || "-"}</td>
          <td>${t.sucursalDireccion || t.sucursalId || "-"}</td>
          <td>${t.servicioNombre || t.servicioId || "-"}</td>
        `;
        tbody.appendChild(row);
      });
    })
    .catch(err => console.error("Error al cargar turnos:", err));
}

function generarCodigo() {
  const now = new Date();
  return `T-${now.getFullYear()}${now.getMonth() + 1}${now.getDate()}-${now.getTime().toString().slice(-4)}`;
}
