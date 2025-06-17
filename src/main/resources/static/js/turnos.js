const apiBase = '/api';

let usuarioActual = null;
let editando = false;
let idEditando = null;

window.addEventListener('DOMContentLoaded', async () => {
  await cargarUsuario();

  const rol = usuarioActual.rol?.toUpperCase();
  const esAdmin = rol === 'ADMIN';
  const esCliente = rol === 'CLIENTE';
  const esEmpleado = rol === 'EMPLEADO';

  if (esAdmin || esCliente) {
    document.getElementById('reserva-form').classList.remove('d-none');
    if (esAdmin) {
      document.getElementById('cliente-group').classList.remove('d-none');
    } else {
      document.getElementById('cliente').value = usuarioActual.id;
    }
  }

  await cargarEstablecimientos();
  await cargarTurnos();

  document.getElementById('establecimiento').addEventListener('change', cargarSucursales);
  document.getElementById('sucursal').addEventListener('change', cargarEspecialidades);
  document.getElementById('especialidad').addEventListener('change', async () => {
    await cargarServicios();
    await cargarEmpleadosPorEspecialidad();
  });
  document.getElementById('servicio').addEventListener('change', cargarHorarios);
  document.getElementById('empleado').addEventListener('change', cargarHorarios);
  document.getElementById('fecha').addEventListener('change', cargarHorarios);

  document.getElementById('form-turno').addEventListener('submit', reservarTurno);
});

async function cargarUsuario() {
  const res = await fetch('/api/usuario/me'); // CORREGIDO
  usuarioActual = await res.json();
  console.log('Usuario actual:', usuarioActual);
}

async function cargarEstablecimientos() {
  const res = await fetch(`${apiBase}/establecimientos`);
  const data = await res.json();
  cargarOptions('establecimiento', data, 'nombre');
}

async function cargarSucursales() {
  const idEstablecimiento = document.getElementById('establecimiento').value;
  const res = await fetch(`${apiBase}/sucursales`);
  const todas = await res.json();
  const filtradas = todas.filter(s => s.idEstablecimiento === parseInt(idEstablecimiento));
  cargarOptions('sucursal', filtradas, 'direccion');
  resetSelects(['especialidad', 'servicio', 'empleado']);
}

async function cargarEspecialidades() {
  const idSucursal = document.getElementById('sucursal').value;
  if (!idSucursal) return;

  const res = await fetch(`${apiBase}/sucursales/${idSucursal}`);
  const sucursal = await res.json();
  const especialidades = sucursal.lstEspecialidad || [];

  cargarOptions('especialidad', especialidades, 'nombre');
  resetSelects(['servicio', 'empleado']);
}

async function cargarServicios() {
  const idEspecialidad = document.getElementById('especialidad').value;
  if (!idEspecialidad) return;

  const res = await fetch(`${apiBase}/servicios/especialidad/${idEspecialidad}`);
  const servicios = await res.json();

  cargarOptions('servicio', servicios, 'nombreServicio');
}

async function cargarEmpleadosPorEspecialidad() {
  const idEspecialidad = document.getElementById('especialidad').value;
  if (!idEspecialidad) return;

  const res = await fetch(`${apiBase}/empleados/especialidad/${idEspecialidad}`);
  const empleados = await res.json();

  cargarOptions('empleado', empleados, 'nombre');
}

function cargarOptions(selectId, lista, prop) {
  const select = document.getElementById(selectId);
  select.innerHTML = '<option value="">Seleccione</option>';
  lista.forEach(item => {
    const option = document.createElement('option');
    option.value = item.id;
    option.textContent = item[prop] || `#${item.id}`;
    select.appendChild(option);
  });
}

function resetSelects(ids) {
  ids.forEach(id => {
    const select = document.getElementById(id);
    if (select) select.innerHTML = '<option value="">Seleccione</option>';
  });
}

async function cargarHorarios() {
  const idSucursal = document.getElementById('sucursal').value;
  const idServicio = document.getElementById('servicio').value;
  const idEmpleado = document.getElementById('empleado').value;
  const fecha = document.getElementById('fecha').value;
  if (!idSucursal || !idServicio || !idEmpleado || !fecha) return;

  const res = await fetch(`${apiBase}/turnos/disponibilidad?idSucursal=${idSucursal}&idServicio=${idServicio}&idEmpleado=${idEmpleado}&fecha=${fecha}`);
  const horarios = await res.json();

  const select = document.getElementById('horario');
  select.innerHTML = '<option value="">Seleccione</option>';

  horarios.forEach(horaStr => {
    const dt = new Date(horaStr);
    const localDateTime = dt.getFullYear() + '-' +
      String(dt.getMonth() + 1).padStart(2, '0') + '-' +
      String(dt.getDate()).padStart(2, '0') + 'T' +
      String(dt.getHours()).padStart(2, '0') + ':' +
      String(dt.getMinutes()).padStart(2, '0');

    const option = document.createElement('option');
    option.value = localDateTime;
    option.textContent = dt.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    select.appendChild(option);
  });
}

async function reservarTurno(e) {
  e.preventDefault();

  const turno = {
    fechaHora: document.getElementById('horario').value,
    estado: true,
    codigo: generarCodigo(),
    cliente: { id: parseInt(document.getElementById('cliente').value || usuarioActual.id) },
    empleado: { id: parseInt(document.getElementById('empleado').value) },
    sucursal: { id: parseInt(document.getElementById('sucursal').value) },
    servicio: { id: parseInt(document.getElementById('servicio').value) }
  };

  const url = `${apiBase}/turnos${editando ? `/${idEditando}` : ''}`;
  const method = editando ? 'PUT' : 'POST';

  const res = await fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(turno)
  });

  if (!res.ok) {
    alert('Error al guardar turno');
    return;
  }

  alert(editando ? 'Turno actualizado' : 'Turno reservado');
  cancelarEdicion();
  cargarTurnos();
}

function cancelarEdicion() {
  editando = false;
  idEditando = null;
  document.getElementById('form-turno').reset();
  document.getElementById('horario').innerHTML = '';
}

async function cargarTurnos() {
  const rol = usuarioActual.rol?.toUpperCase();
  let url = `${apiBase}/turnos`;

  if (rol === 'CLIENTE') {
    url = `${apiBase}/turnos/cliente/${usuarioActual.id}`;
  } else if (rol === 'EMPLEADO') {
    url = `${apiBase}/turnos/empleado/${usuarioActual.id}`;
  }

  console.log('Cargando turnos desde:', url);

  const res = await fetch(url);
  const data = await res.json();

  const tbody = document.getElementById('turnos-tbody');
  tbody.innerHTML = '';

  data.forEach(t => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${t.id}</td>
      <td>${new Date(t.fechaHora).toLocaleString()}</td>
      <td>${t.codigo}</td>
      <td>${t.cliente?.nombre || '-'}</td>
      <td>${t.empleado?.nombre || '-'}</td>
      <td>${t.sucursal?.direccion || '-'}</td>
      <td>${t.servicio?.nombreServicio || '-'}</td>
      ${
        rol !== 'EMPLEADO'
          ? `<td>
              <button class="btn btn-sm btn-warning me-2" onclick="editarTurno(${t.id})">✏️</button>
              <button class="btn btn-sm btn-danger" onclick="eliminarTurno(${t.id})">🗑️</button>
            </td>`
          : ''
      }
    `;
    tbody.appendChild(row);
  });
}

async function editarTurno(id) {
  const res = await fetch(`${apiBase}/turnos/${id}`);
  const t = await res.json();

  document.getElementById('cliente').value = t.cliente.id;

  const sucursalRes = await fetch(`${apiBase}/sucursales/${t.sucursal.id}`);
  const sucursal = await sucursalRes.json();
  document.getElementById('establecimiento').value = sucursal.idEstablecimiento;
  await cargarSucursales();

  document.getElementById('sucursal').value = t.sucursal.id;
  await cargarEspecialidades();

  const especialidadId = t.servicio.especialidad.id;
  document.getElementById('especialidad').value = especialidadId;

  await cargarServicios();
  document.getElementById('servicio').value = t.servicio.id;

  await cargarEmpleadosPorEspecialidad();
  document.getElementById('empleado').value = t.empleado.id;

  const fechaStr = t.fechaHora.split('T')[0];
  document.getElementById('fecha').value = fechaStr;
  await cargarHorarios();

  const horarioStr = t.fechaHora.slice(0, 16);
  document.getElementById('horario').value = horarioStr;

  editando = true;
  idEditando = id;
}

async function eliminarTurno(id) {
  if (!confirm('¿Seguro que deseas eliminar el turno?')) return;

  const res = await fetch(`${apiBase}/turnos/${id}`, { method: 'DELETE' });
  if (!res.ok) return alert('Error al eliminar turno');

  cargarTurnos();
}

function generarCodigo() {
  const now = new Date();
  return `T-${now.getFullYear()}${now.getMonth() + 1}${now.getDate()}-${now.getTime().toString().slice(-4)}`;
}
