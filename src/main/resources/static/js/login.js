document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("form");

  form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    const payload = {
      email,
      password
    };

    try {
      const response = await fetch("/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error?.mensaje || "Credenciales inválidas");
      }

      const data = await response.json();

      // Guardar token y datos en localStorage
      localStorage.setItem("token", data.token);
      localStorage.setItem("nombre", data.nombre);
      localStorage.setItem("rol", data.rol);

      alert("Inicio de sesión exitoso.");
      window.location.href = "index.html"; // redirigir al inicio

    } catch (error) {
      console.error("Error de login:", error);
      alert("No se pudo iniciar sesión: " + error.message);
    }
  });
});
