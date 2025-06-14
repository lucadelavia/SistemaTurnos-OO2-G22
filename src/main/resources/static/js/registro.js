document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("registro-form");

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const nombre = form.nombre.value.trim();
    const apellido = form.apellido.value.trim();
    const dni = form.dni.value.trim();
    const direccion = form.direccion.value.trim();
    const email = form.email.value.trim();
    const password = form.password.value;
    const confirmPassword = form["confirm-password"].value;

    // Validación de contraseña
    if (password !== confirmPassword) {
      alert("Las contraseñas no coinciden.");
      return;
    }

    const payload = {
      nombre,
      apellido,
      dni,
      direccion,
      email,
      password
    };

    try {
      const response = await fetch("/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error?.mensaje || "Error en el registro");
      }

      const data = await response.json();
      alert("Registro exitoso. ID de cliente: " + data.id);
      window.location.href = "login.html";
    } catch (error) {
      console.error("Error en el registro:", error);
      alert("No se pudo registrar: " + error.message);
    }
  });
});
