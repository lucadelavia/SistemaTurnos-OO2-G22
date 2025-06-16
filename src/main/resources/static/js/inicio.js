document.addEventListener("DOMContentLoaded", async () => {
  try {
    const res = await fetch("/auth/usuario");

    if (!res.ok) {
      return window.location.href = "/login";
    }

    const usuario = await res.json();
    const nombre = usuario.nombre || "Usuario";
    const rol = usuario.rol || "Sin rol";

    // Mostrar mensaje de bienvenida
    const bienvenida = document.getElementById("bienvenida");
    if (bienvenida) {
      bienvenida.textContent = `¡Bienvenido, ${nombre} (${rol})!`;
    }

    // Mostrar módulos según el rol
    const mostrar = (ids) => ids.forEach(id => {
      document.getElementById(id)?.classList.remove("d-none");
    });

    switch (rol) {
      case "ADMIN":
        mostrar(["mod-clientes", "mod-empleados", "mod-turnos", "mod-establecimientos", "mod-sucursales", "mod-servicios", "mod-especialidades"]);
        break;
      case "EMPLEADO":
        mostrar(["mod-turnos", "mod-servicios", "mod-especialidades", "mod-sucursales", "mod-establecimientos"]);
        break;
      case "CLIENTE":
        mostrar(["mod-turnos", "mod-servicios", "mod-especialidades", "mod-sucursales"]);
        break;
    }

  } catch (err) {
    console.error("Error al obtener el usuario:", err);
    window.location.href = "/login";
  }
});
