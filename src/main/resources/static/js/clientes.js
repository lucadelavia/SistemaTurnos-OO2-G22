const API_URL = "/api/clientes";
const ROL_URL = "/auth/rol";
const form = document.getElementById("cliente-form");
const tbody = document.getElementById("clientes-tbody");

let editando = false;
let idEditando = null;
let rolUsuario = null;

window.addEventListener("DOMContentLoaded", async () => {
  rolUsuario = await obtenerRol();
  if (rolUsuario !== "ADMIN") {
    form.style.display = "none";
  } else {
    togglePasswordFields(true); // Mostrar campos de contraseña desde el inicio
  }
  await cargarClientes();
});

async function obtenerRol() {
  try {
    const res = await fetch(ROL_URL);
    if (!res.ok) throw new Error("Error al obtener rol");
    return await res.text();
  } catch (err) {
    console.error("No se pudo determinar el rol del usuario", err);
    return null;
  }
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const pass1 = form.password.value.trim();
  const pass2 = form.confirmPassword.value.trim();

  if (pass1 || pass2) {
    if (pass1 !== pass2) {
      alert("Las contraseñas no coinciden.");
      return;
    }
  }

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

  if (pass1) {
    cliente.password = pass1; // Solo enviar si fue completado
  }

  try {
    const method = editando ? "PUT" : "POST";
    const url = editando ? `${API_URL}/${idEditando}` : API_URL;

    await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(cliente),
    });

    form.reset();
    editando = false;
    idEditando = null;
    togglePasswordFields(true);
    await cargarClientes();
  } catch (err) {
    console.error("Error al guardar:", err);
  }
});

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
        ${rolUsuario === "ADMIN" ? `
          <button class="btn btn-sm btn-warning" onclick="editarCliente(${c.id})">✏️</button>
          <button class="btn btn-sm btn-danger" onclick="eliminarCliente(${c.id})">🗑️</button>
        ` : "-"}
      </td>
    `;
    tbody.appendChild(fila);
  });
}

async function eliminarCliente(id) {
  if (confirm("¿Estás seguro de dar de baja este cliente?")) {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    await cargarClientes();
  }
}

async function editarCliente(id) {
  const res = await fetch(`${API_URL}/${id}`);
  const c = await res.json();

  form.nombre.value = c.nombre;
  form.apellido.value = c.apellido;
  form.email.value = c.email;
  form.direccion.value = c.direccion;
  form.dni.value = c.dni;
  form.nroCliente.value = c.nroCliente;

  form.password.value = "";
  form.confirmPassword.value = "";

  editando = true;
  idEditando = id;
  togglePasswordFields(true);
}

function togglePasswordFields(show) {
  const passwordGroup = document.getElementById("password-group");
  const confirmGroup = document.getElementById("confirm-password-group");

  if (show) {
    passwordGroup.classList.remove("d-none");
    confirmGroup.classList.remove("d-none");
  } else {
    passwordGroup.classList.add("d-none");
    confirmGroup.classList.add("d-none");
    form.password.value = "";
    form.confirmPassword.value = "";
  }
}
