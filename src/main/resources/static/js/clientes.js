const API_URL = "/api/clientes";
const form = document.getElementById("cliente-form");
const tbody = document.getElementById("clientes-tbody");

let editando = false;
let idEditando = null;

// Cargar clientes al iniciar
window.addEventListener("DOMContentLoaded", cargarClientes);

// Envío de formulario
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const cliente = {
    nombre: form.nombre.value,
    apellido: form.apellido.value,
    email: form.email.value,
    direccion: form.direccion.value,
    dni: parseInt(form.dni.value),
    nroCliente: parseInt(form.nroCliente.value),
    estado: true,
    fechaAlta: new Date().toISOString()
  };

  try {
    if (editando) {
      await fetch(`${API_URL}/${idEditando}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente),
      });
    } else {
      await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente),
      });
    }

    form.reset();
    editando = false;
    idEditando = null;
    cargarClientes();
  } catch (err) {
    console.error("Error al guardar:", err);
  }
});

// Cargar y mostrar todos los clientes
async function cargarClientes() {
  tbody.innerHTML = "";
  const res = await fetch(API_URL);
  const clientes = await res.json();

  clientes.forEach((c) => {
    const fila = document.createElement("tr");
    fila.innerHTML = `
      <td>${c.id}</td>
      <td>${c.nombre}</td>
      <td>${c.apellido}</td>
      <td>${c.email}</td>
      <td>${c.direccion}</td>
      <td>${c.dni}</td>
      <td>${c.estado ? "Activo" : "Inactivo"}</td>
      <td>${c.fechaAlta ? c.fechaAlta.split("T")[0] : ""}</td>
      <td>
        <button class="btn btn-sm btn-warning" onclick="editarCliente(${c.id})">✏️</button>
        <button class="btn btn-sm btn-danger" onclick="eliminarCliente(${c.id})">🗑️</button>
      </td>
    `;
    tbody.appendChild(fila);
  });
}

// Eliminar cliente
async function eliminarCliente(id) {
  if (confirm("¿Estás seguro de dar de baja este cliente?")) {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    cargarClientes();
  }
}

// Editar cliente
async function editarCliente(id) {
  const res = await fetch(`${API_URL}/${id}`);
  const c = await res.json();

  form.nombre.value = c.nombre;
  form.apellido.value = c.apellido;
  form.email.value = c.email;
  form.direccion.value = c.direccion;
  form.dni.value = c.dni;
  form.nroCliente.value = c.nroCliente;

  editando = true;
  idEditando = id;
}
