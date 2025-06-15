const apiBase = '/api';

let usuarioActual = null;

window.addEventListener('DOMContentLoaded', async () => {
  await cargarUsuario();

  if (usuarioActual.rol === 'CLIENTE') {
    document.getElementById('reserva-form').classList.remove('d-none');
  } else {
    document.getElementById('reserva-form').classList.add('d-none');
  }

  cargarSucursales();
  cargarServicios();
  cargarEmpleados();
  cargarTurnos();

  document.getElementById('fecha').addEventListener('change', cargarHorarios);
  document.getElementById('sucursal').addEventListener('change', cargarHorarios);
  document.getElementById('servicio').addEventListener('change', cargarHorarios);
  document.getElementById('empleado').addEventListener('change', cargarHorarios);

  document.getElementById('reserva-form').addEventListener('submit', reservarTurno);
});

async function cargarUsuario() {
  const res = await fetch('/auth/usuario');
  usuarioActual = await res.json();
}

function cargarSucursales() {
  fetch(`${apiBase}/sucursales`)
    .then(res => res.json())
    .then(data => cargarOptions('sucursal', data));
}

function cargarServicios() {
  fetch(`${apiBase}/servicios`)
    .then(res => res.json())
    .then(data => cargarOptions('servicio', data));
}

function cargarEmpleados() {
  fetch(`${apiBase}/empleados`)
    .then(res => res.json())
    .then(data => cargarOptions('empleado', data));
}

function cargarOptions(selectId, lista) {
  const select = document.getElementById(selectId);
  select.innerHTML = '<option value="">Seleccione</option>';
  lista.forEach(item => {
    const option = document.createElement('option');
    option.value = item.id;
    option.textContent = item.nombre || item.direccion || item.nombreServicio || `#${item.id}`;
    select.appendChild(option);
  });
}

function cargarHorarios() {
  const idSucursal = document.getElementById('sucursal').value;
  const idServicio = document.getElementById('servicio').value;
  const idEmpleado = document.getElementById('empleado').value;
  const fecha = document.getElementById('fecha').value;

  if (!idSucursal || !idServicio || !idEmpleado || !fecha) return;

  fetch(`${apiBase}/turnos/disponibilidad?idSucursal=${idSucursal}&idServicio=${idServicio}&idEmpleado=${idEmpleado}&fecha=${fecha}`)
    .then(res => res.json())
    .then(data => {
      const select = document.getElementById('horario');
      select.innerHTML = '<option value="">Seleccione</option>';
      data.forEach(hora => {
        const dt = new Date(hora);
        const option = document.createElement('option');
        option.value = dt.toISOString();
        option.textContent = dt.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        select.appendChild(option);
      });
    });
}

function reservarTurno(e) {
  e.preventDefault();

  const horario = document.getElementById('horario').value;
  const fechaHora = new Date(horario).toISOString();

  const turno = {
    fechaHora,
    estado: true,
    codigo: generarCodigo(),
    cliente: { id: parseInt(document.getElementById('cliente').value) },
    empleado: { id: parseInt(document.getElementById('empleado').value) },
    sucursal: { id: parseInt(document.getElementById('sucursal').value) },
    servicio: { id: parseInt(document.getElementById('servicio').value) }
  };

  fetch(`${apiBase}/turnos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(turno)
  })
    .then(res => {
      if (!res.ok) throw new Error('Error al reservar el turno');
      return res.json();
    })
    .then(() => {
      alert('Turno reservado con éxito');
      cargarTurnos();
      document.getElementById('reserva-form').reset();
      document.getElementById('horario').innerHTML = '';
    })
    .catch(err => alert(err.message));
}

function cargarTurnos() {
  fetch(`${apiBase}/turnos`)
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById('turnos-tbody');
      tbody.innerHTML = '';
      const turnosFiltrados = usuarioActual.rol === 'EMPLEADO'
        ? data.filter(t => t.empleado?.id === usuarioActual.id)
        : data;

      turnosFiltrados.forEach(t => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${t.id}</td>
          <td>${new Date(t.fechaHora).toLocaleString()}</td>
          <td>${t.codigo}</td>
          <td>${t.cliente?.nombre || t.cliente?.id || '-'}</td>
          <td>${t.empleado?.nombre || t.empleado?.id || '-'}</td>
          <td>${t.sucursal?.direccion || t.sucursal?.id || '-'}</td>
          <td>${t.servicio?.nombreServicio || t.servicio?.id || '-'}</td>
        `;
        tbody.appendChild(row);
      });
    });
}

function generarCodigo() {
  const now = new Date();
  return `T-${now.getFullYear()}${now.getMonth() + 1}${now.getDate()}-${now.getTime().toString().slice(-4)}`;
}
