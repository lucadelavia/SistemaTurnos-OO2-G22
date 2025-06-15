const API_BASE = "/api";
const API_USUARIO = "/auth/usuario";
const API_TURNOS = `${API_BASE}/turnos`;
const API_SUCURSALES = `${API_BASE}/sucursales`;
const API_SERVICIOS = `${API_BASE}/servicios`;
const API_EMPLEADOS = `${API_BASE}/empleados`;
const API_CLIENTES = `${API_BASE}/clientes`;

let usuarioActual = null;
let clienteActual = null;
let empleadoActual = null;

window.addEventListener("DOMContentLoaded", async () => {
  await cargarUsuario();

  // Mostrar el formulario si es cliente o admin
  if (usuarioActual?.rol === "CLIENTE" || usuarioActual?.rol === "ADMIN") {
    document.getElementById("reserva-form").classList.remove("d-none");
  }

  // Obtener cliente o empleado según el rol
  if (usuarioActual?.rol === "CLIENTE") {
    const res = await fetch(`${API_CLIENTES}/${usuarioActual.id}`);
    if (res.ok) clienteActual = await res.json();
  }

  if (usuarioActual?.rol === "EMPLEADO") {
    const res = await fetch(`${API_EMPLEADOS}/${usuarioActual.id}`);
    if (res.ok) empleadoActual = await res.json();
  }

  // Ocultar el campo cliente manual si es cliente logueado
  if (usuarioActual?.rol === "CLIENTE") {
    const inputCliente = document.getElementById("cliente-group");
    if (inputCliente) inputCliente.classList.add("d-none");
  }

  cargarSucursales();
  cargarServicios();
  cargarEmpleados();
  cargarTurnos();

  document.getElementById("fecha").addEventListener("change", cargarHorarios);
  document.getElementById("sucursal").addEventListener("change", cargarHorarios);
  document.getElementById("servicio").addEventListener("change", cargarHorarios);
  document.getElementById("empleado").addEventListener("change", cargarHorarios);
  document.querySelector("#reserva-form form").addEventListener("submit", reservarTurno);
});

async function cargarUsuario() {
  try {
    const res = await fetch(API_USUARIO);
    usuarioActual = await res.json();
  } catch (err) {
    console.error("Error al cargar usuario", err);
  }
}

function cargarSucursales() {
  fetch(API_SUCURSALES)
    .then(res => res.json())
    .then(data => cargarOptions("sucursal", data, "direccion"))
    .catch(err => console.error("Error al cargar sucursales", err));
}

function cargarServicios() {
  fetch(API_SERVICIOS)
    .then(res => res.json())
    .then(data => cargarOptions("servicio", data, "nombreServicio"))
    .catch(err => console.error("Error al cargar servicios", err));
}

function cargarEmpleados() {
  fetch(API_EMPLEADOS)
    .then(res => res.json())
    .then(data => cargarOptions("empleado", data, "nombre"))
    .catch(err => console.error("Error al cargar empleados", err));
}

function cargarOptions(selectId, lista, fieldName) {
  const select = document.getElementById(selectId);
  select.innerHTML = '<option value="">Seleccione</option>';
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
      data.forEach(hora => {
        const dt = new Date(hora);
        const option = document.createElement("option");
        option.value = dt.toISOString();
        option.textContent = dt.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
        select.appendChild(option);
      });
    });
}

function reservarTurno(e) {
  e.preventDefault();

  const horario = document.getElementById("horario").value;

  const turno = {
    fechaHora: new Date(horario).toISOString(),
    estado: true,
    codigo: generarCodigo(),
    cliente: { id: clienteActual?.id ?? parseInt(document.getElementById("cliente").value) },
    empleado: { id: parseInt(document.getElementById("empleado").value) },
    sucursal: { id: parseInt(document.getElementById("sucursal").value) },
    servicio: { id: parseInt(document.getElementById("servicio").value) }
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
      document.getElementById("reserva-form").reset();
      document.getElementById("horario").innerHTML = "";
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
        usuarioActual?.rol === "EMPLEADO" && empleadoActual
          ? data.filter(t => t.empleado?.id === empleadoActual.id)
          : usuarioActual?.rol === "CLIENTE" && clienteActual
          ? data.filter(t => t.cliente?.id === clienteActual.id)
          : data;

      turnosFiltrados.forEach(t => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${t.id}</td>
          <td>${new Date(t.fechaHora).toLocaleString()}</td>
          <td>${t.codigo}</td>
          <td>${t.cliente?.nombre || t.cliente?.id || "-"}</td>
          <td>${t.empleado?.nombre || t.empleado?.id || "-"}</td>
          <td>${t.sucursal?.direccion || t.sucursal?.id || "-"}</td>
          <td>${t.servicio?.nombreServicio || t.servicio?.id || "-"}</td>
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
