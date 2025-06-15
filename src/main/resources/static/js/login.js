<<<<<<< HEAD
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
=======
document.addEventListener('DOMContentLoaded', () => {
  console.log("🟢 login.js cargado");

  const form = document.querySelector('form');
  const apiUrl = '/api/auth/login';

  form?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email')?.value.trim();
    const password = document.getElementById('password')?.value;

    if (!email || !password) {
      alert("Completá todos los campos.");
      return;
    }

    try {
      const res = await fetch(apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      console.log("📡 Estado de respuesta:", res.status);

      if (!res.ok) {
        const { error } = await res.json().catch(() => ({}));
        throw new Error(error || 'Credenciales inválidas');
      }

      const { token } = await res.json();

      // Guardar el token correctamente
      localStorage.setItem('authToken', token);
      console.log("✅ Token guardado en localStorage");

      // Redirigir al panel principal
      window.location.href = '/inicio';

    } catch (err) {
      console.error("❌ Error en login:", err.message);
      alert(err.message || "Error al iniciar sesión");
>>>>>>> 99f4d3c (Version Funcional Spring Security)
    }
  });
});
